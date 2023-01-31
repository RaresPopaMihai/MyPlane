package com.example.myplane.models;

public class Plane {
    private String manufacturer;
    private String model;
    private String engineType;
    private double maxSpeed;
    private double grossWeight;
    private double ceilingAltitude;
    private double length;
    private double height;
    private double wingSpan;
    private double range;

    public Plane(String manufacturer, String model, String engineType, double maxSpeed, double grossWeight,
                 double ceilingAltitude, double length, double height, double wingSpan, double range) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.engineType = engineType;
        this.maxSpeed = maxSpeed;
        this.grossWeight = grossWeight;
        this.ceilingAltitude = ceilingAltitude;
        this.length = length;
        this.height = height;
        this.wingSpan = wingSpan;
        this.range = range;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public String getEngineType() {
        return engineType;
    }

    public double getGrossWeight() {
        return grossWeight;
    }

    public double getCeilingAltitude() {
        return ceilingAltitude;
    }

    public double getLength() {
        return length;
    }

    public double getHeight() {
        return height;
    }

    public double getWingSpan() {
        return wingSpan;
    }

    public double getRange() {
        return range;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", engineType='" + engineType + '\'' +
                ", maxSpeed=" + maxSpeed +
                ", grossWeight=" + grossWeight +
                ", ceilingAltitude=" + ceilingAltitude +
                ", length=" + length +
                ", height=" + height +
                ", wingSpan=" + wingSpan +
                ", range=" + range +
                '}';
    }
}
