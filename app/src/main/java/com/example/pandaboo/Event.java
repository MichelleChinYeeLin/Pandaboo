package com.example.pandaboo;

public class Event{

    String EVENT, DETAILS, startTIME, endTIME, firstDATE, secondDATE, NOTIFY;

    public Event(String EVENT, String DETAILS, String startTIME, String endTIME, String firstDATE, String secondDATE, String NOTIFY){
        this.EVENT = EVENT;
        this.DETAILS = DETAILS;
        this.startTIME = startTIME;
        this.endTIME = endTIME;
        this.firstDATE = firstDATE;
        this.secondDATE = secondDATE;
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

    public String getStartTIME() {
        return startTIME;
    }

    public void setStartTIME(String startTIME) {
        this.startTIME = startTIME;
    }

    public String getEndTIME() {
        return endTIME;
    }

    public void setEndTIME(String endTIME) {
        this.endTIME = endTIME;
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
