package com.example.carpoolbuddy_ison;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.carpoolbuddy_ison.classDictionary.Vehicle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText emailField;
    private EditText nameField;
    private EditText newPasswordField;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        emailField = findViewById(R.id.editTextText);
        nameField = findViewById(R.id.editTextText2);
        newPasswordField = findViewById(R.id.editTextText3);

    }

    public void goToAuthActivity(View V) {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
    }

    public void confirm(View v) {
        String userEmail = emailField.getText().toString();
        String name = nameField.getText().toString();
        String userId = mAuth.getUid();
        TaskCompletionSource<String> getAllRidesTask = new TaskCompletionSource<>();

        firestore.collection("users").whereEqualTo("open", true)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if ((document.get("email") == userEmail)&&(document.get("name") == name)) {
                                        showToast("succesful");

                                        changePassword(document.getId());
                                }
                            }
                        } else {
                            showToast("unsuccesful");
                        }
                    }
                });
    }
    public void changePassword(String d){
        String newPassword = newPasswordField.getText().toString();
        firestore.collection("users").document(d).update("password",newPassword);
    }
}