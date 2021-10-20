package com.example.pandaboo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseUser user;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private EditText emailEditText,passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
     * Initialize user interface binding for login button
     * login email edittext and login password edittext
     */
    private void initializeUI(){

        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(this);

        emailEditText = findViewById(R.id.loginEmail);
        passwordEditText = findViewById(R.id.loginPassword);
    }


    /**
     * Sign in user with email and password
     *
     * pre-condition: email and password must no be empty
     *
     * If the task is successful, redirect the user to main page
     * else the error will display at the edit text
     *
     */
    private void login(){
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // if the email edit text is empty
        // display the error, else no error
        emailEditText.setError((email.isEmpty())? "Please input your email" : null);

        // if the password edit text is empty
        // display the error, else no error
        passwordEditText.setError((password.isEmpty())? "Please input your password" : null);


        // if both email field and password field is not empty
        // login user into firebase and redirect to main page
        if(!email.isEmpty() && !password.isEmpty()){

            //start process of sign in
            auth.signInWithEmailAndPassword(email,password)

                    .addOnCompleteListener(task -> {

                        //check is login successful
                        if(task.isSuccessful()){
                            user = auth.getCurrentUser();
                            Intent toHome = new Intent(MainActivity.this, HomeMain.class);
                            startActivity(toHome); // go to main page
                        }else{
                            //display error
                            Toast.makeText(MainActivity.this,
                                    Objects.requireNonNull(task.getException()).toString(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    /**
     * Allows the user to switch to different activities
     * @param view  view a view or button that is triggered with user click
     */
    @Override
    public void onClick(View view){

        if(view instanceof Button){
            if (view.getId() == R.id.loginButton) {
                login();
            }else if(view.getId() == R.id.registerUserBtn){
                // redirect user to register page
            }
        }

    }
}