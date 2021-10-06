package com.example.pandaboo;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class HomeMain extends AppCompatActivity implements View.OnClickListener{

    private Button startButton;


    private Button pandaButton;
    private Button shopButton;
    private Button plannerButton;
    private Button tasksButton;
    private Button settingsButton;
    //private Button viewSuccess = findViewById(R.id.viewSuccess);

    private Button startTimerButton;
    private TextView timerCountdownHours;
    private TextView timerCountdownMinutes;
    private TextView timerCountdownSeconds;
    private TextView timerProgress;
    public int timerValue = 0;
    final int MINUTES_TO_MILLISECONDS = 60000;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private SeekBar timerSeekBar;

    private int hours = 0, minutes = 0, seconds = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);

        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimer();
            }
        });
        timerCountdownHours = findViewById(R.id.timerCountdownHours);
        timerCountdownMinutes = findViewById(R.id.timerCountdownMinutes);
        timerCountdownSeconds = findViewById(R.id.timerCountdownSeconds);

        pandaButton = findViewById(R.id.pandaButton);
        shopButton = findViewById(R.id.shopButton);
        plannerButton = findViewById(R.id.plannerButton);
        tasksButton = findViewById(R.id.tasksButton);
        settingsButton = findViewById(R.id.settingsButton);

        pandaButton.setOnClickListener(this);
        shopButton.setOnClickListener(this);
        plannerButton.setOnClickListener(this);
        tasksButton.setOnClickListener(this);
        settingsButton.setOnClickListener(this);
        //viewSuccess.setOnClickListener(this);


        timerCountdownHours.setText("00");
        timerCountdownMinutes.setText("30");
        timerCountdownSeconds.setText("00");
    }


    public void onClick(View view) {
        switch(view.getId()){
            case R.id.pandaButton:
                Intent toPanda = new Intent(this, Panda.class);
                startActivity(toPanda);
                break;
            case R.id.shopButton:
                Intent toShop = new Intent(this, Shop.class);
                startActivity(toShop);
                break;
            case R.id.plannerButton:
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
            /*case R.id.viewSuccess:
                Intent toSuccess = new Intent(this, Success.class);
                startActivity(toSuccess);
                break;*/
        }
    }

    public void startTimer(){

        minutes = timerValue;

        while (minutes >= 60){
            hours++;
            minutes -= 60;
        }

        new CountDownTimer((long) timerValue * MINUTES_TO_MILLISECONDS, 1000){
            public void onTick(long milisUntilFinished){

                String hoursText = "";
                String minutesText = "";
                String secondsText = "";

                seconds--;

                if (seconds < 0 && minutes <= 0 && hours > 0){
                    minutes = 59;
                    seconds = 59;
                    hours--;
                }

                if (seconds < 0 && minutes > 0){
                    seconds = 59;
                    minutes--;
                }

                //Formatting the time shown on the countdown
                if (seconds <= 9){
                    secondsText += "0";
                }

                secondsText += String.valueOf(seconds);

                if (minutes <= 9){
                    minutesText += "0";
                }

                minutesText += String.valueOf(minutes);

                if (hours <= 9){
                    hoursText += "0";
                }

                hoursText += String.valueOf(hours);

                timerCountdownHours.setText(hoursText);
                timerCountdownMinutes.setText(minutesText);
                timerCountdownSeconds.setText(secondsText);
            }

            public void onFinish(){
                Toast.makeText(HomeMain.this, "Timer is completed", Toast.LENGTH_LONG).show();
            }
        }.start();
    }

    public void setTimer(){
        dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();

        dialogBuilder.setView(inflater.inflate(R.layout.set_timer, null));

        dialog = dialogBuilder.create();
        dialog.show();

        timerProgress = dialog.findViewById(R.id.timerProgress);
        timerSeekBar = dialog.findViewById(R.id.timerSlider);
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            String timeText = "";

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;

                timerProgress.setText(timeText = setTimerFormat(progressChangedValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                timerValue = progressChangedValue;
            }
        });

        startTimerButton = dialog.findViewById(R.id.startTimerButton);
        startTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
                dialog.dismiss();
            }
        });
    }

    public String setTimerFormat(int minutes){
        String timeText = "";
        int hours = 0;

        while (minutes >= 60){
            hours++;
            minutes -= 60;
        }

        if (hours > 0){
            timeText += hours + " hour ";
        }

        if (minutes > 0){
            timeText += minutes + " minute";
        }

        return timeText;
    }
}
