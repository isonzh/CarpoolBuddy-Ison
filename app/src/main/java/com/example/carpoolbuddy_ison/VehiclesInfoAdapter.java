package com.example.carpoolbuddy_ison;


import android.annotation.SuppressLint;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpoolbuddy_ison.classDictionary.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class VehiclesInfoAdapter extends RecyclerView.Adapter<VehiclesInfoViewHolder> {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Vehicle> mData;
    private VehiclesInfoViewHolder.OnVehicleListener mOnListener;


    public VehiclesInfoAdapter(ArrayList<Vehicle> mData, VehiclesInfoViewHolder.OnVehicleListener onVehicleListener){
        this.mData = mData;
        this.mOnListener = onVehicleListener;
    }

    @NonNull
    @Override
    public VehiclesInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_vehicles_row_view, parent,false);
        VehiclesInfoViewHolder holder = new VehiclesInfoViewHolder(myView, mOnListener);
        return holder;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull VehiclesInfoViewHolder holder, int position) {
        holder.ownerText.setText(mData.get(position).getOwner());
        holder.capacityText.setText(String.valueOf(mData.get(position).getRemainingCapacity()));
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        FirebaseFirestore firebase = FirebaseFirestore.getInstance();
//        String userId = mAuth.getUid();
//        if (userId != null) {
//            firebase.collection("users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                    if (task.isSuccessful()) {
//                        DocumentSnapshot snapshot = task.getResult();
//                        if (snapshot != null && snapshot.exists()) {
//                            try {
//                                User user = snapshot.toObject(User.class);
//                                Long balanceLong = snapshot.getLong("balance");
//                                if (balanceLong != null) {
//                                    int bal = balanceLong.intValue();
//                                    // Assuming balance is a TextView, update its value
//                                    holder.balance.setText(String.valueOf(bal));
//                                } else {
//                                    // Handle the case where balance is null or not an integer
//                                    Log.e("DataRetrieval", "Balance is null or not in correct format");
//                                }
//                            } catch (Exception e) {
//                                Log.e("ConversionError", "Error converting balance to int", e);
//                            }
//                        } else {
//                            Log.e("DataRetrieval", "No document exists for the user");
//                        }
//                    } else {
//                        Log.e("FirestoreTask", "Task failed with exception", task.getException());
//                    }
//                }
//            });
//        } else {
//            // Handle the case where userId is null
//            Log.e("AuthError", "User ID is null");
//        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}