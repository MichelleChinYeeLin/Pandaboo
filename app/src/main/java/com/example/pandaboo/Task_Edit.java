package com.example.pandaboo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Task_Edit extends AppCompatActivity {

    private View TaskBackButton;
    private AlertDialog.Builder spinnerLangauges;
    final String firebaseURL = "https://pandaboodcs-default-rtdb.asia-southeast1.firebasedatabase.app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_edit);

        TaskBackButton = findViewById(R.id.TaskBackButton1);
        TaskBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
            ImageButton taskAddButton = (ImageButton) findViewById(R.id.taskAddButton);
        });

        //dropdown menu related
        Spinner spinner = (Spinner) findViewById(R.id.dropDownSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
    }

    void saveTodo() {
        // first section
        // get the data to save in our firebase db
        EditText mainTask = (EditText) findViewById(R.id.taskTitle);
        EditText subTask = (EditText) findViewById(R.id.subTaskTextEdit);
        EditText dueDate = (EditText) findViewById(R.id.dueDate);


        /* //can use datepicker for the date things, need to change dueDate to datepicker in xml
        Date dueDate = new Date();
        date.setMonth(datePicker.getMonth());
        date.setYear(datePicker.getYear());
        date.setDate(datePicker.getDayOfMonth());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String dateString = format.format(date);*/


        //firebase db
        FirebaseDatabase database = FirebaseDatabase.getInstance(firebaseURL);
        String key = database.getReference("todoList").push().getKey();

        Todo todo = new Todo();
        todo.setName(mainTask.getText().toString());
        todo.setMessage(subTask.getText().toString());
        todo.setDate(subTask.getText().toString());
        // todo.setDate(dateString);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key, todo.toFirebaseObject());
        database.getReference("todoList").updateChildren(childUpdates, (databaseError, databaseReference) -> {
            if (databaseError == null) {
                finish();
            }
        });
    }


}
