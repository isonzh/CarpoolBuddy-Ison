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
        holder.costText.setText(""+mData.get(position).getBasePrice());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}