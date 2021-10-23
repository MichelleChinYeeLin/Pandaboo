package com.example.pandaboo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Date;

public class Task extends AppCompatActivity {

    private String mainTitle;
    private String dueDate;
    private String priority;
    private ArrayList<SubTask> subTask = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task);
    }

    //Default Constructor (No subTask added)
    public Task(){
        this.mainTitle = "";
        this.dueDate = "";
        this.priority = "";
    }

    //Overloaded Constructor (No subTask added)
    public Task(String mainTitle, String dueDate, String priority){
        this.mainTitle = mainTitle;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    //Overloaded Constructor (Added subTask)
    public Task(String mainTitle)
    {
        this.mainTitle = mainTitle;
    }

    //Getters
    public String getMainTitle(){return mainTitle;}
    public String getDueDate(){return dueDate;}
    public String getPriority(){return priority;}
    public ArrayList<SubTask> getSubTask(){return subTask;}

    //Setters
    public void setMainTitle(String mainTitle){this.mainTitle = mainTitle;}
    public void setDueDate(String dueDate){this.dueDate = dueDate;}
    public void setPriority(String priority){this.priority = priority;}

    public void addSubTask(String subTitle, String dueDate, String priority){
        SubTask newSubTask = new SubTask(subTitle, dueDate, priority);

        subTask.add(newSubTask);
    }

    public int getSubTaskArraySize(){
        return subTask.size();
    }
}
