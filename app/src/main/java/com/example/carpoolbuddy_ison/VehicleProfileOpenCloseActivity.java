package com.example.carpoolbuddy_ison;

import com.example.carpoolbuddy_ison.classDictionary.Vehicle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class VehicleProfileOpenCloseActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private Vehicle selectedVehicle;
    private TextView ownerTextView;
    private TextView modelTextView;
    private TextView maxCapacityTextView;
    private TextView remainingCapacityTextView;
    private TextView vehicleTypeTextView;
    private TextView basePriceTextView;
    private TextView bookedUIDs;
    private Button bookRideButton;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_info_openclose);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();


        if(getIntent().hasExtra("vehicle")) {
            selectedVehicle = getIntent().getParcelableExtra("vehicle");

            vehicleTypeTextView = findViewById(R.id.vehicleTypeDataTextView);
            ownerTextView = findViewById(R.id.ownerDataTextView);
            modelTextView = findViewById(R.id.modelDataTextView);
            maxCapacityTextView = findViewById(R.id.maxCapacityDataTextView);
            remainingCapacityTextView = findViewById(R.id.remainingCapacityDataTextView);
            basePriceTextView = findViewById(R.id.basePriceDataTextView);
            bookedUIDs = findViewById(R.id.bookedUIDsDataTextView);
            TextView openClose =findViewById(R.id.openClose);

            vehicleTypeTextView.setText(selectedVehicle.getVehicleType());
            ownerTextView.setText(selectedVehicle.getOwner());
            modelTextView.setText(selectedVehicle.getModel());
            maxCapacityTextView.setText(String.valueOf(selectedVehicle.getCapacity()));
            remainingCapacityTextView.setText(String.valueOf(selectedVehicle.getRemainingCapacity()));
            basePriceTextView.setText(String.valueOf(selectedVehicle.getBasePrice()));
            bookedUIDs.setText(selectedVehicle.getRidersUIDs().toString());
            openClose.setText(selectedVehicle.getOpen());
        }
    }



    public void goToOpenCloseActivity(View V){
        Intent intent = new Intent(this, OpenCloseActivity.class);
        startActivity(intent);
    }
    public void Open(View v){
        firestore.collection("vehicles").document(selectedVehicle.getVehicleID())
                .update("open", true);
        Intent intent = new Intent(this, OpenCloseActivity.class);
        startActivity(intent);
    }
    public void Close(View v){
        firestore.collection("vehicles").document(selectedVehicle.getVehicleID())
                .update("open", false);
        Intent intent = new Intent(this, OpenCloseActivity.class);
        startActivity(intent);
    }
}

