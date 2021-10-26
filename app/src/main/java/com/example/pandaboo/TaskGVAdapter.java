package com.example.pandaboo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TaskGVAdapter extends ArrayAdapter<Task>{

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

        Task task = getItem(position);

        TextView taskMainTitleText = listitemView.findViewById(R.id.taskMainTitle);
        TextView taskDueDateText = listitemView.findViewById(R.id.taskDueDate);
        GridView subTaskGridView = listitemView.findViewById(R.id.subTaskGridView);
        ImageButton editButton = listitemView.findViewById(R.id.editButton);

        if (task.getSubTaskArraySize() > 0){
            taskMainTitleText.setText(task.getMainTitle());
            taskDueDateText.setVisibility(View.GONE);

            ArrayList<SubTask> subTaskArrayList = task.getSubTask();

            SubTaskGVAdapter adapter = new SubTaskGVAdapter(super.getContext(), subTaskArrayList);
            subTaskGridView.setAdapter(adapter);
        }

        else {
            taskMainTitleText.setText(task.getMainTitle());
            taskDueDateText.setText("Due Date: " + task.getDueDate());
            String priority = task.getPriority();
            subTaskGridView.setVisibility(View.GONE);


            if (priority.equals("High")){
                taskMainTitleText.setBackgroundResource(R.color.ashrose);
            }

            else if (priority.equals("Medium")){
                taskMainTitleText.setBackgroundResource(R.color.pale_yellow);
            }

            else {
                taskMainTitleText.setBackgroundResource(R.color.light_blue);
            }
        }

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //editTask(parent, task, position);

                Intent intent = new Intent(getContext(), TaskAdd.class);
                intent.putExtra("task", task);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("subtask", task.getSubTask());
                intent.putExtras(bundle);
                getContext().startActivity(intent);
            }
        });

        return listitemView;
    }

    public void editTask(ViewGroup parent, Task task)
    {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        View editView = LayoutInflater.from(getContext()).inflate(R.layout.task_edit, parent, false);
        dialog = builder.create();
        dialog.show();

        ImageButton addSubTaskButton = editView.findViewById(R.id.addSubTaskButton);
        GridView subTaskGridView = editView.findViewById(R.id.subTaskGridView);
        EditText taskMainTitleText = editView.findViewById(R.id.taskMainTitle);
        Button deleteButton = editView.findViewById(R.id.deleteButton);
        Button saveButton = editView.findViewById(R.id.saveButton);
        Button backButton = editView.findViewById(R.id.backButton);
        ImageButton setDueDate = editView.findViewById(R.id.setDueDateButton);
        Spinner prioritySpinner = editView.findViewById(R.id.prioritySpinner);

        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(getContext(), R.array.priority_text, android.R.layout.simple_spinner_item);
        prioritySpinner.setAdapter(staticAdapter);
        prioritySpinner.setSelection(staticAdapter.getPosition(task.getPriority()));

        prioritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String priority = (parent.getItemAtPosition(position)).toString();
                task.setPriority(priority);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                task.setPriority("High");
            }
        });
    }
}
