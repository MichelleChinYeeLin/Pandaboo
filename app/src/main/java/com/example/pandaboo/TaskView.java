package com.example.pandaboo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class TaskView extends AppCompatActivity {

    final String firebaseURL = "https://pandaboodcs-default-rtdb.asia-southeast1.firebasedatabase.app";
    //public static String userName = "admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_view);

        ImageButton addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskView.this, TaskAdd.class);
                startActivity(intent);
            }
        });
    }
}
