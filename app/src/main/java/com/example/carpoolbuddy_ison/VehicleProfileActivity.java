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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
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
            selectedVehicle = getIntent().getParcelableExtra("vehicle");

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

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i == bookRideButton.getId()) {
            String userId = mAuth.getUid();
            firestore.collection("users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot snapshot = task.getResult();
                        if (snapshot != null && snapshot.exists()) {
                            try {
                                Long balanceDouble = snapshot.getLong("balance");
                                if (balanceDouble != null) {
                                    int balance = balanceDouble.intValue();
                                    proceedWithBooking(balance);
                                } else {
                                    Log.e("DataRetrieval", "Balance is null or not in correct format");
                                }
                            } catch (Exception e) {
                                Log.e("ConversionError", "Error converting balance to double", e);
                            }
                        } else {
                            Log.e("DataRetrieval", "No document exists for the user");
                        }
                    } else {
                        Log.e("FirestoreTask", "Task failed with exception", task.getException());
                    }
                }
            });
        }
    }

    private void proceedWithBooking(int balance) {
        if (balance == 0) {
            Toast.makeText(this,"BookingError User balance is 0, cannot book",Toast.LENGTH_SHORT).show();
            return;
        }

        if(selectedVehicle.getRemainingCapacity() == 1) {
            firestore.collection("vehicles").document(selectedVehicle.getVehicleID())
                    .update("open", false);
        }

        double basePrice = selectedVehicle.getBasePrice();
        if (balance >= basePrice) {
            double newBalance = balance - basePrice;
            firestore.collection("users").document(mAuth.getUid())
                    .update("balance", newBalance);

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
        } else {
            Toast.makeText(this, "BookingError Insufficient balance to book ride", Toast.LENGTH_SHORT).show();
        }
    }

    public void goToVehicleInfoActivity(View V){
        Intent intent = new Intent(this, VehicleInfoActivity.class);
        startActivity(intent);
    }
}