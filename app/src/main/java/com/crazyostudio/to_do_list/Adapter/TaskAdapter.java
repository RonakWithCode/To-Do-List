package com.crazyostudio.to_do_list.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crazyostudio.to_do_list.Activity.TaskDetailsActivity;
import com.crazyostudio.to_do_list.Model.TaskModel;
import com.crazyostudio.to_do_list.Model.onCheck;
import com.crazyostudio.to_do_list.R;
import com.crazyostudio.to_do_list.databinding.TaskLayoutBinding;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskAdapterViewHolder>{
    ArrayList<TaskModel> taskModels;
    onCheck onCheck;
    Context context;

    public TaskAdapter(ArrayList<TaskModel> list,onCheck OnCheck,  Context context) {
        this.taskModels = list;
        this.onCheck = OnCheck;
        this.context = context;
    }

    @NonNull
    @Override
    public TaskAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskAdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.task_layout,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskAdapterViewHolder holder, int position) {
        TaskModel task = taskModels.get(position);
        holder.taskLayoutBinding.TaskCheckbox.setText(task.getTask());
        holder.taskLayoutBinding.TaskCheckbox.setChecked(task.getTaskCheck());
        holder.taskLayoutBinding.delete.setOnClickListener(view -> {
            onCheck.onBtnClickDelete(task);
        });
        holder.taskLayoutBinding.pin.setOnClickListener(view -> {
            onCheck.onBtnClickPin(task);
        });


        holder.taskLayoutBinding.TaskLayout.setOnClickListener(view->{
            Intent intent = new Intent(context, TaskDetailsActivity.class);
            intent.putExtra("index",task.getId()+"");
            System.out.println("index"+task.getId());
            intent.putExtra("Task",task.getTask());
            intent.putExtra("TaskCheck",task.getTaskCheck());
            intent.putExtra("TaskNotes",task.getNotes());
            intent.putExtra("Sub_Task",task.getSub_Task());
            intent.putExtra("Sub_TaskCheck",task.getSub_check());
            intent.putExtra("Task_Category",task.getCategory());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return taskModels.size();
    }

    public static class TaskAdapterViewHolder extends RecyclerView.ViewHolder {
        TaskLayoutBinding taskLayoutBinding;
        public TaskAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            taskLayoutBinding = TaskLayoutBinding.bind(itemView);
        }
    }
}
