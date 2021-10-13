package com.example.pandaboo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
        EditText mUsername,mPassword,mEmail;
        Button mRegisterBtn;
        Textview mLoginBtn;
        FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        /** to connect to XML file cannot be done correctly until XML finish
        mUsername = findViewById(R.id.username);

        mPassword = findViewById(R.id.password);
        mEmail    = findViewById(R.id.email);
        mRegisterBtn = findViewById(R.id.register): **/


        mPassword = findViewById(R.id.passwordArea);




    }
}