package com.example.pandaboo;

import java.io.Serializable;
import java.util.HashMap;

class Todo implements Serializable {

    private String name;
    private String message;
    private String date;
    private String priority;

    public Todo() {

    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {this.message = message;}

    public String getPriority() {return priority;}

    public void setPriority(String priority) {this.priority = priority;}


    public HashMap<String,String> toFirebaseObject() {
        HashMap<String,String> todo =  new HashMap<String,String>();
        todo.put("name", name);
        todo.put("message", message);
        todo.put("date", date);
        todo.put("priority", priority);

        return todo;
    }

}
 /*   private String mainTitle;
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
 */