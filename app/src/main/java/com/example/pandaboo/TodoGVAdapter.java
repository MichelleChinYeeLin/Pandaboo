package com.example.pandaboo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TodoGVAdapter extends ArrayAdapter<Todo> {

    public TodoGVAdapter(@NonNull Context context, ArrayList<Todo> todoArrayList) {
        super(context, 0, todoArrayList );
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        //Display the card view
        View listitemView = convertView;
        if (listitemView == null){
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.add_task, parent, false);
        }

        Todo todo = getItem(position);

        //Initialization of elements in the card view
        RelativeLayout addTaskArea = listitemView.findViewById(R.id.addTaskArea);
        TextView addTaskText = listitemView.findViewById(R.id.addTaskText);
        ImageButton addSubTaskButton = listitemView.findViewById(R.id.addSubTaskButton);

        //if(){}

        return listitemView;
    }
}
