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

        return this.repository.save(existingPart);
    }

    @GetMapping("/{id}/cost")
    public List<PrintOption> getCheapestPrintOptionsForPartId(@PathVariable String id) {
        Long partId = Long.parseLong(id);
        Part part = repository.findById(partId);
        if (!part.getComplete()) {
            return null;
        }
        Long quantity = part.getOrderSize() * 2;

        List<PrintOption> selectedPrintOptions = new ArrayList<PrintOption>();

        List<PrintOption> options = new ArrayList<PrintOption>();

        List<Bureau> bureaus = bureauRepository.findAll();
        for (Bureau bureau : bureaus) {
            options.addAll(PrintOption.getPrintOptions(bureau, part));
        }

        List<PrintOption> validPrintOptions = PrintOption.getValidPrintOptions(options);

        Long remainingQty = quantity;

        while (remainingQty > 0 && validPrintOptions.size() > 0) {
//            System.out.println(remainingQty);
            PrintOption cheapestOption = PrintOption.getCheapestOption(validPrintOptions, remainingQty);
            if (cheapestOption != null) { selectedPrintOptions.add(cheapestOption); }
//            System.out.println(
//                    "Bureau Name (" + cheapestOption.getBureau().getName() +
//                            ") Printer Name (" + cheapestOption.getPrinter().getName() +
//                            ") Max Qty (" + cheapestOption.getBureau().getMaxOrder() +
//                            ") Print Qty (" + cheapestOption.getPrintQuantity() + ")" +
//                            ") Price (" + cheapestOption.calculatePrice(cheapestOption.getPrintQuantity()) + ")"
//            );

            Iterator<PrintOption> iter = validPrintOptions.listIterator();
            while(iter.hasNext()){
                if(iter.next().equals(cheapestOption)){
//                    System.out.println("THEY ARE EQUAL");
                    iter.remove();
                }
            }

            remainingQty -= cheapestOption.getPrintQuantity();
        }
        return selectedPrintOptions;
    }
}
