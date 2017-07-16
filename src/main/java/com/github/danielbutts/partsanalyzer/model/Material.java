package com.github.danielbutts.partsanalyzer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Created by danielbutts on 7/8/17.
 */

@Entity
@Table(name = "materials")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category; // metal, etc.
    private String type; // stainless steel, aluminum, etc.
    private Float density;
    private Float costFactor;

    @ManyToMany
    @JoinTable(name="printers_materials",
            joinColumns = @JoinColumn(name = "material_id"),
            inverseJoinColumns = @JoinColumn(name = "printer_id"))
    @JsonIgnore
    private List<Printer> printer;

    @OneToMany
    @JoinTable(name="parts_materials",
            joinColumns = @JoinColumn(name = "material_id"),
            inverseJoinColumns = @JoinColumn(name = "part_id"))
    @JsonIgnore
    private List<Part> parts;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getDensity() {
        return density;
    }

    public void setDensity(Float density) {
        this.density = density;
    }

    public Float getCostFactor() {
        return costFactor;
    }

    public void setCostFactor(Float costFactor) {
        this.costFactor = costFactor;
    }
}
