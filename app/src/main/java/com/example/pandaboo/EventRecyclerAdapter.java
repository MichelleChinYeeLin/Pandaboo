package com.example.pandaboo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.MyViewHolder> {

    Context context;
    ArrayList<Event> arrayList;

    public EventRecyclerAdapter(Context context, ArrayList<Event> arrayList)
    {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_showrow, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Event events = arrayList.get(position);
        holder.Event.setText(events.getEVENT());
        holder.Details.setText(events.getDETAILS());
        holder.Time.setText(events.getTIME());
        holder.firstDate.setText(events.getFirstDATE());
        holder.secondDate.setText(events.getSecondDATE());
        holder.deleteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.remove(events);
            }
        });
        //change alarm
        if(isAlarmed(events.getEVENT(),events.getDETAILS(),events.getTIME(),events.getFirstDATE(),events.getSecondDATE(),events.getNOTIFY())){
            holder.setAlarm.setImageResource(R.drawable.ic_baseline_notifications_active_24);
            //set alarm
        }
        else{
            holder.setAlarm.setImageResource(R.drawable.ic_baseline_notifications_active_24);
        }

        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(ConvertStringToDate(events.getFirstDATE()));
        int alarmYear = dateCalendar.get(Calendar.YEAR);
        int alarmMonth = dateCalendar.get(Calendar.MONTH);
        int alarmDay = dateCalendar.get(Calendar.DAY_OF_MONTH);
        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.setTime(ConvertStringToTime(events.getTIME()));
        int alarmHour = timeCalendar.get(Calendar.HOUR_OF_DAY);
        int alarmMinute = timeCalendar.get(Calendar.MINUTE);

        holder.setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isAlarmed(events.getEVENT(),events.getDETAILS(),events.getTIME(),events.getFirstDATE(),events.getSecondDATE(),events.getNOTIFY())){
                    holder.setAlarm.setImageResource(R.drawable.ic_baseline_notifications_active_24);
                    //cancel alarm
                    //updateEvent
                    notifyDataSetChanged();
                }
                else{
                    Calendar alarmCalendar = Calendar.getInstance();
                    alarmCalendar.set(alarmYear,alarmMonth,alarmDay,alarmHour,alarmMinute);
                    //setAlarm(alarmCalendar,events.getEVENT(),events.getDETAILS(),events.getTIME(),events.getFirstDATE(),events.getSecondDATE(),events.getDATE(),events.getMONTH(),events.getYEAR(),events.getNOTIFY());
                    //updateEvent
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Event, Details, Time, firstDate, secondDate;
        Button deleteEvent;
        ImageButton setAlarm;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            Event = itemView.findViewById(R.id.eventname);
            Details = itemView.findViewById(R.id.eventdetails);
            Time = itemView.findViewById(R.id.eventtime);
            firstDate = itemView.findViewById(R.id.eventdate1);
            secondDate = itemView.findViewById(R.id.eventdate2);
            deleteEvent = itemView.findViewById(R.id.deleteEvent);
            setAlarm = itemView.findViewById(R.id.alarmButton);
        }
    }

    private Date ConvertStringToDate(String eventDate){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = null;
        try{
            date = format.parse(eventDate);
        }catch(ParseException e){
            e.printStackTrace();
        }

        return date;
    }

    private Date ConvertStringToTime(String eventDate){
        SimpleDateFormat format = new SimpleDateFormat("kk:mm", Locale.ENGLISH);
        Date date = null;
        try{
            date = format.parse(eventDate);
        }catch(ParseException e){
            e.printStackTrace();
        }

        return date;
    }

    private boolean isAlarmed(String event, String details, String time, String date1, String date2, String notify){
        boolean alarmed = false;
        Event events = new Event(event, details, time, date1, date2, notify);
        String Notify = events.getNOTIFY();
        if(Notify.equals("7am")){
            alarmed = true;
        }
        else if(Notify.equals("120min")){
            alarmed = false;
        }

        return alarmed;
    }
}
