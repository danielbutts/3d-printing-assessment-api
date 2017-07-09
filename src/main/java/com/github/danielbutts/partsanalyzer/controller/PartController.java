package com.github.danielbutts.partsanalyzer.controller;

import com.github.danielbutts.partsanalyzer.model.Part;
import com.github.danielbutts.partsanalyzer.repository.PartRepository;
import org.springframework.web.bind.annotation.*;

/**
 * Created by danielbutts on 7/8/17.
 */

@RestController
@RequestMapping("/parts")
public class PartController {

    private final PartRepository repository;

    public PartController(PartRepository repository) {
        this.repository = repository;
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
        return this.repository.save(part);
    }

    @PostMapping("/{id}/material/{mId}")
    public Part addMaterialToPart(@PathVariable String id, @PathVariable String mId) {
        Long partId =  Long.parseLong(id);
        Long materialId =  Long.parseLong(mId);

        Long returnedId = this.repository.addMaterialToPart(partId, materialId);
        return this.repository.findById(returnedId);
    }

    @PatchMapping("")
    public Part update(@RequestBody Part part) {
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
        if (part.getWeight() != null) {
            existingPart.setWeight(part.getWeight());
        }
        if (part.getVolume() != null) {
            existingPart.setVolume(part.getVolume());
        }
        if (part.getMinOrder() != null) {
            existingPart.setMinOrder(part.getMinOrder());
        }
        if (part.getAnnualOrder() != null) {
            existingPart.setAnnualOrder(part.getAnnualOrder());
        }
        if (part.getMinTurnaround() != null) {
            existingPart.setMinTurnaround(part.getMinTurnaround());
        }

        return this.repository.save(existingPart);
    }


}
