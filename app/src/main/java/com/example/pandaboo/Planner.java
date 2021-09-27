package com.example.pandaboo;

public class Planner {
    //private attributes
    private String eventTitle;
    private String eventDetails;
    private int startTime;
    private int endTime;
    private Date startDate;
    private Date endDate;
    private Reminder reminder;

    //getters
    public String getEventTitle(){
        this.eventTitle = eventTitle;
    }
    public String getEventDetails(){
        this.eventDetails = eventDetails;
    }
    public int getStartTime(){
        this.startTime = startTime;
    }
    public int getEndTime() {
        this.endTime = endTime;
    }
    public Date getStartDate(){
        this.startDate = startDate;
    }
    public Date getEndDate(){
        this.endDate = endDate;
    }

    //setters
    public void setReminder(Reminder reminder){

    }
    public void setEventTitle(String eventTitle){

    }
    public void setEventDetails(String eventDetails){

    }
    public void setStartTime(int startTime){

    }
    public void setEndTime(int endTime){

    }
    public void setStartDate(Date startDate){

    }
    public void setEndDate(Date endDate){

    }
}
