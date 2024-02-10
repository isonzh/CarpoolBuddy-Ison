package com.example.carpoolbuddy_ison;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import com.example.carpoolbuddy_ison.classDictionary.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class VehicleInfoActivity extends AppCompatActivity implements VehiclesInfoViewHolder.OnVehicleListener{

    private FirebaseFirestore firestore;
    private RecyclerView recView;
    private VehiclesInfoAdapter mAdapter;

    //added for testing
    private ArrayList<Vehicle> vehiclesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicles_info);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        recView = (RecyclerView) findViewById(R.id.recView);
        vehiclesList = new ArrayList<>();
        getVehicles();
    }

    public void getVehicles() {
        TaskCompletionSource<String> getAllRidesTask = new TaskCompletionSource<>();

        firestore.collection(Constants.VEHICLE_COLLECTION).whereEqualTo("open", true)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                vehiclesList.add(document.toObject(Vehicle.class));
                            }
                            getAllRidesTask.setResult(null);
                        }
                        else {
                            Log.d("VehiclesInfoActivity", "Error getting documents from db: ", task.getException());
                        }
                    }
                });
        getAllRidesTask.getTask().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                mAdapter = new VehiclesInfoAdapter(vehiclesList,VehicleInfoActivity.this);
                recView.setAdapter(mAdapter);
                recView.setLayoutManager(new LinearLayoutManager(VehicleInfoActivity.this));
            }
        });
    }


    public void goToAddVehicleActivity(View V){
        Intent intent = new Intent(this, AddVehicleActivity.class);
        startActivity(intent);
    }

    @Override
    public void onVehicleClick(int position) {
        Intent intent = new Intent(this, VehicleProfileActivity.class);
        intent.putExtra("vehicle", (Parcelable) vehiclesList.get(position));
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void goToUserProfileActivity(View V){
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }
}