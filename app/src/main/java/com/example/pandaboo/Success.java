package com.example.pandaboo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Success extends AppCompatActivity implements View.OnClickListener{

    private ImageView redoButton;
    private ImageView homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success);

        redoButton = findViewById(R.id.redoButton);
        redoButton.setOnClickListener(this);

        homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.homeButton:
                Intent backHome = new Intent(this, HomeMain.class);
                startActivity(backHome);
                break;
            //redo haven't do yet
        }
    }
}
