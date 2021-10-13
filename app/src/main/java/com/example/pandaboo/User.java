package com.example.pandaboo;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

private class Textview {
    Textview mLoginBtn;
}

public class Register extends AppCompatActivity {
    EditText mUsername,mPassword;
    Button mLoginBtn;
    FirebaseAuth fAuth;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        //to connect to XML file cannot be done correctly until XML finish
         mUsername = findViewById(R.id.username);
         mPassword = findViewById(R.id.passwordArea);
         mLoginBtn = findViewById(R.id.loginButton);


    }

