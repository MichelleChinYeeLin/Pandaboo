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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class HomeMain extends AppCompatActivity implements View.OnClickListener{

    //Initialization of default timer countdown values
    final int DEFAULT_COUNTDOWN_HOURS = 0;
    final int DEFAULT_COUNTDOWN_MINUTES = 0;
    final int DEFAULT_COUNTDOWN_SECONDS = 0;

    //Initialization of constant variables to control the timer countdown
    final int MINUTES_TO_MILLISECONDS = 60000;
    final int MINUTES_TO_SECONDS = 60;
    final int SECONDS_TO_MILLISECONDS = 1000;
    final int MINUTES_MAX = 59;
    final int SECONDS_MAX = 59;

    //Initialization of the TextView for the timer countdown in home_main.xml
    private TextView timerCountdownHours;
    private TextView timerCountdownMinutes;
    private TextView timerCountdownSeconds;

    //Initialization for the timer countdown duration
    private int hours = 0, minutes = 0, seconds = 0;
    private int timerDuration = 0;
    private int totalTimerDuration = 0;

    boolean isCancelled = false;
    boolean isPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);

        //Assigning the TextView variables to the TextView in home_main.xml for the timer countdown
        timerCountdownHours = findViewById(R.id.timerCountdownHours);
        timerCountdownMinutes = findViewById(R.id.timerCountdownMinutes);
        timerCountdownSeconds = findViewById(R.id.timerCountdownSeconds);

        //Initialization and assignment of the Button variables to the buttons in the bottom navigation menu of home_main.xml
        Button pandaButton = findViewById(R.id.pandaButton);
        Button shopButton = findViewById(R.id.shopButton);
        Button plannerButton = findViewById(R.id.plannerButton);
        Button tasksButton = findViewById(R.id.tasksButton);
        Button settingsButton = findViewById(R.id.settingsButton);
        //Button viewSuccess = findViewById(R.id.viewSuccess);

        pandaButton.setOnClickListener(this);
        shopButton.setOnClickListener(this);
        plannerButton.setOnClickListener(this);
        tasksButton.setOnClickListener(this);
        settingsButton.setOnClickListener(this);
        //viewSuccess.setOnClickListener(this);

        //Set default timer text for the timer countdown
        timerCountdownHours.setText(timerCountdownFormat(DEFAULT_COUNTDOWN_HOURS));
        timerCountdownMinutes.setText(timerCountdownFormat(DEFAULT_COUNTDOWN_MINUTES));
        timerCountdownSeconds.setText(timerCountdownFormat(DEFAULT_COUNTDOWN_SECONDS));

        fragmentToStartTimer();
    }

    /**
     * Changes the activity based on the button clicked
     * @param view the view of the layout
     */
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

    public void cancelTimer(){
        isCancelled = true;
    }

    public int pauseTimer(){
        isPaused = true;
        return timerDuration;
    }

    public void fragmentToOngoingTimer(){
        Fragment homeOngoingTimer = new HomeOngoingTimer();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.homeFrame, homeOngoingTimer);
        fragmentTransaction.commit();
    }

    public void fragmentToStartTimer(){
        Fragment homeStartTimer = new HomeStartTimer();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.homeFrame, homeStartTimer);
        fragmentTransaction.commit();
    }

    public void closeFragmentStartTimer(){
        Fragment homeStartTimer = new HomeStartTimer();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(homeStartTimer);
        fragmentTransaction.commit();
    }

    /**
     * Start the countdown timer and display the timer duration
     * @param duration the total amount of time
     */
    public void startTimer(int duration){

        closeFragmentStartTimer();
        fragmentToOngoingTimer();

        hours = minutes = seconds = 0;

        isCancelled = false;
        isPaused = false;

        //Assign the timer duration to variables
        seconds = duration;
        timerDuration = duration;
        totalTimerDuration = duration;

        //Calculate the number of hours in the timer duration
        while (seconds >= 60){
            seconds -= 60;
            minutes++;
        }

        while (minutes >= 60){
            minutes -= 60;
            hours++;
        }

        //Start the timer countdown
        new CountDownTimer((long) duration * SECONDS_TO_MILLISECONDS, 1000){

            //After every countdown interval
            public void onTick(long milisUntilFinished){

                seconds--;
                timerDuration--;

                if (isCancelled){
                    hours = 0;
                    minutes = 0;
                    seconds = 0;

                    cancel();
                }

                if (isPaused){
                    cancel();
                }

                //If minutes and seconds < 0, convert an hour to minutes and seconds
                if (seconds < 0 && minutes <= 0 && hours > 0){
                    minutes = MINUTES_MAX;
                    seconds = SECONDS_MAX;
                    hours--;
                }

                //If seconds < 0, convert a minute to seconds
                if (seconds < 0 && minutes > 0){
                    seconds = SECONDS_MAX;
                    minutes--;
                }

                //Display the timer duration
                timerCountdownHours.setText(timerCountdownFormat(hours));
                timerCountdownMinutes.setText(timerCountdownFormat(minutes));
                timerCountdownSeconds.setText(timerCountdownFormat(seconds));
            }

            public void onFinish(){
                Toast.makeText(HomeMain.this, "Timer is completed", Toast.LENGTH_LONG).show();
            }
        }.start();
    }

    /**
     * Changes the time value to String and formats it to double digits
     * @param time the time value to be formatted
     * @return the string value of the time value in double digits
     */
    public String timerCountdownFormat(int time){

        //If the time is in single digit, add a '0' to format it to double digits
        if (time <= 9){
            return "0" + time;
        }

        return String.valueOf(time);
    }

    public int getTotalTimerDuration(){
        return totalTimerDuration;
    }
}
