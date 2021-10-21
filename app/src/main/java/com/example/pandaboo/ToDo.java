package com.example.pandaboo;

import java.io.Serializable;
import java.util.HashMap;

class Todo implements Serializable {

    private String taskName;
    private String taskMessage;
    private String taskDate;
    private String taskPriority;

    //Empty constructor
    public Todo(){
    }

    public Todo(String taskName, String taskMessage, String taskDate, String taskPriority) {
            this.taskName = taskName;
            this.taskMessage = taskMessage;
            this.taskDate = taskDate;
            this.taskPriority = taskPriority;
    }

    public String getDate() {
        return taskDate;
    }

    public void setDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public String getName() {
        return taskName;
    }

    public void setName(String taskName) {
        this.taskName = taskName;
    }

    public String getMessage() {
        return taskMessage;
    }

    public void setMessage(String taskMessage) {this.taskMessage = taskMessage;}

    public String getPriority() {return taskPriority;}

    public void setPriority(String taskPriority) {this.taskPriority = taskPriority;}


    public HashMap<String,String> toFirebaseObject() {
        HashMap<String,String> todo =  new HashMap<String,String>();
        todo.put("name", taskName);
        todo.put("message", taskMessage);
        todo.put("date", taskDate);
        todo.put("priority", taskPriority);

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