package com.example.pandaboo;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Event extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);5
        setContentView(R.layout.event);
    }

    private String eventTitle;
    private String eventDetails;
    private int startTime;
    private int endTime;
    private Date startDate;
    private Date endDate;
    private Reminder reminder;

    //Getters
    public String getEventTitle(){return eventTitle;}
    public String getEventDetails(){return eventDetails;}
    public int getStartTime(){return startTime;}
    public int getEndTime(){return endTime;}
    public Date getStartDate(){return startDate;}
    public Date getEndDate(){return endDate;}

    //Setters
    public void setEventTitle(String eventTitle){this.eventTitle = eventTitle;}
    public void setEventDetails(String eventDetails){this.eventDetails = eventDetails;}
    public void setStartTime(int startTime){this.startTime = startTime;}
    public void setEndTime(int endTime){this.endTime = endTime;}
    public void setStartDate(Date startDate){this.startDate = startDate;}
    public void setEndDate(Date endDate){this.endDate = endDate;}

    public void addReminder(boolean remindDaily, boolean remindWeekly, int remindTime, Date remindDate){
        Reminder reminder = new Reminder(remindDaily, remindWeekly, remindTime, remindDate);
    }
}
