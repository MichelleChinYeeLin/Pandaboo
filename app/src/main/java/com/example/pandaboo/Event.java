package com.example.pandaboo;

public class Event{

    String EVENT, DETAILS, TIME, firstDATE, secondDATE, DATE, MONTH, YEAR, NOTIFY;

    public Event(String EVENT, String DETAILS, String TIME, String firstDATE, String secondDATE, String DATE, String MONTH, String YEAR, String NOTIFY){
        this.EVENT = EVENT;
        this.DETAILS = DETAILS;
        this.TIME = TIME;
        this.firstDATE = firstDATE;
        this.secondDATE = secondDATE;
        this.DATE = DATE;
        this.MONTH = MONTH;
        this.YEAR = YEAR;
        this.NOTIFY = NOTIFY;
    }

    public String getEVENT() {
        return EVENT;
    }

    public void setEVENT(String EVENT) {
        this.EVENT = EVENT;
    }

    public String getDETAILS() {
        return DETAILS;
    }

    public void setDETAILS(String DETAILS) {
        this.DETAILS = DETAILS;
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

    public String getFirstDATE() {
        return firstDATE;
    }

    public void setFirstDATE(String firstDATE) {
        this.firstDATE = firstDATE;
    }

    public String getSecondDATE() {
        return secondDATE;
    }

    public void setSecondDATE(String secondDATE) {
        this.secondDATE = secondDATE;
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

    public String getNOTIFY() {
        return NOTIFY;
    }

    public void setNOTIFY(String NOTIFY) {
        this.NOTIFY = NOTIFY;
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
