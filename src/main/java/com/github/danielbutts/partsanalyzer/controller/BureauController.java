package com.github.danielbutts.partsanalyzer.controller;

import com.github.danielbutts.partsanalyzer.model.Bureau;
import com.github.danielbutts.partsanalyzer.model.Material;
import com.github.danielbutts.partsanalyzer.model.Part;
import com.github.danielbutts.partsanalyzer.model.Printer;
import com.github.danielbutts.partsanalyzer.repository.BureauRepository;
import com.github.danielbutts.partsanalyzer.repository.PartRepository;
import com.github.danielbutts.partsanalyzer.repository.PrinterRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danielbutts on 7/8/17.
 */

@RestController
@CrossOrigin
@RequestMapping("/bureaus")
public class BureauController {

    private final BureauRepository repository;
    private final PrinterRepository printerRepository;
    private final PartRepository partRepository;

    public BureauController(BureauRepository repository,
                            PrinterRepository printerRepository, PartRepository partRepository) {
        this.repository = repository;
        this.printerRepository = printerRepository;
        this.partRepository = partRepository;
    }

    @GetMapping("")
    public Iterable<Bureau> all() {
        return this.repository.findAll();
    }

    @GetMapping("/{id}")
    public Bureau getBureauById(@PathVariable String id) {
        Long bureauId =  Long.parseLong(id);
        return this.repository.findById(bureauId);
    }

    @GetMapping("/part/{id}")
    public List<Bureau> getValidBureausForPartId(@PathVariable String id) {
        Long partId =  Long.parseLong(id);
        Part part = partRepository.findById(partId);
        if (!part.getComplete()) {
            return new ArrayList<Bureau>();
        }
        List<Bureau> bureaus = this.repository.findAll();
        List<Bureau> validBureaus = new ArrayList<Bureau>();

        for (Bureau bureau : bureaus) {
            List<Printer> validPrinters = new ArrayList<Printer>();
            for (Printer printer : bureau.getPrinters()) {
                boolean isValidPrinter = false;
                List<Material> materials = printer.getMaterials();
                if (materials == null) {
                    continue;
                } else {
                    for (Material material : materials) {
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
                if (part.getWidth() > printer.getMaxWidth()) { continue; }
                if (part.getHeight() > printer.getMaxHeight()) { continue; }
                if (part.getDepth() > printer.getMaxDepth()) { continue; }

                validPrinters.add(printer);
            }
            if (validPrinters.size() > 0) {
                bureau.setPrinters(validPrinters);
                validBureaus.add(bureau);
            }
        }
        return validBureaus;
    }

    @PostMapping("")
    public Bureau create(@RequestBody Bureau bureau) {
        List<Printer> printers = bureau.getPrinters();
        return this.repository.save(bureau);
    }

    @PostMapping("/{id}/printer/{pId}")
    public Bureau addPrinterToBureau(@PathVariable String id, @PathVariable String pId) {
        Long bureauId =  Long.parseLong(id);
        Long printerId =  Long.parseLong(pId);

        Long returnedId = this.repository.addPrinterToBureau(bureauId, printerId);
        return this.repository.findById(returnedId);
    }

    @DeleteMapping("/{id}/printer/{pId}")
    public Bureau removePrinterToBureau(@PathVariable String id, @PathVariable String pId) {
        Long bureauId =  Long.parseLong(id);
        Long printerId =  Long.parseLong(pId);

        Long returnedId = this.repository.removePrinterFromBureau(bureauId, printerId);
        return this.repository.findById(returnedId);
    }

    @PatchMapping("")
    public Bureau update(@RequestBody Bureau bureau) {
        Bureau existingBureau = this.repository.findById(bureau.getId());

        if (bureau.getName() != null) {
            existingBureau.setName(bureau.getName());
        }
        if (bureau.getZipCode() != null) {
            existingBureau.setZipCode(bureau.getZipCode());
        }
        if (bureau.getCostFactor() != null) {
            existingBureau.setCostFactor(bureau.getCostFactor());
        }
        if (bureau.getMaxOrder() != null) {
            existingBureau.setMaxOrder(bureau.getMaxOrder());
        }
        if (bureau.getMinOrder() != null) {
            existingBureau.setMinOrder(bureau.getMinOrder());
        }
        if (bureau.getZipCode() != null) {
            existingBureau.setZipCode(bureau.getZipCode());
        }
        List<Printer> printers = bureau.getPrinters();
        if (printers != null) {
            existingBureau.setPrinters(printers);
        }
        List<Long> printerIds = bureau.getPrinterIds();
        if (printerIds != null) {
            printers = new ArrayList<Printer>();
            for (Long printerId : printerIds) {
                Printer printer = printerRepository.findById(printerId);
                if (printer != null) {
                    printers.add(printer);
                }
            }
            existingBureau.setPrinters(printers);
        }

        return this.repository.save(existingBureau);
    }


}
