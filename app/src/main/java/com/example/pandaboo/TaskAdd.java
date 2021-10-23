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
    EditText taskMainTitleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_edit);

        ImageButton addSubTaskButton = findViewById(R.id.addSubTaskButton);
        GridView subTaskGridView = findViewById(R.id.subTaskGridView);
        taskMainTitleText = findViewById(R.id.taskMainTitle);
        Button deleteButton = findViewById(R.id.deleteButton);
        Button saveButton = findViewById(R.id.saveButton);
        ImageButton setDueDate = findViewById(R.id.setDueDateButton);
        Spinner prioritySpinner = findViewById(R.id.prioritySpinner);

        SubTaskAddGVAdapter adapter = new SubTaskAddGVAdapter(TaskAdd.this, integerArraylist);
        addSubTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        taskMainTitleText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                taskMainTitle = taskMainTitleText.getText().toString();
            }
        });

        Task task;
        subTaskArrayList = adapter.getSubTask();

        if (subTaskArrayList.size() > 0){
            task = new Task(taskMainTitle);
            for (SubTask subTask : subTaskArrayList){
                System.out.println(subTask.getSubTitle());
                System.out.println(subTask.getDueDate());
                System.out.println(subTask.getPriority());
                task.addSubTask(subTask.getSubTitle(), subTask.getDueDate(), subTask.getPriority());
            }
        }

        else {
            task = new Task(taskMainTitle, dueDate, priority);
        }
        reference.child("Task").push().setValue(task);
        System.out.println("Success");
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
