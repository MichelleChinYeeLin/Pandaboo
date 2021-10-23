package com.example.pandaboo;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Register extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    EditText mPassword,mEmail;
    Button mSignupBtn;
    TextView mSignInBtn;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        //the initializeUI method to set id to the variables
        initializeUI();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //get the current logged in user information
        user = FirebaseAuth.getInstance().getCurrentUser();

        // if user already login redirects to home page
        if (user != null) {
            Intent toHome = new Intent(this, HomeMain.class);
            startActivity(toHome);
        }
    }

    /**
     * Initialize UI is for configuration the view of
     * the app like edit text, buttons, image, etc
     * and bind with the id set.
     *
     */
    private void initializeUI(){


        mPassword = findViewById(R.id.registerPassword);
        mPassword.addTextChangedListener(this);

        mEmail = findViewById(R.id.registerEmail);
        mEmail.addTextChangedListener(this);

        mSignupBtn = findViewById(R.id.SignupButton);
        mSignupBtn.setEnabled(false);
        mSignupBtn.setOnClickListener(this);

        mSignInBtn = findViewById(R.id.clickSignIn);
        mSignInBtn.setOnClickListener(this);
    }

    /**
     * Sign up or Register user into the firebase.
     * Precondition: the username, email, password must not be empty
     * After the signup is complete, it redirect the user to the
     * login page.
     *
     */
    private void signUp(){

        //get all the input from the user
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        //display error if the edit text field is empty
        mPassword.setError(password.isEmpty()? "Please input your password":null);
        mEmail.setError(email.isEmpty()? "Please input your email":null);

        //if the username, password and email is not empty
        //register the user by email and password
        if(!password.isEmpty() && !email.isEmpty()){

            // register the user with email and password
            fAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            user = fAuth.getCurrentUser();

                            //go to home page
                            Intent toHome = new Intent(this, HomeMain.class);
                            startActivity(toHome);
                        }else{

                            Toast.makeText(Register.this,
                                    Objects.requireNonNull(task.getException()).getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.SignupButton) {
            signUp();
        }else if(v.getId()==R.id.clickSignIn){
            Intent toLogin = new Intent(this, MainActivity.class);
            startActivity(toLogin);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //if the text field is empty, disable sign up button
        if(s.length()==0){
            mSignupBtn.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        //if both text field is not empty, enable signup button
        mSignupBtn.setEnabled(!email.isEmpty() && !password.isEmpty());
    }
}