package com.example.pandaboo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

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

        Task task = getItem(position);

        TextView taskMainTitleText = listitemView.findViewById(R.id.taskMainTitle);
        TextView taskDueDateText = listitemView.findViewById(R.id.taskDueDate);
        GridView subTaskGridView = listitemView.findViewById(R.id.subTaskGridView);

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

        return listitemView;
    }
}
