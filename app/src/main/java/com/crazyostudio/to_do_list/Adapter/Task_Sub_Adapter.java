package com.crazyostudio.to_do_list.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crazyostudio.to_do_list.Model.Task_Sub_Model;
import com.crazyostudio.to_do_list.R;
import com.crazyostudio.to_do_list.databinding.AddSubTaskBinding;

import java.util.ArrayList;

public class Task_Sub_Adapter extends RecyclerView.Adapter<Task_Sub_Adapter.Task_Sub_AdapterViewHolder>{

    ArrayList<Task_Sub_Model> Model;
    Context context;

    public Task_Sub_Adapter(ArrayList<Task_Sub_Model> model, Context context) {
        Model = model;
        this.context = context;
    }

    @NonNull
    @Override
    public Task_Sub_Adapter.Task_Sub_AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Task_Sub_Adapter.Task_Sub_AdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.add_sub_task,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Task_Sub_Adapter.Task_Sub_AdapterViewHolder holder, int position) {
        Task_Sub_Model sub_model = Model.get(position);
        holder.binding.roundCheckbox.setChecked(sub_model.isTrue());
        holder.binding.SubTaskEdittextInput.setText(sub_model.getSub_task());
        holder.binding.SubTaskEdittextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sub_model.setSub_task(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.binding.roundCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> sub_model.setTrue(isChecked));
    }

    @Override
    public int getItemCount() {
        return Model.size();
    }

    public static class Task_Sub_AdapterViewHolder extends RecyclerView.ViewHolder {
        AddSubTaskBinding binding;
        public Task_Sub_AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = AddSubTaskBinding.bind(itemView);
        }
    }
}