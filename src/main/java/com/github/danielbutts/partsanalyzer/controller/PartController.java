package com.github.danielbutts.partsanalyzer.controller;

import com.github.danielbutts.partsanalyzer.model.Material;
import com.github.danielbutts.partsanalyzer.model.Part;
import com.github.danielbutts.partsanalyzer.model.User;
import com.github.danielbutts.partsanalyzer.repository.MaterialRepository;
import com.github.danielbutts.partsanalyzer.repository.PartRepository;
import com.github.danielbutts.partsanalyzer.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    public PartController(PartRepository repository, MaterialRepository materialRepository,
                          UserRepository userRepository) {
        this.repository = repository;
        this.materialRepository = materialRepository;
        this.userRepository = userRepository;
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
        System.out.println("Part update" + part.getMaterialId());
        if (part.getMaterial() != null) {
            this.repository.removeMaterialFromPart(part.getId());
            this.repository.addMaterialToPart(part.getId(),part.getMaterial().getId());
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
        if (part.getMaxTurnaround() != null) {
            existingPart.setMaxTurnaround(part.getMaxTurnaround());
        }

        return this.repository.save(existingPart);
    }
}
