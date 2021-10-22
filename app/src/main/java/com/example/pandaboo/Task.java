package com.example.pandaboo;

import static androidx.recyclerview.widget.RecyclerView.*;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Task extends AppCompatActivity {

    private View TaskBackButton;
    private AlertDialog.Builder spinnerLangauges;
    final String firebaseURL = "https://pandaboodcs-default-rtdb.asia-southeast1.firebasedatabase.app";
    GridView adapter;
    ArrayList<Todo> todoList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task);

        TaskBackButton = findViewById(R.id.TaskBackButton);
        TaskBackButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageButton taskAddButton = (ImageButton) findViewById(R.id.taskAddButton);
        taskAddButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(Task.this,Task_Edit.class);
                startActivity(newIntent);
            }
        });
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }
    @Override
    protected void onResume() {
        super.onResume();
        FirebaseDatabase database = FirebaseDatabase.getInstance(firebaseURL);

        database.getReference("todoList").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        todoList.clear();

                        Log.w("TodoApp", "getUser:onCancelled " + dataSnapshot.toString());
                        Log.w("TodoApp", "count = " + String.valueOf(dataSnapshot.getChildrenCount()) + " values " + dataSnapshot.getKey());
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            String taskName = dataSnapshot.child("TaskName").getValue(String.class);
                            String taskMessage = dataSnapshot.child("TaskMessage").getValue(String.class);
                            String taskDate = dataSnapshot.child("TaskDate").getValue(String.class);
                            String taskPriority = dataSnapshot.child("TaskPriority").getValue(String.class);

                            Todo todo = new Todo(taskName, taskMessage, taskDate, taskPriority);
                            todoList.add(todo);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("TodoApp", "getUser:onCancelled", databaseError.toException());
                    }
                });
    }


    public void showSubtext(){
        //Initialization of variables to create a dialog box
        androidx.appcompat.app.AlertDialog dialog;
        androidx.appcompat.app.AlertDialog.Builder dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        dialogBuilder.setView(inflater.inflate(R.layout.add_task, null));
        dialog = dialogBuilder.create();

        //Show the credits dialog box
        dialog.show();

        GridView gridViewTask = dialog.findViewById(R.id.gridViewTask);
    }


}





