package com.example.pandaboo;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EventGVAdapter extends ArrayAdapter<Event> {

    private String eventStartDate;
    private String eventEndDate;

    public EventGVAdapter(@NonNull Context context, ArrayList<Event> eventArrayList){
        super(context, 0, eventArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View listitemView = convertView;
        if (listitemView == null){
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.planner_card_event, parent, false);
        }

        Event event = getItem(position);

        TextView eventTitle = listitemView.findViewById(R.id.eventTitle);
        TextView eventStartTime = listitemView.findViewById(R.id.eventStartTime);
        TextView eventEndTime = listitemView.findViewById(R.id.eventEndTime);
        ImageButton eventEditButton = listitemView.findViewById(R.id.eventEditButton);

        eventTitle.setText(event.getEVENT());
        eventStartTime.setText(event.getStartTIME());
        eventEndTime.setText(event.getEndTIME());

        return listitemView;
    }
}

