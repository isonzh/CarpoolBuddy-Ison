package com.example.carpoolbuddy_ison;

import androidx.appcompat.app.AppCompatActivity;
import com.example.carpoolbuddy_ison.VehicleProfileActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.carpoolbuddy_ison.classDictionary.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.Objects;

public class UserProfileActivity extends AppCompatActivity {

    private TextView userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        userInfo = this.findViewById(R.id.userInfo) ;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firebase = FirebaseFirestore.getInstance();
        String userId = mAuth.getUid();
        if (userId == null) {
            // 用户未登录，跳转到登录页面
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
            finish(); // 结束当前活动，避免用户通过返回键回到此页面
            return;
        }

        firebase.collection("users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    if (snapshot != null && snapshot.exists()) {
                        try {
//                            User user = snapshot.toObject(User.class);
                            String name = snapshot.getString("name");
                            userInfo.setText(name); // 假设 User 类有 getName 方法
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
        Intent intent = new Intent(this,AddBalanceActivity.class);
        startActivity(intent);
    }
    public void OpenClose(View v){
        Intent intent = new Intent(this,OpenCloseActivity.class);
        startActivity(intent);
    }
}