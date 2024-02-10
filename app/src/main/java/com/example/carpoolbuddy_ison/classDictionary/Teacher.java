package com.example.carpoolbuddy_ison.classDictionary;

import java.util.ArrayList;

public class Teacher extends User{
    private String inSchoolTitle;

    public Teacher(String name, String email, String password, String userType, double priceMultiplier, String inSchoolTitle,int balance) {
        super(name, email, password, userType, priceMultiplier,balance);
        this.inSchoolTitle = inSchoolTitle;
    }


    public String getInSchoolTitle() {
        return inSchoolTitle;
    }

    public void setInSchoolTitle(String inSchoolTitle) {
        this.inSchoolTitle = inSchoolTitle;
    }

    public void printInfo(){
        super.printInfo();
        toString();
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name=" + getName() +
                "email=" + getEmail() +
                "userType=" + getUserType() +
                "priceMultiplier=" + getPriceMultiplier() +
//                "ownedVehicles=" + getOwnedVehicles() +
                "inSchoolTitle='" + inSchoolTitle + '\'' +
                '}';
    }
}