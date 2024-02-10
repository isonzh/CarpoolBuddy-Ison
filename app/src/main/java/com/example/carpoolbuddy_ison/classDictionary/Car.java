package com.example.carpoolbuddy_ison.classDictionary;

import java.util.ArrayList;

public class Car extends Vehicle {
    private int range;

    public Car(String owner, String model, int capacity, int remainingCapacity, String vehicleID, double basePrice, int range) {
        super(owner, model, capacity, remainingCapacity, "Car", vehicleID, basePrice);
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void printInfo(){
        super.printInfo();
        toString();
    }

    @Override
    public String toString() {
        return "Car{" +
                "owner=" + getOwner() +
                "model=" + getModel() +
                "capacity=" + getCapacity() +
                "remainingCapacity=" + getRemainingCapacity() +
                "vehicleID=" + getVehicleID() +
                "open=" + isOpen() +
                "vehicleTpye=" + getVehicleType() +
                "basePrice=" + getBasePrice() +
                "range=" + range +
                '}';
    }
}