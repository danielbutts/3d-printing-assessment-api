package com.github.danielbutts.partsanalyzer.model;

/**
 * Created by danielbutts on 7/20/17.
 */
public class PrintOption {

    private int id;
    private Printer printer;
    private Double price;
    private Long maxQuantity;
    private Long minQuantity;
    private String zipCode;
    private Float costFactor;
    private Long turnaround;
    private Long printQuantity;

    public PrintOption(Printer printer) {
        this.printer = printer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(Long maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public Long getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Long minQuantity) {
        this.minQuantity = minQuantity;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Float getCostFactor() {
        return costFactor;
    }

    public void setCostFactor(Float costFactor) {
        this.costFactor = costFactor;
    }

    public Long getTurnaround() {
        return turnaround;
    }

    public void setTurnaround(Long turnaround) {
        this.turnaround = turnaround;
    }

    public Printer getPrinter() {
        return printer;
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }

    public Long getPrintQuantity() {
        return printQuantity;
    }

    public void setPrintQuantity(Long printQuantity) {
        this.printQuantity = printQuantity;
    }
}
