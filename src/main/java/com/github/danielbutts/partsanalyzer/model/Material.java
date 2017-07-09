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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String manufacturer;
    private Float density;
    private Float volumeBuildSpeed;
    private Float unitCost;

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

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Float getDensity() {
        return density;
    }

    public void setDensity(Float density) {
        this.density = density;
    }

    public Float getVolumeBuildSpeed() {
        return volumeBuildSpeed;
    }

    public void setVolumeBuildSpeed(Float volumeBuildSpeed) {
        this.volumeBuildSpeed = volumeBuildSpeed;
    }

    public Float getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Float unitCost) {
        this.unitCost = unitCost;
    }
}
