package com.example.pandaboo;

public class Event{

    String EVENT, TIME, DATE, MONTH, YEAR;

    public Event(String EVENT, String TIME, String DATE, String MONTH, String YEAR){
        this.EVENT = EVENT;
        this.TIME = TIME;
        this.DATE = DATE;
        this.MONTH = MONTH;
        this.YEAR = YEAR;
    }

    public String getEVENT() {
        return EVENT;
    }

    public void setEVENT(String EVENT) {
        this.EVENT = EVENT;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getMONTH() {
        return MONTH;
    }

    public void setMONTH(String MONTH) {
        this.MONTH = MONTH;
    }

    public String getYEAR() {
        return YEAR;
    }

    public void setYEAR(String YEAR) {
        this.YEAR = YEAR;
    }

    /*
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

     */
}
