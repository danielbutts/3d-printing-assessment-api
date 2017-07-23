package com.github.danielbutts.partsanalyzer.model;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danielbutts on 7/20/17.
 */
public class PrintOption {

    private int id;
    private Bureau bureau;
    private Printer printer;
    private Part part;
    private List<Double> prices;

    // calculated
    private Long printQuantity;
//    private Double unitPrice;

    public Long getPrintQuantity() {
        return printQuantity;
    }

    public void setPrintQuantity(Long printQuantity) {
        this.printQuantity = printQuantity;
    }

    public Bureau getBureau() {
        return bureau;
    }

    public Printer getPrinter() {
        return printer;
    }

    public Part getPart() {
        return part;
    }

//    public Double getUnitPrice() {
//        return unitPrice;
//    }
//
//    public void setUnitPrice(Double unitPrice) {
//        this.unitPrice = unitPrice;
//    }

    public PrintOption(Bureau bureau, Printer printer, Part part) throws InvalidArgumentException {
        if (bureau == null) {
            throw new InvalidArgumentException(new String[]{"Bureau cannot be null."});
        }
        if (printer == null) {
            throw new InvalidArgumentException(new String[]{"Printer cannot be null."});
        }
        if (printer.getMaterials() == null) {
            throw new InvalidArgumentException(new String[]{"Printer materials cannot be null."});
        }
        if (part == null) {
            throw new InvalidArgumentException(new String[]{"Part cannot be null."});
        }
        if (part.getMaterial() == null) {
            throw new InvalidArgumentException(new String[]{"Part material cannot be null."});
        }

        this.bureau = bureau;
        this.printer = printer;
        this.part = part;
    }

    public static List<PrintOption> getPrintOptions(Bureau bureau, Part part) {
        ArrayList<PrintOption> printOptions = new ArrayList<PrintOption>();

        for (Printer printer : bureau.getPrinters()) {
            try {
                printOptions.add(new PrintOption(bureau, printer, part));
            } catch (InvalidArgumentException e) {
                e.printStackTrace();
            }
        }
        return printOptions;
    }

    public static List<PrintOption> getValidPrintOptions(List<PrintOption> options) {
        ArrayList<PrintOption> validOptions = new ArrayList<PrintOption>();

        for (PrintOption option : options) {
            boolean isValid = false;
            for (Material material : option.printer.getMaterials()) {
                if (material.getType().equals(option.part.getMaterial().getType())) {
                    isValid = true;
                }
            }
            if (!isValid) {
                continue;
            }

            if (option.part.getStrengthCritical() == null ||
                    (option.part.getStrengthCritical() &&
                    !option.printer.getProcess().equals("DMLS"))) {
                continue;
            }

            if (option.part.getWidth() > option.printer.getMaxWidth()) {
                continue;
            }
            if (option.part.getHeight() > option.printer.getMaxHeight()) {
                continue;
            }
            if (option.part.getDepth() > option.printer.getMaxDepth()) {
                continue;
            }

            validOptions.add(option);
        }
        return validOptions;
    }

    public Double calculatePrice(Long quantity) {
        Double basePriceMultiplier = calculateBasePriceMultiplier();
        Double materialMultiplier = calculateMaterialMultiplier();
        Double processMultiplier = calculateProcessMultiplier();
        Double quantityDiscount = getQuantityDiscount(quantity);

        if (quantity == null || basePriceMultiplier == null || materialMultiplier == null ||
                processMultiplier == null ) {
            return null;
        }

        Double price = part.getVolume() * basePriceMultiplier * materialMultiplier *
                processMultiplier * bureau.getCostFactor() * getQuantityDiscount(quantity);

//        System.out.println("volume "+ part.getVolume());
//        System.out.println("basePriceMultiplier "+ basePriceMultiplier);
//        System.out.println("materialMultiplier "+ materialMultiplier);
//        System.out.println("processMultiplier "+ processMultiplier);
//        System.out.println("quantityDiscount "+ quantityDiscount);
//        System.out.println("price "+ price);

        return price;
    }

    private Double getQuantityDiscount(Long quantity) {
        Double discount = 0.6003085 + (348425.5 - 0.6003085)/(1 + Math.pow(quantity/0.0001441639,1.546536));
        return discount;
    }

    private Double calculateProcessMultiplier() {
        // multiplier based on single data point (~$450 for Binder Jetting and $2500 for DMLS)
        Double multiplier = null;

        if (printer.getProcess() == null) {
            return null;
        }
        if (printer.getProcess().equals("DMLS")) {
            multiplier = 5.56d;
        } else {
            multiplier = 1d;
        }
        return multiplier;
    }

    private Double calculateBasePriceMultiplier() {
        // y = 9.029815 + 18414310.97/(1 + (x/1.107513e-17)^0.3233867)

        Double multiplier = null;
        if (part.getVolume() != null) {
            multiplier = 9.029815 + 18414310.97/
                    (1 + java.lang.Math.pow(part.getVolume()/1.107513e-17,0.3233867));
        }
        return multiplier;
    }

    private Double calculateMaterialMultiplier() {
        // fitting equation from https://mycurvefit.com/
        // y = 0.424533 + (0.8587039 - 0.424533)/(1 + (x/11.96723)^3.993398)
        Double multiplier = null;

        if (part.getMaterial() == null || part.getMaterial().getType() == null || part.getVolume() == null) {
            return null;
        }
        switch (part.getMaterial().getType()) {
            case "Stainless Steel": multiplier = 1d;
                break;
            case "Aluminum":
                multiplier = 0.424533 + (0.8587039 - 0.424533)/
                        (1 + java.lang.Math.pow(part.getVolume()/11.96723,3.993398));
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
        return multiplier;
    }

    public static PrintOption getCheapestOption(List<PrintOption> options, Long quantity) {
        Double minUnitCost = null;
        PrintOption cheapestOption = null;

        for (PrintOption option : options) {
            Long optionQuantity = Math.min(option.bureau.getMaxOrder(), quantity);
            Double unitPrice = option.calculatePrice(optionQuantity);
            if (minUnitCost == null || unitPrice < minUnitCost) {
                minUnitCost = unitPrice;
                cheapestOption = option;
                cheapestOption.setPrintQuantity(optionQuantity);
//                cheapestOption.setUnitPrice(unitPrice);
//                cheapestOption.prices = option.buildPrices(cheapestOption);
            }
        }
        return cheapestOption;
    }

    public List<Double> getPrices() {
        List<Double> prices = new ArrayList<Double>();
        for (Long i = 1l; i <= printQuantity; i++) {
            prices.add(calculatePrice(i));
        }
        return prices;
    }
}
