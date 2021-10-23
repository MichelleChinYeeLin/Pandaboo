package com.example.pandaboo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class TaskAdd extends AppCompatActivity {

    final String firebaseURL = "https://pandaboodcs-default-rtdb.asia-southeast1.firebasedatabase.app";
    final DatabaseReference reference = FirebaseDatabase.getInstance(firebaseURL).getReference().child("admin");

    private ArrayList<SubTask> subTaskArrayList = new ArrayList<>();
    private ArrayList<Integer> integerArraylist = new ArrayList<>();

    private String taskMainTitle = "";
    private String dueDate = "";
    private String priority = "";
    private EditText taskMainTitleText;

    private int subTaskCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_edit);

        ImageButton addSubTaskButton = findViewById(R.id.addSubTaskButton);
        GridView subTaskGridView = findViewById(R.id.subTaskGridView);
        taskMainTitleText = findViewById(R.id.taskMainTitle);
        Button deleteButton = findViewById(R.id.deleteButton);
        Button saveButton = findViewById(R.id.saveButton);
        Button backButton = findViewById(R.id.backButton);
        ImageButton setDueDate = findViewById(R.id.setDueDateButton);
        Spinner prioritySpinner = findViewById(R.id.prioritySpinner);

        SubTaskAddGVAdapter adapter = new SubTaskAddGVAdapter(TaskAdd.this, integerArraylist);

        addSubTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subTaskCounter++;

                RelativeLayout area = findViewById(R.id.taskMainInfoArea);
                TextView setDueDateText = findViewById(R.id.dueDateText);
                TextView setPriorityText = findViewById(R.id.priorityText);

                area.setVisibility(View.GONE);
                setDueDate.setVisibility(View.GONE);
                prioritySpinner.setVisibility(View.GONE);
                setDueDateText.setVisibility(View.GONE);
                setPriorityText.setVisibility(View.GONE);

                integerArraylist.add(integerArraylist.size() + 1);

                subTaskGridView.setAdapter(adapter);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask(adapter);
            }
        });

        setDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendar();
            }
        });

        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(TaskAdd.this, R.array.priority_text, android.R.layout.simple_spinner_item);
        prioritySpinner.setAdapter(staticAdapter);

        prioritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                priority = (parent.getItemAtPosition(position)).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                priority = "Low";
            }
        });
    }

    public void saveTask(SubTaskAddGVAdapter adapter){

        taskMainTitle = taskMainTitleText.getText().toString();

        subTaskArrayList = adapter.getSubTask();

        if (subTaskArrayList.size() > 0){
            reference.child("Task").child(taskMainTitle).child("TaskName").setValue(taskMainTitle);

            for (SubTask subTask : subTaskArrayList){
                reference.child("Task").child(taskMainTitle).child("SubTask").child(subTask.getSubTitle()).child("SubTaskName").setValue(subTask.getSubTitle());
                reference.child("Task").child(taskMainTitle).child("SubTask").child(subTask.getSubTitle()).child("SubTaskDueDate").setValue(subTask.getDueDate());
                reference.child("Task").child(taskMainTitle).child("SubTask").child(subTask.getSubTitle()).child("SubTaskPriority").setValue(subTask.getPriority());
            }
        }

        else {
            reference.child("Task").child(taskMainTitle).child("TaskName").setValue(taskMainTitle);
            reference.child("Task").child(taskMainTitle).child("TaskDueDate").setValue(dueDate);
            reference.child("Task").child(taskMainTitle).child("TaskPriority").setValue(priority);
        }

        finish();
    }

    public void showCalendar(){
        TextView dueDateText = findViewById(R.id.dueDate);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(TaskAdd.this, R.style.Theme_AppCompat_DayNight_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(Calendar.YEAR, i);
                newDate.set(Calendar.MONTH, i1);
                newDate.set(Calendar.DAY_OF_MONTH, i2);
                SimpleDateFormat d1format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                String event_Date1 = d1format.format(newDate.getTime());
                dueDateText.setText(event_Date1);
                dueDate = (dueDateText.getText()).toString();
            }
        }, year, month, dayOfMonth);
        datePickerDialog.show();
    }
}
