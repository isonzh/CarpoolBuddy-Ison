package com.example.carpoolbuddy_ison;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserProfileActivity extends AppCompatActivity {

    private TextView userInfo;
    private TextView enviroment;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // 初始化 FirebaseAuth 和 FirebaseFirestore 实例
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // 初始化 TextView
        userInfo = findViewById(R.id.userInfo);
        enviroment = findViewById(R.id.enviroment);

        // 获取当前用户的 UID
        String userId = mAuth.getUid();
        if (userId == null) {
            // 用户未登录，跳转到登录页面
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
            finish(); // 结束当前活动，避免用户通过返回键回到此页面
            return;
        }

        // 查询用户信息并显示在界面上
        firestore.collection("users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    if (snapshot != null && snapshot.exists()) {
                        try {
                            String name = snapshot.getString("name");
                            userInfo.setText(name); // 设置用户名
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

        // 查询并显示用户车辆预定信息
        countUserVehicleBookings(userId);
    }

    // 查询用户车辆预定信息并显示在界面上
    private void countUserVehicleBookings(String userId) {
        firestore.collection("vehicles").whereArrayContains("ridersUIDs", userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                int totalBookings = 0;
                Map<String, Integer> typeBookings = new HashMap<>();

                for (DocumentSnapshot document : task.getResult()) {
                    totalBookings++;
                    String type = document.getString("vehicleType");
                    if (type != null) {
                        typeBookings.put(type, typeBookings.getOrDefault(type, 0) + 1);
                    }
                }

                // 格式化统计结果为字符串
                StringBuilder resultBuilder = new StringBuilder();
                resultBuilder.append("Total bookings: ").append(totalBookings).append("\n");
                for (Map.Entry<String, Integer> entry : typeBookings.entrySet()) {
                    resultBuilder.append("Type: ").append(entry.getKey()).append(", Bookings: ").append(entry.getValue()).append("\n");
                }
                resultBuilder.append("Total Carbon saved: ").append(String.format("%.2f", totalBookings * 0.133)).append(" kg").append("\n");
                resultBuilder.append("Thank you for caring for the environment!");

                // 在 TextView 中显示统计结果
                String result = resultBuilder.toString();
                enviroment.setText(result);
            } else {
                Log.e("UserProfileActivity", "Error fetching user vehicle bookings", task.getException());
            }
        });
    }

    // 各种按钮点击事件处理方法
    public void seeVehicles(View v){
        Intent intent = new Intent(this, VehicleInfoActivity.class);
        startActivity(intent);
    }

    public void logout(View v){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
    }

    public void goToBalance(View v){
        Intent intent = new Intent(this, AddBalanceActivity.class);
        startActivity(intent);
    }

    public void OpenClose(View v){
        Intent intent = new Intent(this, OpenCloseActivity.class);
        startActivity(intent);
    }
}