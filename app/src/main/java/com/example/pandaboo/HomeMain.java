package com.example.pandaboo;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class HomeMain extends AppCompatActivity implements View.OnClickListener {

    private Button startButton;
    private TextView timerCountdown;
    public int counter = 30;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);

        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.startButton:
                setTimer();
                startTimer();
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
                counter = 30;
            }
        }.start();
    }

    public void setTimer(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View contactPopupView = getLayoutInflater().inflate(R.layout.set_timer);
    }
}
