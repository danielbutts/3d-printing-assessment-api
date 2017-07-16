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
    private Long minOrder;
    private Long maxTurnaround;
    private String stlFilename;
    private String printProcess;
    private Double basePriceMultiplier;
    private Double materialMultiplier;
    private Double processMultiplier;

    public Double getBasePriceMultiplier() {
        return calculateBasePriceMultiplier();
    }

    public void setBasePriceMultiplier(Double basePriceMultiplier) {
        this.basePriceMultiplier = calculateBasePriceMultiplier();
    }

    public Double getMaterialMultiplier() {
        return calculateMaterialMultiplier();
    }

    public void setMaterialMultiplier(Double materialMultiplier) {
        this.materialMultiplier = calculateMaterialMultiplier();
    }

    public Double getProcessMultiplier() {
        return calculateProcessMultiplier();
    }

    public void setProcessMultiplier(Double processMultiplier) {
        this.processMultiplier = calculateProcessMultiplier();
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

    public Long getMinOrder() {
        return minOrder;
    }

    public void setMinOrder(Long minOrder) {
        this.minOrder = minOrder;
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

    public String getPrintProcess() {
        return printProcess;
    }

    public void setPrintProcess(String printProcess) {
        this.printProcess = printProcess;
    }

//    public Double getPrintCostEstimate() {
//        Double costEstimate = basePriceMultiplier();
//        System.out.println("costEstimate = "+costEstimate);
//        if (costEstimate != null) {
//            costEstimate = processMultiplier(costEstimate);
//        }
//        System.out.println("costEstimate = "+costEstimate);
//        if (costEstimate != null) {
//            costEstimate = materialMultiplier(costEstimate);
//        }
//        System.out.println("costEstimate = "+costEstimate);
//
//        if (this.volume == null || costEstimate == null) {
//            System.out.println("Cost Estimate Null");
//            return null;
//        }
//        System.out.println("Cost Estimate: "+costEstimate);
//        return costEstimate;
//    }

    private Double calculateBasePriceMultiplier() {
        // y = 9.029815 + 18414310.97/(1 + (x/1.107513e-17)^0.3233867)

        Double multiplier = null;
        if (this.volume != null) {
            multiplier = 9.029815 + 18414310.97/
                    (1 + java.lang.Math.pow(this.volume/1.107513e-17,0.3233867));
        }
        System.out.println("Base Price Multiplier: " + multiplier);
        return multiplier;
    }

    private Double calculateMaterialMultiplier() {
        // fitting equation from https://mycurvefit.com/
        // y = 0.424533 + (0.8587039 - 0.424533)/(1 + (x/11.96723)^3.993398)
        Double multiplier = null;

        if (this.material == null || this.volume == null) {
            return null;
        }
        switch (this.material.getType()) {
            case "Stainless Steel": multiplier = 1d;
                break;
            case "Aluminum":
                multiplier = 0.424533 + (0.8587039 - 0.424533)/
                        (1 + java.lang.Math.pow(this.volume/11.96723,3.993398));
                break;
            case "Cobalt":
                multiplier = 1.15;
                break;
            case "Nickel":
                multiplier = 0.85;
                break;
            case "Titanium":
                multiplier = 1.24;
                break;
        }

        if (multiplier == null) {
            return null;
        }
        System.out.println("Material Multiplier: " + multiplier);
        return multiplier;
    }

    private Double calculateProcessMultiplier() {
        // multiplier based on single data point (~$450 for Binder Jetting and $2500 for DMLS)
        Double multiplier = null;

        if (this.printProcess == null || this.volume == null) {
            return null;
        }
        switch (this.printProcess) {
            case "Binder Jetting":
                multiplier = 1d;
                break;
            case "Direct Metal Laser Sintering (DMLS)":
                multiplier = 5.56d;
                break;
        }
        if (multiplier == null) {
            return null;
        }
        System.out.println("Process Multiplier: " + multiplier);
        return multiplier;
    }
}
