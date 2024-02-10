package com.example.carpoolbuddy_ison;


import android.os.Bundle;

import com.example.carpoolbuddy_ison.*;
import com.google.firebase.firestore.FirebaseFirestore;

public class Constants {
    private FirebaseFirestore firestore;

    protected void onCreate(Bundle savedInstanceState) {
        firestore = FirebaseFirestore.getInstance();
    }

    public static final String CAR = "Car";
    public static final String HELICOPTER = "Helicopter";
    public static final String BICYCLE = "Bicycle";
    public static final String SEGWAY = "Segway";

    public static final String OWNER_HINT = "Owner";
    public static final String MODEL_HINT = "Model";
    public static final String CAPACITY_HINT = "Capatity";
    public static final String VEHICLE_ID_HINT = "Vehicle ID";
    public static final String RIDERS_UIDS = "riders uids";
    public static final boolean OPEN = true;
    public static final String BASE_PRICE_HINT = "Base price";
    public static final String BICYCLE_TYPE_HINT = "Bicycle type";
    public static final String WEIGHT_HINT = "Weight";
    public static final String WEIGHT_CAPACITY_HINT = "Weight capacity";

    public static final String RANGE_HINT = "Range";
    public static final String MAX_ALTITUDE_HINT = "max altitude";
    public static final String MAX_AIRSPEED_HINT = "max airspeed";

    public static final String VEHICLE_COLLECTION = "vehicles";

    ;
}