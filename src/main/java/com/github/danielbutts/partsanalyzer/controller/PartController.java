package com.github.danielbutts.partsanalyzer.controller;

import com.github.danielbutts.partsanalyzer.model.*;
import com.github.danielbutts.partsanalyzer.repository.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by danielbutts on 7/8/17.
 */

@RestController
@CrossOrigin
@RequestMapping("/parts")
public class PartController {

    private final PartRepository repository;
    private final UserRepository userRepository;
    private final MaterialRepository materialRepository;
    private final BureauRepository bureauRepository;
    private final PrinterRepository printerRepository;

    public PartController(PartRepository repository, MaterialRepository materialRepository,
                          UserRepository userRepository, BureauRepository bureauRepository,
                          PrinterRepository printerRepository) {
        this.repository = repository;
        this.materialRepository = materialRepository;
        this.userRepository = userRepository;
        this.bureauRepository = bureauRepository;
        this.printerRepository = printerRepository;
    }

    @GetMapping("")
    public Iterable<Part> all() {
        return this.repository.findAll();
    }

    @GetMapping("/{id}")
    public Part getPartById(@PathVariable String id) {
        Long partId =  Long.parseLong(id);
        return this.repository.findById(partId);
    }

    @PostMapping("")
    public Part create(@RequestBody Part part) {
        Material material = materialRepository.findById(part.getMaterialId());
        part.setMaterial(material);
        Part newPart = this.repository.save(part);
        User user = userRepository.findById(part.getUserId());
        if (user.getParts() != null) {
            List<Part> parts = user.getParts();
            parts.add(part);
            user.setParts(parts);
        } else {
            List<Part> parts = new ArrayList<Part>();
            parts.add(part);
            user.setParts(parts);
        }
        userRepository.save(user);
        return newPart;
    }

    @PatchMapping("")
    public Part update(@RequestBody Part part) {
        System.out.println("Part update " + part.getMaterialId());
        if (part.getMaterialId() != null) {
            this.repository.removeMaterialFromPart(part.getId());
            this.repository.addMaterialToPart(part.getId(),part.getMaterialId());
        }

        Part existingPart = this.repository.findById(part.getId());

        if (part.getName() != null) {
            existingPart.setName(part.getName());
        }
        if (part.getHeight() != null) {
            existingPart.setHeight(part.getHeight());
        }
        if (part.getWidth() != null) {
            existingPart.setWidth(part.getWidth());
        }
        if (part.getDepth() != null) {
            existingPart.setDepth(part.getDepth());
        }
        if (part.getVolume() != null) {
            existingPart.setVolume(part.getVolume());
        }
        if (part.getOrderSize() != null) {
            existingPart.setOrderSize(part.getOrderSize());
        }
        if (part.getMaxTurnaround() != null) {
            existingPart.setMaxTurnaround(part.getMaxTurnaround());
        }
        if (part.getStrengthCritical() != null) {
            existingPart.setStrengthCritical(part.getStrengthCritical());
        }

        if (part.getPrice() != null) {
            existingPart.setPrice(part.getPrice());
        }

        existingPart.setMaterialMultiplier(null);
        existingPart.setBasePriceMultiplier(null);

        return this.repository.save(existingPart);
    }

    @GetMapping("/{id}/quantity/{qty}")
    public List<PrintOption> getCheapestPrintOptionsForPartId(@PathVariable String id, @PathVariable String qty) {
        Long partId =  Long.parseLong(id);
        Long quantity =  Long.parseLong(qty);
        Part part = repository.findById(partId);
        if (!part.getComplete()) {
            return null;
        }

        List<PrintOption> validPrintOptions = getValidPrintOptions(part);
        List<PrintOption> cheapestPrintOptions = getValidPrintOptions(part);


        Long remainingQty = part.getOrderSize();

        while (remainingQty > 0) {
            PrintOption cheapestOption = getCheapestOption(remainingQty, validPrintOptions);
            if (cheapestOption != null) {
                cheapestPrintOptions.add(cheapestOption);
            } else {
                break;
            }
            remainingQty -= Math.max(cheapestOption.getMaxQuantity(), remainingQty);
        }
        return cheapestPrintOptions;
    }

    public PrintOption getCheapestOption(Long remainingQty, List<PrintOption> validPrintOptions) {
        Double minUnitCost = null;
        PrintOption cheapestOption = null;

        for (PrintOption option : validPrintOptions) {
            Long optionQuantity = Math.min(option.getMaxQuantity(), remainingQty);
            Double discountedPrice = option.getPrice() * getQuantityDiscount(optionQuantity);
            if (minUnitCost == null || discountedPrice < minUnitCost) {
                minUnitCost = discountedPrice;
                option.setPrintQuantity(optionQuantity);
                cheapestOption = option;
            }
        }

        Iterator<PrintOption> iter = validPrintOptions.iterator();
        while(iter.hasNext()){
            if(iter.next().getId() == cheapestOption.getId()){
                iter.remove();
            }
        }

       return cheapestOption;
    }

    public Double getQuantityDiscount(Long quantity) {
        Double discount = 52.53779 + 11625337.46/(1 + Math.pow(quantity/0.0001151726,1.368253));
        System.out.println("discount " + discount);
        return discount;
    }

    private List<PrintOption> getValidPrintOptions(Part part) {
        List<Bureau> bureaus = bureauRepository.findAll();
        List<PrintOption> validPrintOptions = new ArrayList<PrintOption>();

        Integer optionId = 1;

        for (Bureau bureau : bureaus) {
            for (Printer printer : bureau.getPrinters()) {
                boolean isValidPrinter = false;
                List<Material> materials = printer.getMaterials();
                if (materials == null) {
                    continue;
                } else {
                    for (Material material : materials) {
//                        System.out.println("part material: "+part.getMaterial().getType());
//                        System.out.println("part material: "+material.getType());
                        if (material.getType().equals(part.getMaterial().getType())) {
                            isValidPrinter = true;
                            List<Material> partMaterials = new ArrayList<Material>();
                            partMaterials.add(material);
                            printer.setMaterials(partMaterials);
                        }
                    }
                    if (!isValidPrinter) {
                        continue;
                    }
                }

                if (part.getStrengthCritical() != null &&
                        part.getStrengthCritical() && !printer.getProcess().equals("DMLS")) {
                    continue;
                }
                if (part.getWidth() > printer.getMaxWidth()) {
                    continue;
                }
                if (part.getHeight() > printer.getMaxHeight()) {
                    continue;
                }
                if (part.getDepth() > printer.getMaxDepth()) {
                    continue;
                }

                PrintOption option = new PrintOption(printer);

                option.setMaxQuantity(bureau.getMaxOrder());
                option.setZipCode(bureau.getZipCode());
                option.setMinQuantity(bureau.getMinOrder());
                option.setCostFactor(bureau.getCostFactor());
                option.setTurnaround(bureau.getTurnaround());
                option.setId(optionId);
                Double price = part.getVolume() *
                        part.getBasePriceMultiplier() * part.getMaterialMultiplier() *
                        option.getPrinter().getProcessMultiplier() * option.getCostFactor();
                option.setPrice(price);


                validPrintOptions.add(option);
                optionId++;
            }
        }

        return validPrintOptions;
    }

}
