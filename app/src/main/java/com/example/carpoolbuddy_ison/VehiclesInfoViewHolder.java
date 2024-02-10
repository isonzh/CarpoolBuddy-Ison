package com.example.carpoolbuddy_ison;



import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class VehiclesInfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    protected TextView ownerText;
    protected TextView capacityText;

    public TextView balance;

    private OnVehicleListener onVehicleListener;

    public VehiclesInfoViewHolder(@NonNull View itemView, OnVehicleListener onVehicleListener) {
        super(itemView);

        ownerText = itemView.findViewById(R.id.ownerTextView);
        capacityText = itemView.findViewById(R.id.capacityTextView);
        this.onVehicleListener = onVehicleListener;
        itemView.setOnClickListener(this);
        balance=itemView.findViewById(R.id.balance);
    }

    @Override
    public void onClick(View v) {
        onVehicleListener.onVehicleClick(getAdapterPosition());
    }

    public interface OnVehicleListener{
        void onVehicleClick(int position);
    }
}