package com.example.carpoolbuddy_ison;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.carpoolbuddy_ison.classDictionary.Vehicle;
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
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_info_openclose);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        ownerTextView = findViewById(R.id.ownerDataTextView);
        modelTextView = findViewById(R.id.modelDataTextView);
        maxCapacityTextView = findViewById(R.id.maxCapacityDataTextView);
        remainingCapacityTextView = findViewById(R.id.remainingCapacityDataTextView);
        basePriceTextView = findViewById(R.id.basePriceDataTextView);
        bookedUIDs = findViewById(R.id.bookedUIDsDataTextView);
        vehicleTypeTextView = findViewById(R.id.vehicleTypeDataTextView);

        if(getIntent().hasExtra("vehicles")) {
            selectedVehicle = getIntent().getParcelableExtra("vehicles");

            if(selectedVehicle != null){
                ownerTextView.setText(selectedVehicle.getOwner());
                modelTextView.setText(selectedVehicle.getModel());
                maxCapacityTextView.setText(String.valueOf(selectedVehicle.getCapacity()));
                remainingCapacityTextView.setText(String.valueOf(selectedVehicle.getRemainingCapacity()));
                basePriceTextView.setText(String.valueOf(selectedVehicle.getBasePrice()));
                bookedUIDs.setText(selectedVehicle.getRidersUIDs().toString());
                vehicleTypeTextView.setText(selectedVehicle.getVehicleType());
            }
        }
    }

    public void goToOpenCloseActivity(View V){
        Intent intent = new Intent(this, OpenCloseActivity.class);
        startActivity(intent);
    }

    public void Open(View v){
        updateVehicleStatus(true);
    }

    public void Close(View v){
        updateVehicleStatus(false);
    }

    private void updateVehicleStatus(boolean isOpen){
        if(selectedVehicle != null){
            firestore.collection("vehicles").document(selectedVehicle.getVehicleID())
                    .update("open", isOpen)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(VehicleProfileOpenCloseActivity.this, OpenCloseActivity.class);
                            startActivity(intent);
                        } else {
                            Log.e("VehicleProfileOpenCloseActivity", "Error updating vehicle status", task.getException());
                        }
                    });
        }
    }
}
