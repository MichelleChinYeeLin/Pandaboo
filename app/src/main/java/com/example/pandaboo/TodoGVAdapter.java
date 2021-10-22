package com.example.pandaboo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.task_edit_cardview, parent, false);
        }

        Todo todo = getItem(position);

        //Initialization of elements in the card view
        RelativeLayout subTaskCard = listitemView.findViewById(R.id.subTaskCard);
        RelativeLayout subTaskText1 = listitemView.findViewById(R.id.subTaskText1);
        RelativeLayout timeAndPriority = listitemView.findViewById(R.id.timeAndPriority);
        RelativeLayout priority = listitemView.findViewById(R.id.priority);
        RelativeLayout taskDropDown = listitemView.findViewById(R.id.taskDropDown);
        RelativeLayout cancelTaskArea = listitemView.findViewById(R.id.cancelTaskArea);
        Button dueDateButton = listitemView.findViewById(R.id.dueDateButton);
        EditText subTaskTextEdit1 = listitemView.findViewById(R.id.subTaskTextEdit1);
        TextView addTaskText = listitemView.findViewById(R.id.addTaskText);
        TextView selectdDate = listitemView.findViewById(R.id.selectdDate);
        TextView prorityText = listitemView.findViewById(R.id.prorityText);
        ImageButton addSubTaskButton = listitemView.findViewById(R.id.addSubTaskButton);
        Spinner dropDownSpinner = listitemView.findViewById(R.id.dropDownSpinner);

        //if(){}

        return listitemView;
    }
}
