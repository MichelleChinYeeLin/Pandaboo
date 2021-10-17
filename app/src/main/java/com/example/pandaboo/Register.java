package com.example.pandaboo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
        EditText mUsername,mPassword,mEmail;
        Button mSignupBtn;
        TextView mLoginBtn;
        FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        mUsername = findViewById(R.id.username);

        mPassword = findViewById(R.id.editPassword);
        //mEmail    = findViewById(R.id.email);
        mSignupBtn = findViewById(R.id.signupButton);







    }
}