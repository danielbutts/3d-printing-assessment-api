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
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    private Float width;
    private Float height;
    private Float depth;
    private Float volume;
    private Float weight;
    private Float price;
    private Long minOrder;
    private Long annualOrder;
    private Long maxTurnaround;
    private String stlFilename;
    private Long printTime;
    private Long preprocessTime;
    private Long postprocessTime;

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

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
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

    public Long getAnnualOrder() {
        return annualOrder;
    }

    public void setAnnualOrder(Long annualOrder) {
        this.annualOrder = annualOrder;
    }

    public Long getMinTurnaround() {
        return maxTurnaround;
    }

    public void setMinTurnaround(Long minTurnaround) {
        this.maxTurnaround = minTurnaround;
    }

    public String getStlFilename() {
        return stlFilename;
    }

    public void setStlFilename(String stlFilename) {
        this.stlFilename = stlFilename;
    }

    public Long getPrintTime() {
        return printTime;
    }

    public void setPrintTime(Long printTime) {
        this.printTime = printTime;
    }

    public Long getPreprocessTime() {
        return preprocessTime;
    }

    public void setPreprocessTime(Long preprocessTime) {
        this.preprocessTime = preprocessTime;
    }

    public Long getPostprocessTime() {
        return postprocessTime;
    }

    public void setPostprocessTime(Long postprocessTime) {
        this.postprocessTime = postprocessTime;
    }
}
