package com.github.danielbutts.partsanalyzer.controller;

import com.github.danielbutts.partsanalyzer.model.Printer;
import com.github.danielbutts.partsanalyzer.repository.PrinterRepository;
import org.springframework.web.bind.annotation.*;

/**
 * Created by danielbutts on 7/8/17.
 */

@RestController
@CrossOrigin
@RequestMapping("/printers")
public class PrinterController {

    private final PrinterRepository repository;

    public PrinterController(PrinterRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public Iterable<Printer> all() {
        return this.repository.findAll();
    }

    @GetMapping("/{id}")
    public Printer getPrinterById(@PathVariable String id) {
        Long printerId =  Long.parseLong(id);
        return this.repository.findById(printerId);
    }

    @PostMapping("")
    public Printer create(@RequestBody Printer printer) throws Exception {
         return this.repository.save(printer);
    }

    @PostMapping("/{id}/material/{mId}")
    public Printer addPrinterToBureau(@PathVariable String id, @PathVariable String mId) {
        Long printerId =  Long.parseLong(id);
        Long materialId =  Long.parseLong(mId);

        Long returnedId = this.repository.addMaterialToPrinter(materialId, printerId);
        return this.repository.findById(returnedId);
    }

    @PatchMapping("")
    public Printer update(@RequestBody Printer printer) {
        Printer existingPrinter = this.repository.findById(printer.getId());

        if (printer.getName() != null) {
            existingPrinter.setName(printer.getName());
        }
        if (printer.getManufacturer() != null) {
            existingPrinter.setManufacturer(printer.getManufacturer());
        }
        if (printer.getMaxHeight() != null) {
            existingPrinter.setMaxHeight(printer.getMaxHeight());
        }
        if (printer.getMaxWidth() != null) {
            existingPrinter.setMaxWidth(printer.getMaxWidth());
        }
        if (printer.getMaxDepth() != null) {
            existingPrinter.setMaxDepth(printer.getMaxDepth());
        }
        if (printer.getMaterials() != null) {
            existingPrinter.setMaterials(printer.getMaterials());
        }
        if (printer.getProcess() != null) {
            existingPrinter.setProcess(printer.getProcess());
        }

        return this.repository.save(existingPrinter);
    }


}
