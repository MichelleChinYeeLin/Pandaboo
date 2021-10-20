package com.example.pandaboo;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.util.AttributeSet;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PlannerView extends AppCompatActivity {

    final String firebaseURL = "https://pandaboodcs-default-rtdb.asia-southeast1.firebasedatabase.app";
    DatabaseReference reff;

    Button backButton;
    ImageButton nextButton;
    ImageButton prevButton;
    ImageButton addEvent;
    TextView monthYear;
    GridView gridView;
    private static final int MAX_CALENDAR_DAYS = 42;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    Context context;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy",Locale.ENGLISH);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM",Locale.ENGLISH);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy",Locale.ENGLISH);
    SimpleDateFormat eventDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    GridAdapter gridAdapter;
    AlertDialog alertDialog;
    List<Date> dates = new ArrayList<>();
    List<Event> eventsList = new ArrayList<>();
    int alarmYear, alarmMonth, alarmDay, alarmHour, alarmMinute;
    long maxid = 0;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planner_view);

        reff = FirebaseDatabase.getInstance().getReference().child("admin");

        backButton = findViewById(R.id.backButton);
        prevButton = findViewById(R.id.prevButton);
        nextButton = findViewById(R.id.nextButton);
        gridView = findViewById(R.id.gridView);
        addEvent = findViewById(R.id.addEvent);
        monthYear = findViewById(R.id.monthYear);

        SetUpCalendar();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MONTH,-1);
                SetUpCalendar();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MONTH,1);
                SetUpCalendar();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PlannerView.this);
                builder.setCancelable(true);
                View addView = LayoutInflater.from(adapterView.getContext()).inflate(R.layout.event_add, null);
                EditText EventTitle = addView.findViewById(R.id.eventTitle);
                EditText EventDetails = addView.findViewById(R.id.details);
                TextView EventTime = addView.findViewById(R.id.setTimeText);
                TextView EventDate1 = addView.findViewById(R.id.setDateText1);
                TextView EventDate2 = addView.findViewById(R.id.setDateText2);
                ImageButton SetTime = addView.findViewById(R.id.selectTime);
                ImageButton SetDate1 = addView.findViewById(R.id.selectDate1);
                ImageButton SetDate2 = addView.findViewById(R.id.selectDate2);
                CheckBox SetReminder1 = addView.findViewById(R.id.setReminder1);
                CheckBox SetReminder2 = addView.findViewById(R.id.setReminder2);
                Calendar dateCalendar = Calendar.getInstance();
                dateCalendar.setTime(dates.get(i));
                alarmYear = dateCalendar.get(Calendar.YEAR);
                alarmMonth = dateCalendar.get(Calendar.MONTH);
                alarmDay = dateCalendar.get(Calendar.DAY_OF_MONTH);

                Button AddEvent = addView.findViewById(R.id.saveButton);
                Button backButton = addView.findViewById(R.id.backButton);
                backButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                SetTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar calendar = Calendar.getInstance();
                        int hours = calendar.get(Calendar.HOUR_OF_DAY);
                        int minutes = calendar.get(calendar.MINUTE);
                        TimePickerDialog timePickerDialog = new TimePickerDialog(addView.getContext(), R.style.Theme_AppCompat_Dialog, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                Calendar c = Calendar.getInstance();
                                c.set(Calendar.HOUR_OF_DAY, i);
                                c.set(Calendar.MINUTE, i1);
                                c.setTimeZone(TimeZone.getDefault());
                                SimpleDateFormat hformat = new SimpleDateFormat("K:mm a", Locale.ENGLISH);
                                String event_Time = hformat.format(c.getTime());
                                EventTime.setText(event_Time);

                                alarmHour = c.get(Calendar.HOUR_OF_DAY);
                                alarmMinute = c.get(Calendar.MINUTE);
                            }
                        }, hours, minutes, false);
                        timePickerDialog.show();
                    }
                });
                SetDate1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(addView.getContext(), R.style.Theme_AppCompat_DayNight_Dialog, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                Calendar newDate = Calendar.getInstance();
                                newDate.set(Calendar.YEAR, i);
                                newDate.set(Calendar.MONTH, i1);
                                newDate.set(Calendar.DAY_OF_MONTH, i2);
                                SimpleDateFormat d1format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                                String event_Date1 = d1format.format(newDate.getTime());
                                EventDate1.setText(event_Date1);
                            }
                        }, year, month, dayOfMonth);
                        datePickerDialog.show();
                    }
                });
                SetDate2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar calendar = Calendar.getInstance();
                        int years = calendar.get(Calendar.YEAR);
                        int months = calendar.get(Calendar.MONTH);
                        int dayOfMonths = calendar.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(addView.getContext(), R.style.Theme_AppCompat_DayNight_Dialog, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                Calendar newDates = Calendar.getInstance();
                                newDates.set(Calendar.YEAR, i);
                                newDates.set(Calendar.MONTH, i1);
                                newDates.set(Calendar.DAY_OF_MONTH, i2);
                                SimpleDateFormat d2format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                                String event_Date2 = d2format.format(newDates.getTime());
                                EventDate2.setText(event_Date2);
                            }
                        }, years, months, dayOfMonths);
                        datePickerDialog.show();
                    }
                });
                final String date = eventDateFormat.format(dates.get(i));
                final String month = monthFormat.format(dates.get(i));
                final String year = yearFormat.format(dates.get(i));

                AddEvent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Toast.makeText(PlannerView.this, "Event Saved", Toast.LENGTH_SHORT).show();

                        //reff.child("Events").push().setValue("event_title");

                        String eventTitle = EventTitle.getText().toString();
                        String eventDetails = EventDetails.getText().toString();
                        String eventTime = EventTime.getText().toString();
                        String firstDate = EventDate1.getText().toString();
                        String secondDate = EventDate2.getText().toString();
                        if(SetReminder1.isChecked()){
                            Event events = new Event(eventTitle, eventDetails, eventTime, firstDate, secondDate, date, month, year, "7am");
                            events.setEVENT(eventTitle);
                            events.setDETAILS(eventDetails);
                            events.setTIME(eventTime);
                            //events.setFirstDATE(eventDateFormat.format(firstDate));
                            //events.setSecondDATE(eventDateFormat.format(secondDate));
                            events.setFirstDATE(firstDate);
                            events.setSecondDATE(secondDate);
                            events.setDATE(date);
                            events.setMONTH(month);
                            events.setYEAR(year);
                            events.setNOTIFY("7am");

                            SaveEvent(eventTitle,eventDetails,eventTime,firstDate,secondDate,date,month,year,"7am");
                            //SetUpCalendar();
                            //Calendar calendar = Calendar.getInstance();
                            //calendar.set(alarmYear, alarmMonth, alarmDay, alarmHour, alarmMinute);
                            //setAlarm(calendar,EventTitle.getText().toString(),EventTime.getText().toString());
                            alertDialog.dismiss();
                        }
                        else if(SetReminder2.isChecked()){
                            SaveEvent(eventTitle,eventDetails,eventTime,firstDate,secondDate,date,month,year,"120min");
                            SetUpCalendar();
                            alertDialog.dismiss();
                        }
                        else{
                            Toast.makeText(PlannerView.this, "Please tick a reminder.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setView(addView);
                alertDialog = builder.create();
                alertDialog.show();
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String date = eventDateFormat.format(dates.get(i));
                AlertDialog.Builder builder = new AlertDialog.Builder(PlannerView.this);
                builder.setCancelable(true);
                View showView = LayoutInflater.from(adapterView.getContext()).inflate(R.layout.event_show,null);
                RecyclerView recyclerView = showView.findViewById(R.id.eventsRV);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(showView.getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                EventRecyclerAdapter eventRecyclerAdapter = new EventRecyclerAdapter(showView.getContext(),CollectEventByDate(date));
                recyclerView.setAdapter(eventRecyclerAdapter);
                eventRecyclerAdapter.notifyDataSetChanged();

                builder.setView(showView);
                alertDialog = builder.create();
                alertDialog.show();
                alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        SetUpCalendar();
                    }
                });

                return true;
            }
        });

    }
    //get events by date from database
    private ArrayList<Event> CollectEventByDate(String date){
        ArrayList<Event> arrayList = new ArrayList<>();

        return arrayList;
    }

    //wut the saveButton does
    private void SaveEvent(String event, String details, String time, String date1, String date2, String date, String month, String year, String notify){

        Event events = new Event(event, details, time, date1, date2, date, month, year, notify);

        /*events.setEVENT(event);
        events.setDETAILS(details);
        events.setTIME(time);
        events.setFirstDATE(eventDateFormat.format(date1));
        events.setSecondDATE(eventDateFormat.format(date2));
        events.setDATE(date);
        events.setMONTH(month);
        events.setYEAR(year);
        events.setNOTIFY(notify);*/

        //reff.child("Events").push().setValue(events);
        reff.child("Events").push().setValue(events);

        System.out.println("testing");

        //Toast.makeText(context, "Event Saved", Toast.LENGTH_SHORT).show();
    }

    private void SetUpCalendar(){
        String currentDate = dateFormat.format(calendar.getTime());

        monthYear.setText(currentDate);
        dates.clear();
        Calendar monthCalendar = (Calendar) calendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int FirstDayOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) -1;
        monthCalendar.add(Calendar.DAY_OF_MONTH, -FirstDayOfMonth);
        //CollectEventsPerMonth();

        while(dates.size() < MAX_CALENDAR_DAYS){
            dates.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        gridAdapter = new GridAdapter(PlannerView.this, dates, calendar, eventsList);
        gridView.setAdapter(gridAdapter);
    }

    //get events by month from database
    private void CollectEventsPerMonth(String event, String details, String time, String date1, String date2, String date, String month, String year, String notify){
        Event events = new Event(event, details, time, date1, date2, date, month, year, notify);
        eventsList.add(events);
    }
}