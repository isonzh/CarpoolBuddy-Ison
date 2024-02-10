package com.example.carpoolbuddy_ison.classDictionary;

import java.util.ArrayList;

public class Alumni extends User {
    private String graduateYear;

    public Alumni(String name, String email, String password, String userType, double priceMultiplier, String graduateYear,int balance) {
        super(name, email, password, userType, priceMultiplier,balance);
        this.graduateYear = graduateYear;
    }


    public String getGraduateYear() {
        return graduateYear;
    }

    public void setGraduateYear(String graduateYear) {
        this.graduateYear = graduateYear;
    }

    public void printInfo(){
        super.printInfo();
        toString();
    }

    @Override
    public String toString() {
        return "Alumni{" +
                "name=" + getName() +
                "email=" + getEmail() +
                "userType=" + getUserType() +
                "priceMultiplier=" + getPriceMultiplier() +
                "graduateYear='" + graduateYear + '\'' +
                '}';
    }
}