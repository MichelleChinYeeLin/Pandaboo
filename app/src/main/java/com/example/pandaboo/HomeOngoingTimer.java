package com.example.pandaboo;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class HomeOngoingTimer extends Fragment {

    private int totalTimerDuration = 0;
    private int maxPauseDuration = 0;
    private int pauseSeconds = 0;
    private int pauseMinutes = 0;
    private int pauseDurationRemainder = 0;
    private boolean isResumed = false;
    private int pauseCounter = 0;
    int timerCountdownRemainder = 0;

    final int MINUTES_TO_SECONDS = 60;
    final int SECONDS_TO_MILLISECONDS = 1000;
    final int SECONDS_MAX = 59;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.home_ongoing_timer, container, false);

        Button cancelButton = view.findViewById(R.id.cancelButton);
        Button pauseButton = view.findViewById(R.id.pauseButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelTimer();
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                pauseCounter++;
            }
        });
        return view;
    }

    public void cancelTimer(){
        //Initialization for the alert dialog builder
        AlertDialog dialog;
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        dialogBuilder.setView(inflater.inflate(R.layout.timer_cancel, null));
        dialog = dialogBuilder.create();

        //Display the alert dialog
        dialog.show();

        Button cancelYes = dialog.findViewById(R.id.cancelYes);
        Button cancelNo = dialog.findViewById(R.id.cancelNo);

        cancelYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ((HomeMain)getActivity()).cancelTimer();
                ((HomeMain)getActivity()).fragmentToStartTimer();
            }
        });

        cancelNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void pauseTimer(){

        if (pauseCounter <= 1){
            totalTimerDuration = ((HomeMain)getActivity()).getTotalTimerDuration();

            maxPauseDuration = calcMaxPauseDuration();
            pauseDurationRemainder = maxPauseDuration;
            pauseSeconds = pauseDurationRemainder;

            while (pauseSeconds >= 60){
                pauseMinutes++;
            }
        }

        timerCountdownRemainder = ((HomeMain) getActivity()).pauseTimer();

        AlertDialog dialog;
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        dialogBuilder.setView(inflater.inflate(R.layout.timer_pause, null));
        dialog = dialogBuilder.create();

        dialog.show();

        TextView pauseTimer = dialog.findViewById(R.id.pauseTimer);
        ImageButton resumeButton = dialog.findViewById(R.id.resumeButton);

        new CountDownTimer((long) pauseDurationRemainder * SECONDS_TO_MILLISECONDS, 1000){
            public void onTick(long milisUntilFinished){

                if (isResumed){
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "durationRemainder: " + pauseDurationRemainder, Toast.LENGTH_LONG).show();
                    cancel();
                    resumeMainTimer();
                }

                pauseSeconds--;
                pauseDurationRemainder--;

                if (pauseSeconds < 0 && pauseMinutes > 0){
                    pauseSeconds = SECONDS_MAX;
                    pauseMinutes--;
                }

                pauseTimer.setText(timerCountdownFormat(pauseMinutes) + ":" + timerCountdownFormat(pauseSeconds));
            }

            public void onFinish(){
                Toast.makeText(getActivity(), "Pause duration reached.", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }.start();

        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isResumed = true;
            }
        });
    }

    public int calcMaxPauseDuration(){
        return (int) (totalTimerDuration * 0.2);
    }

    public void resumeMainTimer(){
        ((HomeMain)getActivity()).startTimer(timerCountdownRemainder);
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
}