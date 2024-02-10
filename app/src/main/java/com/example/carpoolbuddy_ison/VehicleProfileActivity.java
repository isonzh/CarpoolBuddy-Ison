package com.example.carpoolbuddy_ison;

import com.example.carpoolbuddy_ison.classDictionary.Vehicle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class VehicleProfileActivity extends AppCompatActivity implements View.OnClickListener{
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
        setContentView(R.layout.activity_vehicle_profile);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        if(getIntent().hasExtra("vehicle")) {
            selectedVehicle = (Vehicle) getIntent().getParcelableExtra("vehicle");

            //Common fields
            vehicleTypeTextView = findViewById(R.id.vehicleTypeDataTextView);
            ownerTextView = findViewById(R.id.ownerDataTextView);
            modelTextView = findViewById(R.id.modelDataTextView);
            maxCapacityTextView = findViewById(R.id.maxCapacityDataTextView);
            remainingCapacityTextView = findViewById(R.id.remainingCapacityDataTextView);
            basePriceTextView = findViewById(R.id.basePriceDataTextView);
            bookedUIDs = findViewById(R.id.bookedUIDsDataTextView);

            vehicleTypeTextView.setText(selectedVehicle.getVehicleType());
            ownerTextView.setText(selectedVehicle.getOwner());
            modelTextView.setText(selectedVehicle.getModel());
            maxCapacityTextView.setText(String.valueOf(selectedVehicle.getCapacity()));
            remainingCapacityTextView.setText(String.valueOf(selectedVehicle.getRemainingCapacity()));
            basePriceTextView.setText(String.valueOf(selectedVehicle.getBasePrice()));
            bookedUIDs.setText(selectedVehicle.getRidersUIDs().toString());

        }
        bookRideButton = findViewById(R.id.bookRideButton);
        bookRideButton.setOnClickListener(this);
    }

    public void bookTheRide() {
        if(selectedVehicle.getRemainingCapacity() == 1) {
            firestore.collection("vehicles").document(selectedVehicle.getVehicleID())
                    .update("open", false);
        }

        firestore.collection("vehicles").document(selectedVehicle.getVehicleID())
                .update("remainingCapacity", selectedVehicle.getRemainingCapacity() - 1);

        selectedVehicle.addRidersUIDs(mAuth.getUid());
        firestore.collection("vehicles").document(selectedVehicle.getVehicleID())
                .update("ridersUIDs", selectedVehicle.getRidersUIDs())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(getApplicationContext(), VehicleInfoActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i == bookRideButton.getId()) {
            bookTheRide();
        }
    }

    public void goToVehicleInfoActivity(View V){
        Intent intent = new Intent(this, VehicleInfoActivity.class);
        startActivity(intent);
    }
}