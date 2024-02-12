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

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class OpenCloseActivity extends AppCompatActivity implements VehiclesInfoViewHolder.OnVehicleListener{

    private FirebaseFirestore firestore;
    private RecyclerView recView;
    private VehiclesInfoAdapter mAdapter;

    //added for testing
    private ArrayList<Vehicle> vehiclesList;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_close);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        recView = (RecyclerView) findViewById(R.id.recView);

        vehiclesList = new ArrayList<>();
        String userId = mAuth.getUid();
        getVehicles();

        firestore.collection("users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    if (snapshot != null && snapshot.exists()) {
                        try {
//                            User user = snapshot.toObject(User.class);
                            name = snapshot.getString("name");
                             // 假设 User 类有 getName 方法
                            // 其他代码
                        } catch (Exception e) {
                            Log.e("ConversionError", "Error converting snapshot to User", e);
                        }
                    } else {
                        // 文档不存在的处理
                        // 例如显示错误信息或日志记录
                    }
                } else {
                    // 查询失败的处理
                    // 例如显示错误信息或日志记录
                }
            }
        });
    }

    public void getVehicles() {
        TaskCompletionSource<String> getAllRidesTask = new TaskCompletionSource<>();

        firestore.collection(Constants.VEHICLE_COLLECTION).whereEqualTo("owner", name)
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
                mAdapter = new VehiclesInfoAdapter(vehiclesList,OpenCloseActivity.this);
                recView.setAdapter(mAdapter);
                recView.setLayoutManager(new LinearLayoutManager(OpenCloseActivity.this));
            }
        });
    }



    @Override
    public void onVehicleClick(int position) {
        Intent intent = new Intent(this, VehicleProfileOpenCloseActivity.class);
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