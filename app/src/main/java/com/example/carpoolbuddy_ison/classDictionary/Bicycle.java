package com.example.carpoolbuddy_ison.classDictionary;
import java.util.ArrayList;

public class Bicycle extends Vehicle {
    private String bicycleType;
    private int weight;
    private int weightCapacity;


    public Bicycle(String owner, String model, int capacity, int remainingCapacity, String vehicleID, double basePrice, String bicycleType, int weight, int weightCapacity){
        super(owner, model, capacity, remainingCapacity, "Bicycle", vehicleID, basePrice);

    }

    public String getBicycleType() {
        return bicycleType;
    }

    public void setBicycleType(String bicycleType) {
        this.bicycleType = bicycleType;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
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
        return "Bicycle{" +
                "owner=" + getOwner() +
                "model=" + getModel() +
                "capacity=" + getCapacity() +
                "remainingCapacity=" + getRemainingCapacity() +
                "vehicleID=" + getVehicleID() +
                "open=" + isOpen() +
                "vehicleTpye=" + getVehicleType() +
                "basePrice=" + getBasePrice() +
                "bicycleType='" + bicycleType + '\'' +
                ", weight=" + weight +
                ", weightCapacity=" + weightCapacity +
                '}';
    }
}