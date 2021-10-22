package com.example.pandaboo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Task_Edit extends AppCompatActivity {

    private View TaskBackButton;
    private AlertDialog.Builder spinnerLangauges;
    final String firebaseURL = "https://pandaboodcs-default-rtdb.asia-southeast1.firebasedatabase.app";
    Button selectDate;
    TextView Date;
    TextView displaySpinner;
    DatePickerDialog datePickerDialog;
    int year,month, dayOfMonth;
    Calendar calendar;

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

        selectDate = findViewById(R.id.dueDateButton);
        Date = findViewById(R.id.selectdDate);

        selectDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Task_Edit.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                Date.setText(day + "/" + (month+1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        //dropdown menu related
        Spinner spinner = (Spinner) findViewById(R.id.dropDownSpinner);
        //String size = spinner.getSelectedItem().toString();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        /*spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //update text view
                displaySpinner.setText(spinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            } */
         }

    void saveTodo() {
        // first section
        // get the data to save in our firebase db
        EditText mainTask = (EditText) findViewById(R.id.taskTitleEdit);
        EditText subTask = (EditText) findViewById(R.id.subTaskTextEdit);
        Button dueDate = (Button) findViewById(R.id.dueDateButton);


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
