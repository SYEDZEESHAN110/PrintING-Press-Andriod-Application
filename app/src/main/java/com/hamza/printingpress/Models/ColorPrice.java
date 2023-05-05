package com.hamza.printingpress.Models;

public class ColorPrice {
    private double rgb;
    private double cmyk;
    private double grayscale;

    public ColorPrice(double rgb, double cmyk, double grayscale) {
        this.rgb = rgb;
        this.cmyk = cmyk;
        this.grayscale = grayscale;
    }

    public double getRgb() {
        return rgb;
    }

    public double getCmyk() {
        return cmyk;
    }

    public double getGrayscale() {
        return grayscale;
    }
}
