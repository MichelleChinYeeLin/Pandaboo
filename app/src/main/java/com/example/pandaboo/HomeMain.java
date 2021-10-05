package com.example.pandaboo;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeMain extends AppCompatActivity implements View.OnClickListener {

    private Button startButton;
    private Button pandaButton;
    private Button shopButton;
    private Button eventButton;
    private Button tasksButton;
    private Button settingsButton;
    private Button viewSuccess;
    private TextView timerCountdown;
    public int counter = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);

        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(this);

        pandaButton = findViewById(R.id.pandaButton);
        pandaButton.setOnClickListener(this);

        shopButton = findViewById(R.id.shopButton);
        shopButton.setOnClickListener(this);

        eventButton = findViewById(R.id.eventButton);
        eventButton.setOnClickListener(this);

        tasksButton = findViewById(R.id.tasksButton);
        tasksButton.setOnClickListener(this);

        settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(this);

        viewSuccess = findViewById(R.id.viewSuccess);
        viewSuccess.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.startButton:
                startTimer();
                break;
            case R.id.pandaButton:
                Intent toPanda = new Intent(this, Panda.class);
                startActivity(toPanda);
                break;
            case R.id.shopButton:
                Intent toShop = new Intent(this, Shop.class);
                startActivity(toShop);
                break;
            case R.id.eventButton:
                Intent toEvent = new Intent(this, Event.class);
                startActivity(toEvent);
                break;
            case R.id.tasksButton:
                Intent toTasks = new Intent(this, Task.class);
                startActivity(toTasks);
                break;
            case R.id.settingsButton:
                Intent toSettings = new Intent(this, Settings.class);
                startActivity(toSettings);
                break;
            case R.id.viewSuccess:
                Intent toSuccess = new Intent(this, Success.class);
                startActivity(toSuccess);
                break;
        }
    }

    public void startTimer(){

        timerCountdown = findViewById(R.id.timerCountdown);

        new CountDownTimer(30000, 1000){
            public void onTick(long milisUntilFinished){
                timerCountdown.setText(String.valueOf(counter));
                counter--;
            }

            public void onFinish(){
                timerCountdown.setText("Done!");
            }
        }.start();
    }
}
