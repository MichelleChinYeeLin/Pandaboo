package com.example.pandaboo;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;

public class Register extends AppCompatActivity implements View.OnClickListener {
    EditText mUsername,mPassword,mEmail;
    Button mSignupBtn;
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

        mUsername = findViewById(R.id.registerUsername);
        mPassword = findViewById(R.id.registerPassword);
        mEmail = findViewById(R.id.registerEmail);

        mSignupBtn = findViewById(R.id.signupButton);

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
        String username = mUsername.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        //display error if the edit text field is empty
        mUsername.setError(username.isEmpty()? "Please input your username":null);
        mPassword.setError(password.isEmpty()? "Please input your password":null);
        mEmail.setError(email.isEmpty()? "Please input your email":null);

        //if the username, password and email is not empty
        //register the user by email and password
        if(!username.isEmpty() && !password.isEmpty() && !email.isEmpty()){

            // register the user with email and password
            fAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            user = fAuth.getCurrentUser();

                            UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username).build();
                            user.updateProfile(request);

                            //go to login page
                            Intent toLogin = new Intent(this, MainActivity.class);
                            startActivity(toLogin);
                        }else{

                            Toast.makeText(Register.this,
                                    Objects.requireNonNull(task.getException()).toString(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    @Override
    public void onClick(View v) {

        if(v instanceof Button){
            if(v.getId()==R.id.signupButton){
                signUp();
            }else if(v.getId()==R.id.clickSignIn){
                Intent toLogin = new Intent(this, MainActivity.class);
                startActivity(toLogin);
            }
        }
    }
}