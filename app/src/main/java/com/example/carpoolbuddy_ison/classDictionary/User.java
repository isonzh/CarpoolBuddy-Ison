package com.example.carpoolbuddy_ison.classDictionary;

import java.util.ArrayList;

public class User {
    private String name;
    private String email;
    private String password;
    private String userType;
    private double priceMultiplier;
    private int balance;

//    private ArrayList<String> ownedVehicles;

    public User() {
        // Firestore 需要的无参构造函数
    }
    public User(String name, String email, String password, String userType, double priceMultiplier, int balance) {

        this.name = name;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.priceMultiplier = priceMultiplier;
        this.balance=balance;

//        this.ownedVehicles = ownedVehicles;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword(){ return password;}

    public void setPassword(String password) {this.password = password;};

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public double getPriceMultiplier() {
        return priceMultiplier;
    }

    public void setPriceMultiplier(double priceMultiplier) {
        this.priceMultiplier = priceMultiplier;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

//    public ArrayList<String> getOwnedVehicles() {
//        return ownedVehicles;
//    }
//
//    public void setOwnedVehicles(ArrayList<String> ownedVehicles) {
//        this.ownedVehicles = ownedVehicles;
//    }

    public void printInfo(){
        System.out.println(this.name + this.email + this.password + this.userType + this.priceMultiplier
               );
    }

    @Override
    public String toString() {
        return "User{" +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userType='" + userType + '\'' +
                ", priceMultiplier=" + priceMultiplier +
                '}';
    }
}