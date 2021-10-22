package com.example.pandaboo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
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

public class Task_Edit extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private View TaskBackButton;
    private AlertDialog.Builder spinnerLangauges;
    final String firebaseURL = "https://pandaboodcs-default-rtdb.asia-southeast1.firebasedatabase.app";
    Button selectDate;
    TextView Date;
    Button addSubTaskButton;
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
        Spinner spinner = findViewById(R.id.dropDownSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    //dropdown stuff
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void saveTodo() {
        // get the data to save in our firebase db
        EditText mainTask = (EditText) findViewById(R.id.taskTitleEdit);
        EditText subTask = (EditText) findViewById(R.id.subTaskTextEdit);
        Button dueDate = (Button) findViewById(R.id.dueDateButton);

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
