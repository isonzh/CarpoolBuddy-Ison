package com.example.carpoolbuddy_ison;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.carpoolbuddy_ison.classDictionary.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Transaction;

public class AddBalanceActivity extends AppCompatActivity {
    private TextView balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_balance);

        // 初始化TextView
        balance = findViewById(R.id.balance);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firebase = FirebaseFirestore.getInstance();

        String userId = mAuth.getUid();
        if (userId != null) {
            firebase.collection("users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot snapshot = task.getResult();
                        if (snapshot != null && snapshot.exists()) {
                            try {
                                User user = snapshot.toObject(User.class);
                                Long balanceLong = snapshot.getLong("balance");
                                if (balanceLong != null) {
                                    int bal = balanceLong.intValue();
                                    // Assuming balance is a TextView, update its value
                                    balance.setText(String.valueOf(bal));
                                } else {
                                    // Handle the case where balance is null or not an integer
                                    Log.e("DataRetrieval", "Balance is null or not in correct format");
                                }
                            } catch (Exception e) {
                                Log.e("ConversionError", "Error converting balance to int", e);
                            }
                        } else {
                            Log.e("DataRetrieval", "No document exists for the user");
                        }
                    } else {
                        Log.e("FirestoreTask", "Task failed with exception", task.getException());
                    }

                }
            });
        } else {
            // Handle the case where userId is null
            Log.e("AuthError", "User ID is null");
        }
    }

    public void goToUserProfileActivity(View v) {
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }

    public void addBalance(int amountToAdd) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firebase = FirebaseFirestore.getInstance();

        String userId = mAuth.getUid();
        if (userId != null) {
            DocumentReference userDocRef = firebase.collection("users").document(userId);

            // Run a transaction to ensure that the balance update is atomic
            firebase.runTransaction((Transaction.Function<Void>) transaction -> {
                DocumentSnapshot snapshot = transaction.get(userDocRef);

                // Check if the document exists and has a balance field
                if (snapshot.exists() && snapshot.contains("balance")) {
                    Long currentBalance = snapshot.getLong("balance");
                    if (currentBalance != null) {
                        // Calculate the new balance
                        long newBalance = currentBalance + amountToAdd;
                        // Update the balance field in the document
                        transaction.update(userDocRef, "balance", newBalance);
                    } else {
                        // Handle the case where balance is null or not a Long
                        Log.e("BalanceUpdate", "Current balance is null or not a Long");
                    }
                } else {
                    // Handle the case where the document does not exist or does not have a balance field
                    Log.e("BalanceUpdate", "Document does not exist or lacks 'balance' field");
                }
                // This function must return null because it does not return any result
                return null;
            }).addOnSuccessListener(aVoid -> {
                Log.d("Transaction", "Transaction successfully committed!");
            }).addOnFailureListener(e -> {
                Log.e("Transaction", "Transaction failed: ", e);
            });
        } else {
            // Handle the case where userId is null
            Log.e("AuthError", "User ID is null");
        }
    }

    public void addBalance(View v) {
        addBalance(100);
    }
}