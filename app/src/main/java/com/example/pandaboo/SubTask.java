package com.example.pandaboo;

import java.util.Date;

public class SubTask {

    private String subTitle;
    private Date dueDate;
    private String priority;
    private boolean doneStatus;

    //Default Constructor
    public SubTask(){
        this.subTitle ="";
        this.dueDate = new Date();
        this.priority = "";
        this.doneStatus = false;
    }

    //Overloaded Constructor
    public SubTask(String subTitle, Date dueDate, String priority, boolean doneStatus){
        this.subTitle = subTitle;
        this.dueDate = dueDate;
        this.priority = priority;
        this.doneStatus = doneStatus;
    }

    //Getters
    public String getSubTitle(){return subTitle;}
    public Date getDueDate(){return dueDate;}
    public String getPriority(){return priority;}
    public boolean getDoneStatus(){return doneStatus;}

    //Setters
    public void setSubTitle(String subTitle){this.subTitle = subTitle;}
    public void setDueDate(Date dueDate){this.dueDate = dueDate;}
    public void setPriority(String priority){this.priority = priority;}
    public void setDoneStatus(boolean doneStatus){this.doneStatus = doneStatus;}
}
