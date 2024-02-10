package com.example.carpoolbuddy_ison.classDictionary;

import java.util.ArrayList;

public class Segway extends Vehicle {
    private int range;
    private int weightCapacity;

    public Segway(String owner, String model, int capacity, int remainingCapacity, String vehilceID, double basePrice, int range, int weightCapacity){
        super(owner, model, capacity, remainingCapacity,"Bicycle", vehilceID, basePrice);
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getWeightCapacity() {
        return weightCapacity;
    }

    public void setWeightCapacity(int weightCapacity) {
        this.weightCapacity = weightCapacity;
    }

    public void printInfo(){
        super.printInfo();
        toString();
    }

    @Override
    public String toString() {
        return "Segway{" +
                "owner=" + getOwner() +
                "model=" + getModel() +
                "capacity=" + getCapacity() +
                "remainingCapacity=" + getRemainingCapacity() +
                "vehicleID=" + getVehicleID() +
                "open=" + isOpen() +
                "vehicleTpye=" + getVehicleType() +
                "basePrice=" + getBasePrice() +
                "range=" + range +
                ", weightCapacity=" + weightCapacity +
                '}';
    }
}