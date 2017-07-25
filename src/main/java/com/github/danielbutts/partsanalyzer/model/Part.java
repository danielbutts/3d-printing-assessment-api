package com.github.danielbutts.partsanalyzer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by danielbutts on 7/8/17.
 */

@Entity
@Table(name = "parts")
public class Part {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinTable(name="users_parts",
            joinColumns = @JoinColumn(name = "part_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinTable(name="parts_materials",
            joinColumns = @JoinColumn(name = "part_id"),
            inverseJoinColumns = @JoinColumn(name = "material_id"))
    private Material material;

    private Long userId;
    @Transient
    private Long materialId;
    private Float width;
    private Float height;
    private Float depth;
    private Float volume;
    private Float price;
    private Long orderSize;
    private Long maxTurnaround;
    private String stlFilename;

    @Column(name="is_strength_critical", columnDefinition="boolean default false")
    private Boolean isStrengthCritical;
    private Float weight;

    @Column(name="is_complete", columnDefinition="boolean default false")
    private Boolean isComplete;

    public Boolean getComplete() {
        boolean partComplete = true;
        if (this.width == null) {
            partComplete = false;
        }
        if (this.depth == null) {
            partComplete = false;
        }
        if (this.height == null) {
            partComplete = false;
        }
        if (this.volume == null) {
            partComplete = false;
        }
        if (this.material == null) {
            partComplete = false;
        }
        if (this.price == null) {
            partComplete = false;
        }
        if (this.orderSize == null) {
            partComplete = false;
        }
        if (this.maxTurnaround == null) {
           partComplete = false;
        }
        return partComplete;
    }

    public Float getWeight() {
        if (this.volume == null || this.material == null || this.material.getDensity() == null) {
            return null;
        }
        return this.volume * this.material.getDensity();
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getDepth() {
        return depth;
    }

    public void setDepth(Float depth) {
        this.depth = depth;
    }

    public Float getVolume() {
        return volume;
    }

    public void setVolume(Float volume) {
        this.volume = volume;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Long getOrderSize() {
        return orderSize;
    }

    public void setOrderSize(Long orderSize) {
        this.orderSize = orderSize;
    }

    public String getStlFilename() {
        return stlFilename;
    }

    public void setStlFilename(String stlFilename) {
        this.stlFilename = stlFilename;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(long materialId) {
        this.materialId = materialId;
    }

    public Long getMaxTurnaround() {
        return maxTurnaround;
    }

    public void setMaxTurnaround(Long maxTurnaround) {
        this.maxTurnaround = maxTurnaround;
    }

    public Long getUserId() {
        return userId;
    }

    public Boolean getStrengthCritical() {
        return isStrengthCritical;
    }

    public void setStrengthCritical(Boolean strengthCritical) {
        isStrengthCritical = strengthCritical;
    }
}
