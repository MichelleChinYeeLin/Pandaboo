package com.example.pandaboo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskGVAdapter extends ArrayAdapter<Task> {

    //Constructor
    public TaskGVAdapter(@NonNull Context context, ArrayList<Task> taskArrayList){
        super(context, 0, taskArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //Display the card view
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.task_card, parent, false);
        }

        return listitemView;
    }
}
