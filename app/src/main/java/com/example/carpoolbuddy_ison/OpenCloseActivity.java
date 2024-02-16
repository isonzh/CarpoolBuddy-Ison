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

import com.example.carpoolbuddy_ison.classDictionary.User;
import com.example.carpoolbuddy_ison.classDictionary.Vehicle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class OpenCloseActivity extends AppCompatActivity implements VehiclesInfoViewHolder.OnVehicleListener{

    private FirebaseFirestore firestore;
    private RecyclerView recView;
    private VehiclesInfoAdapter mAdapter;
    private FirebaseAuth mAuth;
    private ArrayList<Vehicle> vehiclesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_close);
        firestore = FirebaseFirestore.getInstance();
        recView = findViewById(R.id.recView);
        mAuth = FirebaseAuth.getInstance();
        vehiclesList = new ArrayList<>();
        getVehicles();
    }

    public void getVehicles() {
        firestore.collection("users").document(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    if (snapshot != null && snapshot.exists()) {
                        try {
                            String name;
                            User user = snapshot.toObject(User.class);
                            name = user.getName();
                            fetchVehiclesFromFirestore(name);
                        } catch (Exception e) {
                            Log.e("ConversionError", "Error converting snapshot to User", e);
                        }
                    } else {

                    }
                } else {

                }
            }
        });
    }

    private void fetchVehiclesFromFirestore(String userName) {
        firestore.collection(Constants.VEHICLE_COLLECTION).whereEqualTo("owner", userName)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                vehiclesList.add(document.toObject(Vehicle.class));
                            }
                            mAdapter = new VehiclesInfoAdapter(vehiclesList, OpenCloseActivity.this);
                            recView.setAdapter(mAdapter);
                            recView.setLayoutManager(new LinearLayoutManager(OpenCloseActivity.this));
                        } else {
                            Log.d("VehiclesInfoActivity", "Error getting documents from db: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onVehicleClick(int position) {
        Intent intent = new Intent(this, VehicleProfileOpenCloseActivity.class);
        intent.putExtra("vehicles", (Parcelable) vehiclesList.get(position));
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
