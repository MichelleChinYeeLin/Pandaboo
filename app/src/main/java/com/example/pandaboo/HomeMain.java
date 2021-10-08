package com.example.pandaboo;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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

    //Initialization of constant variables for time values
    final int SECONDS_TO_MILLISECONDS = 1000;
    final int MINUTES_MAX = 59;
    final int SECONDS_MAX = 59;

    //Initialization of constant variables to control the maximum pause duration
    final double PAUSE_TIMER_DURATION_PERCENTAGE = 0.2;

    //Initialization of the TextView for the timer countdown in home_main.xml
    private TextView timerCountdownHours;
    private TextView timerCountdownMinutes;
    private TextView timerCountdownSeconds;

    //Initialization for the timer countdown duration
    private int hours = 0, minutes = 0, seconds = 0;
    private int timerDuration = 0;
    private int totalTimerDuration = 0;

    //Initialization for variable to control the timer (cancel/ pause)
    private boolean isCancelled = false;
    private boolean isPaused = false;
    private int pauseCounter = 0;
    private int maxPauseDuration = 0;
    private int pauseDurationRemainder = 0;

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

        pandaButton.setOnClickListener(this);
        shopButton.setOnClickListener(this);
        plannerButton.setOnClickListener(this);
        tasksButton.setOnClickListener(this);
        settingsButton.setOnClickListener(this);

        //Set default timer text for the timer countdown
        timerCountdownHours.setText(timerCountdownFormat(DEFAULT_COUNTDOWN_HOURS));
        timerCountdownMinutes.setText(timerCountdownFormat(DEFAULT_COUNTDOWN_MINUTES));
        timerCountdownSeconds.setText(timerCountdownFormat(DEFAULT_COUNTDOWN_SECONDS));

        Fragment homeStartTimer = new HomeStartTimer();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.homeFrame, homeStartTimer);
        fragmentTransaction.commit();
    }

    public void resetTimerCountdown(){
        timerCountdownHours.setText(timerCountdownFormat(DEFAULT_COUNTDOWN_HOURS));
        timerCountdownMinutes.setText(timerCountdownFormat(DEFAULT_COUNTDOWN_MINUTES));
        timerCountdownSeconds.setText(timerCountdownFormat(DEFAULT_COUNTDOWN_SECONDS));
        pauseCounter = 0;
        hours = minutes = seconds = 0;
        isCancelled = isPaused = false;
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
        }
    }

    /**
     * Start the ongoingTimer fragment and replace the current fragment
     */
    public void fragmentToOngoingTimer(){
        Fragment homeOngoingTimer = new HomeOngoingTimer();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.homeFrame, homeOngoingTimer);
        fragmentTransaction.commit();
    }

    /**
     * Start the startTimer fragment and replace the current fragment
     */
    public void fragmentToStartTimer(){
        Fragment homeStartTimer = new HomeStartTimer();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.homeFrame, homeStartTimer);
        fragmentTransaction.commit();
    }

    /**
     * CLose the startTimer fragment
     */
    public void closeFragmentStartTimer(){
        Fragment homeStartTimer = new HomeStartTimer();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(homeStartTimer);
        fragmentTransaction.commit();
    }

    /**
     * Close the ongoingTimer fragment
     */
    public void closeFragmentOngoingTimer(){
        Fragment homeStartTimer = new HomeStartTimer();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(homeStartTimer);
        fragmentTransaction.commit();
    }

    /**
     * Closes the startTimer fragment and starts the timer countdown
     * @param duration duration of the timer countdown set by the user
     */
    public void toStartTimer(int duration){
        closeFragmentStartTimer();
        startTimer(duration);
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

    /**
     * Formats the time in hours, minutes and seconds
     * @param seconds the duration of time
     * @return formatted string in hours, minutes and seconds
     */
    public String formatTimeDuration(int seconds){
        int minutes = 0;
        int hours = 0;
        String textFormat = "";

        if (seconds <= 0){
            return "0 seconds";
        }

        //Calculates the total minutes
        while (seconds >= 60){
            minutes++;
            seconds -= 60;
        }

        //Calculates the total hours
        while (minutes >= 60){
            hours++;
            minutes -= 60;
        }

        //Formats the hours
        if (hours > 0){

            if (hours == 1){
                textFormat += hours + " hour ";
            }

            else {
                textFormat += hours + " hour ";
            }
        }

        //Formats the minutes
        if (minutes > 0){

            if (minutes == 1){
                textFormat += minutes + " minute ";
            }

            else {
                textFormat += minutes + " minutes ";
            }
        }

        //Formats the seconds
        if (seconds > 0){

            if (seconds == 1){
                textFormat += seconds + " second";
            }

            else {
                textFormat += seconds + " seconds";
            }
        }

        return textFormat;
    }

    /**
     * Changes the value of isCancelled(boolean) to true
     */
    public void cancelTimer(){
        isCancelled = true;
    }

    /**
     * Changes the value of isPaused(boolean) to true
     * @return the remaining time left on the timer countdown
     */
    public int pauseTimer(){
        isPaused = true;
        pauseCounter++;
        return timerDuration;
    }

    /**
     * Calculate the duration that users are allowed to pause
     * @return the duration that users can pause
     */
    public int calcMaxPauseDuration(){

        //If the user paused the timer for the first time
        if (pauseCounter <= 1){
            maxPauseDuration = (int) (totalTimerDuration * PAUSE_TIMER_DURATION_PERCENTAGE);
            return maxPauseDuration;
        }

        //If the user has paused the timer before
        else {
            return pauseDurationRemainder;
        }
    }

    /**
     * Sets the remaining duration of the pause timer
     * @param remainder the remaining duration the user can pause
     */
    public void setPauseDurationRemainder(int remainder){
        pauseDurationRemainder = remainder;
    }

    //Calculates the bamboo earned by the user
    public int calcEarnedBamboo (){
        return totalTimerDuration / 60;
    }

    /**
     * Shows a dialog box with details of the timer(succeeded)
     */
    public void succeededTimer(){

        //Close the ongoingTimer fragment
        closeFragmentOngoingTimer();

        //Create the alert dialog box
        AlertDialog dialog;
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        dialogBuilder.setView(inflater.inflate(R.layout.timer_success, null));
        dialog = dialogBuilder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        //Display the alert dialog box
        dialog.show();

        //Initialization of the elements in timer_success.xml
        TextView totalStudyMinutes = dialog.findViewById(R.id.totalStudyMinutes);
        TextView totalPauseMinutes = dialog.findViewById(R.id.totalPauseMinutes);
        TextView totalBambooEarned = dialog.findViewById(R.id.totalBambooEarned);
        ImageButton redoTimerButton = dialog.findViewById(R.id.redoTimerIcon);
        ImageButton homeButton = dialog.findViewById(R.id.homeIcon);

        //Displays the timer details on the alert dialog box
        totalStudyMinutes.setText(formatTimeDuration(totalTimerDuration - timerDuration));
        totalPauseMinutes.setText(formatTimeDuration(maxPauseDuration));
        totalBambooEarned.setText(String.valueOf(calcEarnedBamboo()));

        //Reset the timer countdown
        resetTimerCountdown();

        //Closes the alert dialog box and restarts the timer with the same duration as previously set
        //When users click the Redo Timer icon
        redoTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startTimer(totalTimerDuration);
            }
        });

        //Closes the alert dialog box and returns to the home page
        //When users click the Home icon
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                fragmentToStartTimer();
            }
        });
    }

    /**
     * Shows a dialog box with details of the timer(failed)
     */
    public void failedTimer(){

        //Closes the ongoingTimer fragment
        closeFragmentOngoingTimer();

        //Creates the alert dialog box
        AlertDialog dialog;
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        dialogBuilder.setView(inflater.inflate(R.layout.timer_pause_exceeded, null));
        dialog = dialogBuilder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        //Displays the alert dialog box
        dialog.show();

        //Initialization of the elements in timer_pause_exceeded.xml
        TextView totalStudyMinutes = dialog.findViewById(R.id.totalStudyMinutes);
        TextView totalPauseMinutes = dialog.findViewById(R.id.totalPauseMinutes);
        ImageButton redoTimerButton = dialog.findViewById(R.id.redoTimerIcon);
        ImageButton homeButton = dialog.findViewById(R.id.homeIcon);

        //Displays the timer details on the alert dialog box
        totalStudyMinutes.setText(formatTimeDuration(totalTimerDuration - timerDuration));
        totalPauseMinutes.setText(formatTimeDuration(maxPauseDuration));

        //Resets the timer countdown
        resetTimerCountdown();

        //Closes the alert dialog box and restarts the timer with the same duration as previously set
        //When users click the Redo Timer icon
        redoTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseCounter = 0;
                dialog.dismiss();
                startTimer(totalTimerDuration);
            }
        });

        //Closes the alert dialog box and returns to the home page
        //When users click the Home icon
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseCounter = 0;
                dialog.dismiss();
                fragmentToStartTimer();
            }
        });
    }

    /**
     * Start the countdown timer and display the timer duration
     * @param duration the total amount of time
     */
    public void startTimer(int duration){

        //Start the ongoingTimer fragment
        fragmentToOngoingTimer();

        //Assign the timer duration to variables
        seconds = duration;
        timerDuration = duration;
        totalTimerDuration = duration;

        //Calculate the number of hours in the timer duration
        while (seconds >= 60){
            seconds -= 60;
            minutes++;
        }

        //Calculate the number of minutes in the timer duration
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

                //TODO: Prevent users from exiting the application
                //TODO: Prevent users from opening the Panda and Shop page

                //If the user cancels the timer countdown
                if (isCancelled){
                    //Cancel and reset timer
                    cancel();
                    resetTimerCountdown();
                }

                //If the user pauses the timer countdown
                if (isPaused){
                    //Cancel the timer
                    cancel();
                }
            }

            //When the timer countdown finishes
            public void onFinish(){
                succeededTimer();
            }
        }.start();
    }
}
