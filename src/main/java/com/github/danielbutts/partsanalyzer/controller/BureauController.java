package com.github.danielbutts.partsanalyzer.controller;

import com.github.danielbutts.partsanalyzer.model.Bureau;
import com.github.danielbutts.partsanalyzer.model.Printer;
import com.github.danielbutts.partsanalyzer.repository.BureauRepository;
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

    public BureauController(BureauRepository repository, PrinterRepository printerRepository) {
        this.repository = repository;
        this.printerRepository = printerRepository;
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
