package com.example.carpoolbuddy_ison.classDictionary;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Vehicle implements Parcelable {
    private String owner;
    private String model;
    private int capacity;
    private int remainingCapacity;
    private String vehicleID;
    private boolean open;
    private String vehicleType;
    private double basePrice;
    private ArrayList<String> ridersUIDs;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    public Vehicle() {}

    public Vehicle(String owner, String model, int capacity, int remainingCapacity, String vehicleType, String vehicleID, double basePrice) {
        this.owner = owner;
        this.model = model;
        this.capacity = capacity;
        this.remainingCapacity = remainingCapacity;
        this.vehicleID = vehicleID;
        this.open = true;
        this.vehicleType = vehicleType;
        this.basePrice = basePrice;
        this.ridersUIDs = new ArrayList<>();
    }

    protected Vehicle(Parcel in) {
        owner = in.readString();
        model = in.readString();
        capacity = in.readInt();
        remainingCapacity = in.readInt();
        vehicleID = in.readString();
        open = in.readByte() != 0;
        vehicleType = in.readString();
        basePrice = in.readDouble();
        ridersUIDs = in.createStringArrayList();
    }

    public static final Creator<Vehicle> CREATOR = new Creator<Vehicle>() {
        @Override
        public Vehicle createFromParcel(Parcel in) {
            return new Vehicle(in);
        }

        @Override
        public Vehicle[] newArray(int size) {
            return new Vehicle[size];
        }
    };

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRemainingCapacity() {
        return remainingCapacity;
    }

    public void setRemainingCapacity(int remainingCapacity) {
        this.remainingCapacity = remainingCapacity;
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    public boolean isOpen() {
        return open;
    }
    public String getOpen(){
        if(open=true){
            return "open";
        }
        else{
            return "close";
        }
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public ArrayList<String> getRidersUIDs() {
        return ridersUIDs;
    }

    public void setRidersUIDs(ArrayList<String> ridersUIDs) {
        if (ridersUIDs != null) {
            ridersUIDs.add(mAuth.getUid()); // Add the current user's UID
            this.ridersUIDs = ridersUIDs;
        }
    }

    public void addRidersUIDs(String uid) {
        if (uid != null) {
            this.ridersUIDs.add(uid);
        }
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "owner='" + owner + '\'' +
                ", model='" + model + '\'' +
                ", capacity=" + capacity +
                ", remainingCapacity=" + remainingCapacity +
                ", vehicleID='" + vehicleID + '\'' +
                ", open=" + open +
                ", vehicleType='" + vehicleType + '\'' +
                ", basePrice=" + basePrice +
                ", ridersUIDs=" + ridersUIDs +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }
    public void printInfo(){
        System.out.println(this.owner + this.model + this.capacity + this.ridersUIDs);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(owner);
        dest.writeString(model);
        dest.writeInt(capacity);
        dest.writeInt(remainingCapacity);
        dest.writeString(vehicleID);
        dest.writeByte((byte) (open ? 1 : 0));
        dest.writeString(vehicleType);
        dest.writeDouble(basePrice);
        dest.writeStringList(ridersUIDs);
    }
}


