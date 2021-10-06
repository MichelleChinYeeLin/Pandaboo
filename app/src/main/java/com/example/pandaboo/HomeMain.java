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

    //Initialization of default timer countdown values
    final int DEFAULT_COUNTDOWN_HOURS = 0;
    final int DEFAULT_COUNTDOWN_MINUTES = 30;
    final int DEFAULT_COUNTDOWN_SECONDS = 0;
    final int DEFAULT_SEEKBAR_PROGRESS = 30;

    //Initialization of constant variables to control the timer countdown
    final int MINUTES_TO_MILLISECONDS = 60000;
    final int MINUTES_MAX = 59;
    final int SECONDS_MAX = 59;

    //Initialization of the TextView for the timer countdown in home_main.xml
    private TextView timerCountdownHours;
    private TextView timerCountdownMinutes;
    private TextView timerCountdownSeconds;

    //Initialization for the alert dialog builder
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    //Initialization for the timer countdown duration
    private int hours = 0, minutes = 0, seconds = 0;
    public int timerValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);

        //Link the start button to the button in the home_main.xml file
        Button startButton = findViewById(R.id.startButton);

        //Open the set timer dialog box when the start button is clicked
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimer();
            }
        });

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

    /**
     * Start the timer countdown and display the timer duration
     */
    public void startTimer(){

        //Assign the timer duration to minutes variable
        minutes = timerValue;

        //Calculate the number of hours in the timer duration
        while (minutes >= 60){
            hours++;
            minutes -= 60;
        }

        //Start the timer countdown
        new CountDownTimer((long) timerValue * MINUTES_TO_MILLISECONDS, 1000){

            //After every countdown interval
            public void onTick(long milisUntilFinished){

                seconds--;

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
     * Create and displays the alert dialog to allow users to set the timer duration
     */
    public void setTimer(){
        //Create the alert dialog
        dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        dialogBuilder.setView(inflater.inflate(R.layout.set_timer, null));
        dialog = dialogBuilder.create();

        //Display the alert dialog
        dialog.show();

        //Initialization of elements in the home_main.xml
        TextView timerProgress = dialog.findViewById(R.id.timerProgress);
        SeekBar timerSeekBar = dialog.findViewById(R.id.timerSlider);
        Button startTimerButton = dialog.findViewById(R.id.startTimerButton);

        //Display the default timer duration
        timerProgress.setText(setTimerFormat(DEFAULT_SEEKBAR_PROGRESS));

        //Allow users to change the seekbar value and set the timer countdown duration
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            //Change the timer duration display when the seekbar value changes
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Retrieve the seekbar value (+1 since seekbar value starts at 0)
                progressChangedValue = progress + 1;

                //Display the selected timer duration
                timerProgress.setText(setTimerFormat(progressChangedValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                timerValue = progressChangedValue;
            }
        });

        //When users click the START button
        startTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start the timer countdown
                startTimer();

                //Close the alert dialog
                dialog.dismiss();
            }
        });
    }

    /**
     * Formats the seek bar value to a duration (in hours and minutes)
     * @param minutes the number of minutes as determined by the seek bar
     * @return a string of the timer duration to be set (in hours and minutes)
     */
    public String setTimerFormat(int minutes){
        String timeText = "";
        int hours = 0;

        //Calculate the number of hours
        while (minutes >= 60){
            hours++;
            minutes -= 60;
        }

        //Display the number of hours if hours > 0
        if (hours > 0){
            timeText += hours + " hour ";
        }

        //Display the number of minutes if minutes > 0
        if (minutes > 0){
            timeText += minutes + " minute";
        }

        return timeText;
    }

    /**
     * Changes the time value to String and formats it to double digits
     * @param time the time value to be formatted
     * @return the string value of the time value in double digits
     */
    public String timerCountdownFormat(int time){

        //If the time is in single digit, add a '0' to format it to double digits
        if (time < 9){
            return "0" + time;
        }

        return String.valueOf(time);
    }
}
