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
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    FloatingActionButton addEvent;
    TextView monthYear;
    GridView gridView, eventGridView;
    private static final int MAX_CALENDAR_DAYS = 42;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    Context context;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy",Locale.ENGLISH);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM",Locale.ENGLISH);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy",Locale.ENGLISH);
    SimpleDateFormat eventDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    GridAdapter gridAdapter;
    AlertDialog alertDialog;
    ArrayList<Date> dates = new ArrayList<>();
    ArrayList<Event> eventsList = new ArrayList<>();
    int alarmYear, alarmMonth, alarmDay, alarmHour, alarmMinute;
    long maxid = 0;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planner_view);

        reff = FirebaseDatabase.getInstance().getReference();

        backButton = findViewById(R.id.backButton);
        prevButton = findViewById(R.id.prevButton);
        nextButton = findViewById(R.id.nextButton);
        gridView = findViewById(R.id.gridView);
        eventGridView = findViewById(R.id.eventGridView);
        addEvent = findViewById(R.id.add_event);
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

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PlannerView.this);
                builder.setCancelable(true);
                View addView = LayoutInflater.from(view.getContext()).inflate(R.layout.event_add, null);
                EditText EventTitle = addView.findViewById(R.id.eventTitle);
                EditText EventDetails = addView.findViewById(R.id.details);

                TextView EventStartTime = addView.findViewById(R.id.setStartTimeText);
                TextView EventEndTime = addView.findViewById(R.id.setEndTimeText);
                TextView EventDate1 = addView.findViewById(R.id.setDateText1);
                TextView EventDate2 = addView.findViewById(R.id.setDateText2);

                ImageButton SetStartTime = addView.findViewById(R.id.selectStartTime);
                ImageButton SetEndTime = addView.findViewById(R.id.selectEndTime);
                ImageButton SetDate1 = addView.findViewById(R.id.selectDate1);
                ImageButton SetDate2 = addView.findViewById(R.id.selectDate2);

                CheckBox SetReminder1 = addView.findViewById(R.id.setReminder1);
                CheckBox SetReminder2 = addView.findViewById(R.id.setReminder2);
                Calendar dateCalendar = Calendar.getInstance();
                //dateCalendar.setTime(dates.get(i));
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
                SetStartTime.setOnClickListener(new View.OnClickListener() {
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
                                EventStartTime.setText(event_Time);

                                alarmHour = c.get(Calendar.HOUR_OF_DAY);
                                alarmMinute = c.get(Calendar.MINUTE);
                            }
                        }, hours, minutes, false);
                        timePickerDialog.show();
                    }
                });

                SetEndTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                                EventEndTime.setText(event_Time);

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

                //final String date = eventDateFormat.format(dates.get(i));
                //final String month = monthFormat.format(dates.get(i));
                //final String year = yearFormat.format(dates.get(i));

                AddEvent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Toast.makeText(PlannerView.this, "Event Saved", Toast.LENGTH_SHORT).show();

                        String eventTitle = EventTitle.getText().toString();
                        String eventDetails = EventDetails.getText().toString();
                        String eventStartTime = EventStartTime.getText().toString();
                        String eventEndTime = EventEndTime.getText().toString();
                        String firstDate = EventDate1.getText().toString();
                        String secondDate = EventDate2.getText().toString();
                        String notify = "";

                        if(SetReminder1.isChecked()){
                            //Event events = new Event(eventTitle, eventDetails, eventTime, firstDate, secondDate, date, month, year, "7am");
                            Event events = new Event(eventTitle, eventDetails, eventStartTime, eventEndTime, firstDate, secondDate, "7am");
                            notify = "7am";

                            //SaveEvent(eventTitle,eventDetails,eventTime,firstDate,secondDate,date,month,year,"7am");
                            //SetUpCalendar();
                            //Calendar calendar = Calendar.getInstance();
                            //calendar.set(alarmYear, alarmMonth, alarmDay, alarmHour, alarmMinute);
                            //setAlarm(calendar,EventTitle.getText().toString(),EventTime.getText().toString());
                            alertDialog.dismiss();
                        }
                        else if(SetReminder2.isChecked()){
                            notify = "120mins";
                            SetUpCalendar();
                            alertDialog.dismiss();
                        }

                        SaveEvent(eventTitle, eventDetails, eventStartTime, eventEndTime, firstDate, secondDate, notify);
                    }
                });

                builder.setView(addView);
                alertDialog = builder.create();
                alertDialog.show();
            }
        });

        /*gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
                //EventRecyclerAdapter eventRecyclerAdapter = new EventRecyclerAdapter(showView.getContext(),CollectEventByDate(date));
                //recyclerView.setAdapter(eventRecyclerAdapter);
                //eventRecyclerAdapter.notifyDataSetChanged();

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
        });*/

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                GridAdapter adapter = new GridAdapter(PlannerView.this, dates, calendar, eventsList);
                ArrayList<Event> eventArrayList = new ArrayList<>();

                String date = adapter.getDay(position);
                String[] splitDate = date.split(" ");
                String month = monthValue(splitDate[1]);
                String day = splitDate[2];
                String year = splitDate[5];

                String selectDate = year + "-" + month + "-" + day;

                for (Event event: eventsList){
                    System.out.println("Test1");
                    if (event.getFirstDATE().equals(selectDate) || event.secondDATE.equals(selectDate)){
                        eventArrayList.add(event);
                        System.out.println("Test2");
                    }
                }


                EventGVAdapter eventAdapter = new EventGVAdapter(PlannerView.this, eventArrayList);
                eventGridView.setAdapter(eventAdapter);
            }
        });

    }
    //get events by date from database
    private ArrayList<Event> CollectEventByDate(String date){
        ArrayList<Event> arrayList = new ArrayList<>();

        return arrayList;
    }

    //wut the saveButton does
    private void SaveEvent(String event, String details, String startTime, String endTime, String date1, String date2, String notify){
        reff = FirebaseDatabase.getInstance().getReference().child("admin").child("Event");
        //Event events = new Event(event, details, time, date1, date2, date, month, year, notify);
        Event events = new Event(event, details, startTime, endTime, date1, date2, notify);

        reff.push().setValue(events);

        Toast.makeText(PlannerView.this, "Event Saved", Toast.LENGTH_SHORT).show();
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

        reff = FirebaseDatabase.getInstance().getReference().child("admin").child("Event");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                eventsList.clear();

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String eventName = dataSnapshot.child("event").getValue(String.class);
                    String eventDetails = dataSnapshot.child("details").getValue(String.class);
                    String eventStartTime = dataSnapshot.child("startTIME").getValue(String.class);
                    String eventEndTime = dataSnapshot.child("endTIME").getValue(String.class);
                    String eventFirstDate = dataSnapshot.child("firstDATE").getValue(String.class);
                    String eventSecondDate = dataSnapshot.child("secondDATE").getValue(String.class);
                    String eventNotify = dataSnapshot.child("notify").getValue(String.class);

                    Event event = new Event (eventName, eventDetails, eventStartTime, eventEndTime, eventFirstDate, eventSecondDate, eventNotify);
                    eventsList.add(event);
                }

                gridAdapter = new GridAdapter(PlannerView.this, dates, calendar, eventsList);
                gridView.setAdapter(gridAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /*
    //get events by month from database
    private void CollectEventsPerMonth(String event, String details, String time, String date1, String date2, String date, String month, String year, String notify){
        Event events = new Event(event, details, time, date1, date2, notify);
        eventsList.add(events);
    }*/

    public String monthValue(String month){
        String monthValue = "";

        switch(month){
            case "Jan": monthValue = "1";
                break;
            case "Feb": monthValue = "2";
                break;
            case "Mar": monthValue = "3";
                break;
            case "Apr": monthValue = "4";
                break;
            case "May": monthValue = "5";
                break;
            case "Jun": monthValue = "6";
                break;
            case "Jul": monthValue = "7";
                break;
            case "Aug": monthValue = "8";
                break;
            case "Sep": monthValue = "9";
                break;
            case "Oct": monthValue = "10";
                break;
            case "Nov": monthValue = "11";
                break;
            case "Dec": monthValue = "12";
        }

        return monthValue;
    }
}