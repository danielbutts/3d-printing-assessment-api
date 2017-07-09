package com.github.danielbutts.partsanalyzer.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by danielbutts on 7/8/17.
 */

@Entity
@Table(name = "bureaus")
public class Bureau {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String zipCode;

    @ManyToMany
    @JoinTable(name="bureaus_printers",
            joinColumns = @JoinColumn(name = "bureau_id"),
            inverseJoinColumns = @JoinColumn(name = "printer_id"))
    private List<Printer> printers;

    private Float margin;
    private Float discountFactor;  // unit price = (unit cost) * (1 + margin) * (n units)^discountFactor
    private Long minOrder;
    private Long maxOrder;

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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public List<Printer> getPrinters() {
        return printers;
    }

    public void setPrinters(List<Printer> printers) {
        this.printers = printers;
    }

    public Float getMargin() {
        return margin;
    }

    public void setMargin(Float margin) {
        this.margin = margin;
    }

    public Float getDiscountFactor() {
        return discountFactor;
    }

    public void setDiscountFactor(Float discountFactor) {
        this.discountFactor = discountFactor;
    }

    public Long getMinOrder() {
        return minOrder;
    }

    public void setMinOrder(Long minOrder) {
        this.minOrder = minOrder;
    }

    public Long getMaxOrder() {
        return maxOrder;
    }

    public void setMaxOrder(Long maxOrder) {
        this.maxOrder = maxOrder;
    }
}
