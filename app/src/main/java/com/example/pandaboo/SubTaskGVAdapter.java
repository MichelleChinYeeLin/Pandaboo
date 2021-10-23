package com.example.pandaboo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SubTaskGVAdapter extends ArrayAdapter<SubTask> {

    //Constructor
    public SubTaskGVAdapter(@NonNull Context context, ArrayList<SubTask> subTaskArrayList){
        super(context, 0, subTaskArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //Display the card view
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.task_subtask_card, parent, false);
        }

        return listitemView;
    }
}
