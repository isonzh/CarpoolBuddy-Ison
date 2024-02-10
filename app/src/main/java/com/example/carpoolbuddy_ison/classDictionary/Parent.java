package com.example.carpoolbuddy_ison.classDictionary;

import java.util.ArrayList;

public class Parent extends User{
    private ArrayList<String> childrenUIDs;

    public Parent(String name, String email, String password, String userType, double priceMultiplier, ArrayList<String> childrenUIDs,int balance) {
        super(name, email, password, userType, priceMultiplier,balance);
        this.childrenUIDs = childrenUIDs;
    }


    public ArrayList<String> getChildrenUIDs() {
        return childrenUIDs;
    }

    public void setChildrenUIDs(ArrayList<String> childrenUIDs) {
        this.childrenUIDs = childrenUIDs;
    }

    public void printInfo(){
        super.printInfo();
        toString();
    }

    @Override
    public String toString() {
        return "Parent{" +
                "name=" + getName() +
                "email=" + getEmail() +
                "userType=" + getUserType() +
                "priceMultiplier=" + getPriceMultiplier() +
                "childrenUIDs=" + childrenUIDs +
                '}';
    }
}