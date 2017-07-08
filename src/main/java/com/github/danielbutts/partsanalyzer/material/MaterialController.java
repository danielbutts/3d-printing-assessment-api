package com.github.danielbutts.partsanalyzer.material;

import org.springframework.web.bind.annotation.*;

/**
 * Created by danielbutts on 7/8/17.
 */

@RestController
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
        if (material.getManufacturer() != null) {
            existingMaterial.setManufacturer(material.getManufacturer());
        }
        if (material.getDensity() != null) {
            existingMaterial.setDensity(material.getDensity());
        }
        if (material.getVolumeBuildSpeed() != null) {
            existingMaterial.setVolumeBuildSpeed(material.getVolumeBuildSpeed());
        }
        if (material.getUnitCost() != null) {
            existingMaterial.setUnitCost(material.getUnitCost());
        }

        return this.repository.save(existingMaterial);
    }


}
