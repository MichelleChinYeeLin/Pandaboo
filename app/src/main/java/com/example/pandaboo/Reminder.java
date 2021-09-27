package com.example.pandaboo;

import java.util.Date;

public class Reminder {

    private boolean remindDaily;
    private boolean remindWeekly;
    private int remindTime;
    private Date remindDate;

    //Overloaded Constructor
    public Reminder(boolean remindDaily, boolean remindWeekly, int remindTime, Date remindDate){
        this.remindDaily = remindDaily;
        this.remindWeekly = remindWeekly;
        this.remindTime = remindTime;
        this.remindDate = remindDate;
    }

    //Getters
    public boolean getRemindDaily(){return remindDaily;}
    public boolean getRemindWeekly(){return remindWeekly;}
    public int getRemindTime(){return remindTime;}
    public Date getRemindDate(){return remindDate;}

    //Setters
    public void setRemindDaily(boolean remindDaily){this.remindDaily = remindDaily;}
    public void setRemindWeekly(boolean remindWeekly){this.remindWeekly = remindWeekly;}
    public void setRemindTime(int remindTime){this.remindTime = remindTime;}
    public void setRemindDate(Date remindDate){this.remindDate = remindDate;}
}
