package com.crazyostudio.to_do_list.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crazyostudio.to_do_list.Activity.TaskDetailsActivity;
import com.crazyostudio.to_do_list.Model.TaskModel;
import com.crazyostudio.to_do_list.Model.onCheck;
import com.crazyostudio.to_do_list.R;
import com.crazyostudio.to_do_list.databinding.TaskLayoutBinding;

import java.util.ArrayList;
import java.util.Collections;

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
        // Retrieve your to-do items (e.g., from a Room database)
// Sort the items using the custom comparator
        if (task.isPin()) {
            holder.taskLayoutBinding.pin.setImageResource(R.drawable.ic_baseline_push_pin_24_blue);
        }else {
            holder.taskLayoutBinding.pin.setImageResource(R.drawable.ic_baseline_push_pin_24);

        }
        holder.taskLayoutBinding.TaskCheckbox.setText(task.getTask());
        holder.taskLayoutBinding.TaskCheckbox.setChecked(task.getTaskCheck());
        holder.taskLayoutBinding.delete.setOnClickListener(view -> onCheck.onBtnClickDelete(task));
        holder.taskLayoutBinding.pin.setOnClickListener(view -> {
            boolean change;
            if (task.isPin()) {
                holder.taskLayoutBinding.pin.setImageResource(R.drawable.ic_baseline_push_pin_24);
                change = false;
            }else {
                holder.taskLayoutBinding.pin.setImageResource(R.drawable.ic_baseline_push_pin_24_blue);
                change = true;
            }

            onCheck.onBtnClickPin(task,change);

        });
        holder.taskLayoutBinding.TaskCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> onCheck.onBtnClickCheckBox(task,isChecked));

        holder.taskLayoutBinding.TaskLayout.setOnClickListener(view->{
            Intent intent = new Intent(context, TaskDetailsActivity.class);
            intent.putExtra("index",task.getId()+"");
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
