package com.example.pandaboo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
    }

    /**
     * Allows the user to switch to different activities
     * @param view
     */
    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.loginButton:
                Intent toHome = new Intent(this, HomeMain.class);
                startActivity(toHome);
                break;
            //Add case for register new account
            //Add case for reset password
        }
    }
}