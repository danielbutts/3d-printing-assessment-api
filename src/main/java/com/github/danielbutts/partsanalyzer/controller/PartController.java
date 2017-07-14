package com.github.danielbutts.partsanalyzer.controller;

import com.github.danielbutts.partsanalyzer.model.Part;
import com.github.danielbutts.partsanalyzer.repository.MaterialRepository;
import com.github.danielbutts.partsanalyzer.repository.PartRepository;
import org.springframework.web.bind.annotation.*;

/**
 * Created by danielbutts on 7/8/17.
 */

@RestController
@CrossOrigin
@RequestMapping("/parts")
public class PartController {

    private final PartRepository repository;
    private final MaterialRepository materialRepository;

    public PartController(PartRepository repository, MaterialRepository materialRepository) {
        this.repository = repository;
        this.materialRepository = materialRepository;
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

//    @PostMapping("/batch")
//    public ArrayList<Part> createMultiple(@RequestBody List<Part> parts) throws Exception {
//        ArrayList<Part> createdParts = new ArrayList<Part>();
////        for (Part part : parts) {
////            createdParts.add(this.repository.save(part));
////            this.repository.addUserToPart(part.getUserId(),part.getId());
////        }
//        return createdParts;
//    }

    @PatchMapping("")
    public Part update(@RequestBody Part part) {
        if (part.getMaterialId() != null) {
            this.repository.removeMaterialFromPart(part.getId());
            this.repository.addMaterialToPart(part.getId(),part.getMaterialId());
        }

        if (part.getUserId() != null) {
            this.repository.removeUserFromPart(part.getId());
            this.repository.addUserToPart(part.getUserId(),part.getId());
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
        if (part.getMinOrder() != null) {
            existingPart.setMinOrder(part.getMinOrder());
        }
        if (part.getAnnualOrder() != null) {
            existingPart.setAnnualOrder(part.getAnnualOrder());
        }

        return this.repository.save(existingPart);
    }
}
