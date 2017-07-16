package com.github.danielbutts.partsanalyzer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Created by danielbutts on 7/8/17.
 */

@Entity
@Table(name = "printers")
public class Printer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String manufacturer;

    @ManyToMany
    @JoinTable(name="printers_materials",
            joinColumns = @JoinColumn(name = "printer_id"),
            inverseJoinColumns = @JoinColumn(name = "material_id"))
    private List<Material> materials;

    @ManyToMany
    @JoinTable(name="bureaus_printers",
            joinColumns = @JoinColumn(name = "printer_id"),
            inverseJoinColumns = @JoinColumn(name = "bureau_id"))
    @JsonIgnore
    private List<Bureau> bureaus;

    private Float maxWidth;
    private Float maxHeight;
    private Float maxDepth;
    private String process;

    private Float costFactor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public Float getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(Float maxWidth) {
        this.maxWidth = maxWidth;
    }

    public Float getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(Float maxHeight) {
        this.maxHeight = maxHeight;
    }

    public Float getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(Float maxDepth) {
        this.maxDepth = maxDepth;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public Float getCostFactor() {
        return costFactor;
    }

    public void setCostFactor(Float costFactor) {
        this.costFactor = costFactor;
    }
}
