package com.example.pandaboo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Planner extends AppCompatActivity implements View.OnClickListener{

    //CalendarView simpleCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planner);

        /*
        simpleCalendarView = (CalendarView) findViewById(R.id.simpleCalendarView); // get the reference of CalendarView
        simpleCalendarView.setFocusedMonthDateColor(Color.BLACK); // set the black color for the dates of focused month
        simpleCalendarView.setUnfocusedMonthDateColor(Color.BLUE); // set the blue color for the dates of an unfocused month
        simpleCalendarView.setWeekSeparatorLineColor(Color.WHITE); // white color for the week separator line
        // perform setOnDateChangeListener event on CalendarView
        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // display the selected date
                Toast.makeText(getApplicationContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
            }
        });
         */
    }

    @Override
    public void onClick(View view) {
        //next button
        //previous button
    }
}