package com.github.danielbutts.partsanalyzer.controller;

import com.github.danielbutts.partsanalyzer.model.Material;
import com.github.danielbutts.partsanalyzer.repository.MaterialRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by danielbutts on 7/8/17.
 */

@RestController
@CrossOrigin
@RequestMapping("/materials")
public class MaterialController {

    private final MaterialRepository repository;

    public MaterialController(MaterialRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public Iterable<Material> all() {
        return this.repository.findAll();
    }

    @GetMapping("/{id}")
    public Material getMeterialById(@PathVariable String id) {
        Long materialId =  Long.parseLong(id);
        return this.repository.findById(materialId);
    }

    @GetMapping("/type/{type}")
    public Material getMaterialByType(@PathVariable String type) {
        List<Material> materials = this.repository.findByType(type);
        return materials.get(0);
    }

    @PostMapping("")
    public Material create(@RequestBody Material material) throws Exception {
         return this.repository.save(material);
    }

    @PatchMapping("")
    public Material update(@RequestBody Material material) {
        Material existingMaterial = this.repository.findById(material.getId());

        if (material.getName() != null) {
            existingMaterial.setName(material.getName());
        }
        if (material.getCategory() != null) {
            existingMaterial.setCategory(material.getCategory());
        }
        if (material.getDensity() != null) {
            existingMaterial.setDensity(material.getDensity());
        }
        if (material.getType() != null) {
            existingMaterial.setType(material.getType());
        }

        return this.repository.save(existingMaterial);
    }


}
