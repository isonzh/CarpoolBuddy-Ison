package com.example.carpoolbuddy_ison.classDictionary;
import java.util.ArrayList;

public class Helicopter extends Vehicle {
    private int maxAltitude;
    private int maxAirSpeed;

    public Helicopter(String owner, String model, int capacity, int remainingCapacity, String vehicleID, double basePrice, int maxAltitude, int maxAirSpeed) {
        super(owner, model, capacity, remainingCapacity,"Helicopter", vehicleID, basePrice);
    }

    public int getMaxAltitude() {
        return maxAltitude;
    }

    public void setMaxAltitude(int maxAltitude) {
        this.maxAltitude = maxAltitude;
    }

    public int getMaxAirSpeed() {
        return maxAirSpeed;
    }

    public void setMaxAirSpeed(int maxAirSpeed) {
        this.maxAirSpeed = maxAirSpeed;
    }

    public void printInfo(){
        super.printInfo();
        toString();
    }

    @Override
    public String toString() {
        return "Helicopter{" +
                "owner=" + getOwner() +
                "model=" + getModel() +
                "capacity=" + getCapacity() +
                "remainingCapacity=" + getRemainingCapacity() +
                "vehicleID=" + getVehicleID() +
                "open=" + isOpen() +
                "vehicleTpye=" + getVehicleType() +
                "basePrice=" + getBasePrice() +
                "maxAltitude=" + maxAltitude +
                ", maxAirSpeed=" + maxAirSpeed +
                '}';
    }
}