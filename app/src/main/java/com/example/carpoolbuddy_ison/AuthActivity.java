package com.example.carpoolbuddy_ison;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class AuthActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    private EditText emailField;
    private EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        emailField = findViewById(R.id.editTextEmail);
        passwordField = findViewById(R.id.editTextPassword);

    }

    public void updateUI(FirebaseUser currentUser){
        if(currentUser != null){
            Intent intent = new Intent(this, UserProfileActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void signup(View V){
        Intent intent = new Intent(this, CreateUserActivity.class);
        startActivity(intent);
    }
    public void signIn(View V) {
        String userEmail = emailField.getText().toString();
        String userPassword = passwordField.getText().toString();
        if (!userEmail.endsWith("cis.edu.hk")) {
            showToast("Please use a valid 'cis.edu.hk' email address.");
            return;
        }
        if (userEmail.isEmpty() || userPassword.isEmpty()) {
            showToast("Please fill in all required fields.");
            return;
        }

        mAuth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        showToast("Successfully signed in");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        showToast("Sign in failed.");
                        updateUI(null);
                    }
                });
    }

    public void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    public void onStart() {
        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null) {
//            Intent intent = new Intent(this, UserProfileActivity.class);
//            startActivity(intent);
//        }
    }

//    public void signIn(View V){
//        System.out.println("Log In");
//        String emailString = emailField.getText().toString();
//        String passwordString = passwordField.getText().toString();
//        System.out.println(String.format("email: %s and password: %s", emailString, passwordString));
//
//        FirebaseUser mUser = mAuth.getCurrentUser();
//        updateUI(mUser);
//    }


}