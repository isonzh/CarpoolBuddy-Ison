package com.example.carpoolbuddy_ison;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.carpoolbuddy_ison.classDictionary.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CreateUserActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Spinner roleSpinner;
    private FirebaseFirestore firestore;
    private LinearLayout layout;
    private EditText nameField;
    private EditText emailField;
    private EditText passwordField;
    private EditText priceMultiplierField;
    private EditText ownedVehiclesField;
    private ArrayList<String> ownedVehicles = new ArrayList<>();
    private EditText inSchoolTitleField;
    private EditText gradYearField;
    private String roleSelected;
    private EditText childrenUIDsField;
    private ArrayList<String> childrenUIDs = new ArrayList<>();
    private EditText parentUIDsField;
    private ArrayList<String> parentUIDs =  new ArrayList<>();

    private int balance;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        layout = findViewById(R.id.createUserLayout);
        roleSpinner = findViewById(R.id.selectedRoleSpinner);
        setupSpinner();
        ownedVehicles = new ArrayList<>();
    }


    private void setupSpinner(){
        String[] userTypes = {"Student", "Teacher", "Alumni", "Parent"};
        // add user types to spinner
        ArrayAdapter<String> langArrAdapter = new ArrayAdapter<String>(CreateUserActivity.this,
                android.R.layout.simple_spinner_item, userTypes);
        langArrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(langArrAdapter);

        //triggered whenever user selects something different
        roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                roleSelected = parent.getItemAtPosition(position).toString();
                addFields();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(CreateUserActivity.this, "please choose your role!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addFields(){
        commonFields();
        if(roleSelected.equals("Alumni")){
            gradYearField = new EditText(this);
            gradYearField.setHint("Graduation Year");
            layout.addView(gradYearField);
        }
        if(roleSelected.equals("Teacher")){
            inSchoolTitleField = new EditText(this);
            inSchoolTitleField.setHint("School Title");
            layout.addView(inSchoolTitleField);
        }
        if(roleSelected.equals("Parent")){
            childrenUIDsField = new EditText(this);
            childrenUIDsField.setHint("Childrens UID");
            layout.addView(childrenUIDsField);
        }
        if(roleSelected.equals("Student")){
            gradYearField = new EditText(this);
            gradYearField.setHint("Graduation Year");
            layout.addView(gradYearField);
            parentUIDsField = new EditText(this);
            parentUIDsField.setHint("Parent UID");
            layout.addView(parentUIDsField);
        }
    }

    public void commonFields(){
        layout.removeAllViewsInLayout();
        nameField = new EditText(this);
        nameField.setHint("Name");
        layout.addView(nameField);
        emailField = new EditText(this);
        emailField.setHint("Email");
        layout.addView(emailField);
        passwordField = new EditText(this);
        passwordField.setHint("Password");
        layout.addView(passwordField);
        priceMultiplierField = new EditText(this);
        priceMultiplierField.setHint("Price Multiplier");
        layout.addView(priceMultiplierField);
        ownedVehiclesField = new EditText(this);
        ownedVehiclesField.setHint("Number of owned vehicles");
        layout.addView(ownedVehiclesField);
    }

    public void signUp(View V){
        String nameString = nameField.getText().toString();
        String emailString = emailField.getText().toString();
        String passwordString = passwordField.getText().toString();
        double priceMultiplierDoub = Double.parseDouble(priceMultiplierField.getText().toString());
        String ownedVehiclesString = ownedVehiclesField.getText().toString();
        ownedVehicles.add(ownedVehiclesString);
        String roleString = roleSelected;
        String gradYearInt = gradYearField.getText().toString();
        int balance=100;
        if (!emailString.endsWith("cis.edu.hk")) {
            Toast.makeText(CreateUserActivity.this,"Use a cis email",Toast.LENGTH_SHORT).show();
            return;
        }



        mAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d("SIGN UP", "Successfully signed up");
                    Toast.makeText(CreateUserActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                    if(roleSelected.equals("Alumni")) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Alumni newUser = new Alumni(nameString, emailString, passwordString, roleString, priceMultiplierDoub, gradYearInt,balance);

                        firestore.collection("users").document(user.getUid()).set(newUser);
                        updateUI(user);
                    }
                    if(roleSelected.equals("Teacher")) {
                        FirebaseUser user = mAuth.getCurrentUser();

                        String inSchoolTitleString = inSchoolTitleField.getText().toString();
                        Teacher newUser = new Teacher(nameString, emailString, passwordString, roleString, priceMultiplierDoub, inSchoolTitleString,balance);
                        firestore.collection("users").document(user.getUid()).set(newUser);
                        updateUI(user);

                    }
                    if(roleSelected.equals("Parent")) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        String childrenUIDsString = childrenUIDsField.getText().toString();

                        ArrayList<String> childrenUIDs = new ArrayList<>();

                        // 假设子用户ID是以逗号分隔的，分割字符串并添加到列表中
                        String[] ids = childrenUIDsString.split(",");
                        for (String id : ids) {
                            childrenUIDs.add(id.trim()); // 添加到列表中，并去除可能的空格
                        }
                        Parent newUser = new Parent( nameString, emailString, passwordString, roleString, priceMultiplierDoub, childrenUIDs,balance);

                        firestore.collection("users").document(user.getUid()).set(newUser);
                        updateUI(user);

                    }
                    if(roleSelected.equals("Student")){
                        FirebaseUser user = mAuth.getCurrentUser();

                        String parentUIDsString = parentUIDsField.getText().toString();
                        ArrayList<String> parentUIDs = new ArrayList<>();

                        // 分割字符串并添加到列表中
                        String[] ids = parentUIDsString.split(",");
                        for (String id : ids) {
                            parentUIDs.add(id.trim()); // 添加到列表中，并去除可能的空格
                        }
                        Student newUser = new Student(nameString, emailString, passwordString, roleString, priceMultiplierDoub, gradYearInt, parentUIDs,balance);

                        firestore.collection("users").document(user.getUid()).set(newUser);
                        updateUI(user);

                    }
                }
                else{
                    Log.d("SIGN UP", "createUserWithEmail:failure", task.getException());
                    updateUI(null);
                }
            }
        });
//
//        mAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
//                    Log.d("SIGN UP", "Successfully signed up");
//                    Toast.makeText(CreateUserActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
//                    FirebaseUser user = mAuth.getCurrentUser();
//                    if(roleSelected.equals("Alumni")) {
//                        String gradYearInt = gradYearField.getText().toString();
//
//                        Alumni newUser = new Alumni(String.valueOf(uidGenerator), nameString, emailString, passwordString, roleString, priceMultiplierDoub, ownedVehicles, gradYearInt);
//                        uidGenerator++;
//                        firestore.collection("users").document(user.getUid()).set(newUser);
//                    }
//                    if(roleSelected.equals("Teacher")) {
//                        String inSchoolTitleString = inSchoolTitleField.getText().toString();
//                        Teacher newUser = new Teacher(String.valueOf(uidGenerator), nameString, emailString, passwordString, roleString, priceMultiplierDoub, ownedVehicles, inSchoolTitleString);
//                        uidGenerator++;
//                        firestore.collection("users").document(user.getUid()).set(newUser);
//                    }
//                    if(roleSelected.equals("Parent")) {
//                        String childrenUIDsString = childrenUIDsField.getText().toString();
//
//                        ArrayList<String> childrenUIDs = new ArrayList<>();
//                        String[] ids = childrenUIDsString.split(";");
//                        for(String id : ids){
//                            childrenUIDs.add((id.trim()));
//                        }
//
//                        Parent newUser = new Parent(String.valueOf(uidGenerator), nameString, emailString, passwordString, roleString, priceMultiplierDoub, ownedVehicles, childrenUIDs);
//                        uidGenerator++;
//                        firestore.collection("users").document(user.getUid()).set(newUser);
//                    }
//                    if(roleSelected.equals("Student")){
//                        String gradYearInt = gradYearField.getText().toString();
//
//                        String parentUIDsString = parentUIDsField.getText().toString();
//
//                        ArrayList<String> parentUIDs = new ArrayList<>();
//                        String[] ids = parentUIDsString.split(";");
//
//                        for(String id : ids){
//                            parentUIDs.add((id.trim()));
//                        }
//
//                        Student newUser = new Student(String.valueOf(uidGenerator), nameString, emailString, passwordString, roleString, priceMultiplierDoub, ownedVehicles, gradYearInt, parentUIDs);
//                        uidGenerator++;
//                        firestore.collection("users").document(user.getUid()).set(newUser);
//                    }
//                    updateUI(user);
//                }
//                else{
//                    Log.d("SIGN UP", "createUserWithEmail:failure", task.getException());
//                    Toast.makeText(CreateUserActivity.this, "Registration failure", Toast.LENGTH_SHORT).show();
//
//                    updateUI(null);
//                }
//            }
//        });

    }

    public void updateUI(FirebaseUser currentUser){
        if(currentUser != null){
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
        }
    }
}