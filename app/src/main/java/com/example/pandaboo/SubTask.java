package com.example.pandaboo;

import java.util.Date;

public class SubTask {

    private String subTitle;
    private String dueDate;
    private String priority;

    //Default Constructor
    public SubTask(){
        this.subTitle ="";
        this.dueDate = "";
        this.priority = "";
    }

    //Overloaded Constructor
    public SubTask(String subTitle, String dueDate, String priority){
        this.subTitle = subTitle;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    //Getters
    public String getSubTitle(){return subTitle;}
    public String getDueDate(){return dueDate;}
    public String getPriority(){return priority;}

    //Setters
    public void setSubTitle(String subTitle){this.subTitle = subTitle;}
    public void setDueDate(String dueDate){this.dueDate = dueDate;}
    public void setPriority(String priority){this.priority = priority;}
}
