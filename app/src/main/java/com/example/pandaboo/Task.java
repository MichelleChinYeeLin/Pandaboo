package com.example.pandaboo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class Task extends AppCompatActivity {

    private String mainTitle;
    private Date dueDate;
    private String priority;
    private boolean doneStatus;
    private SubTask subTask[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task);
    }

    //Default Constructor (No subTask added)
    public Task(){
        this.mainTitle = "";
        this.dueDate = new Date();
        this.priority = "";
        this.doneStatus = false;
    }

    //Overloaded Constructor (No subTask added)
    public Task(String mainTitle, Date dueDate, String priority, boolean doneStatus){
        this.mainTitle = mainTitle;
        this.dueDate = dueDate;
        this.priority = priority;
        this.doneStatus = doneStatus;
    }

    //Getters
    public String getMainTitle(){return mainTitle;}
    public Date getDueDate(){return dueDate;}
    public String getPriority(){return priority;}
    public boolean getDoneStatus(){return doneStatus;}
    public SubTask getSubTask(int index){
        return subTask[index];
    }

    //Setters
    public void setMainTitle(String mainTitle){this.mainTitle = mainTitle;}
    public void setDueDate(Date dueDate){this.dueDate = dueDate;}
    public void setDoneStatus(boolean doneStatus){this.doneStatus = doneStatus;}
    public void setPriority(String priority){this.priority = priority;}

    public void addSubTask(String subTitle, Date dueDate, String priority, boolean doneStatus){
        SubTask newSubTask = new SubTask(subTitle, dueDate, priority, doneStatus);

        this.subTask[this.subTask.length + 1] = newSubTask;
    }
}
