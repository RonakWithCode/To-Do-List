package com.crazyostudio.to_do_list.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crazyostudio.to_do_list.Adapter.Task_Sub_Adapter;
import com.crazyostudio.to_do_list.DAO.CategoryDatabaseHelper;
import com.crazyostudio.to_do_list.DAO.TaskDatabaseHelper;
import com.crazyostudio.to_do_list.Model.CategoryModel;
import com.crazyostudio.to_do_list.Model.TaskModel;
import com.crazyostudio.to_do_list.Model.Task_Sub_Model;
import com.crazyostudio.to_do_list.databinding.ActivityTaskDetailsBinding;

import java.util.ArrayList;

public class TaskDetailsActivity extends AppCompatActivity {
    ActivityTaskDetailsBinding binding;
//    ArrayList<Boolean> subTaskCheckList = new ArrayList<>();
    int index,id;
    ArrayList<String> sub_task;
    ArrayList<Boolean> sub_task_check;
    Task_Sub_Adapter task_sub_adapter;
    ArrayList<Task_Sub_Model> task_sub_models;
    TaskDatabaseHelper taskDatabaseHelper;
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        /// get data
        taskDatabaseHelper = TaskDatabaseHelper.TasGetDB(this);
        int get;
        get  = Integer.parseInt(getIntent().getStringExtra("index"));
        id  = Integer.parseInt(getIntent().getStringExtra("index"));
        get = get-1;
        index = get;

        binding.TaskDetailsAddSubTask.setOnClickListener(view->{
            task_sub_models.add(new Task_Sub_Model(" ",false));
            task_sub_adapter.notifyDataSetChanged();
        });

        getSubTask();
        binding.TaskTitle.setText(taskDatabaseHelper.taskModelDAO().getAllTaskModel().get(index).getTask());
        binding.TaskCheck.setChecked(taskDatabaseHelper.taskModelDAO().getAllTaskModel().get(index).getTaskCheck());
        binding.TaskNotes.setText(taskDatabaseHelper.taskModelDAO().getAllTaskModel().get(index).getNotes());
        CategorySpinner(taskDatabaseHelper.taskModelDAO().getAllTaskModel().get(index).getCategory());
//    Changing data
    }

    private void getSubTask() {
        task_sub_models = new ArrayList<>();
        task_sub_adapter = new Task_Sub_Adapter(task_sub_models,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.SubTaskRecycler.setLayoutManager(layoutManager);
        binding.SubTaskRecycler.setAdapter(task_sub_adapter);
        sub_task = new ArrayList<>();
        sub_task_check = new ArrayList<>();
        System.out.println("index "+index);
        sub_task.addAll(taskDatabaseHelper.taskModelDAO().getAllTaskModel().get(index).getSub_Task());
        sub_task_check.addAll(taskDatabaseHelper.taskModelDAO().getAllTaskModel().get(index).getSub_check());
        for (int i = 0; i < sub_task.size(); i++) {
            task_sub_models.add(new Task_Sub_Model(sub_task.get(i),sub_task_check.get(i)));
        }
        task_sub_adapter.notifyDataSetChanged();
    }

    private void CategorySpinner(String category) {
        ArrayAdapter<String> CategorySpinnerAdapter;
        CategoryDatabaseHelper categoryDatabaseHelper = CategoryDatabaseHelper.getDB(this);
        binding.TaskDetailsCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        int CategoryIndex = 0;
        ArrayList<String> CategorySpinnerDate = new ArrayList<>();
        ArrayList<CategoryModel> categoryModels = (ArrayList<CategoryModel>) categoryDatabaseHelper.categoryModelDAO().getAllCategory();
        CategorySpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, CategorySpinnerDate);
        for (int i = 0; i < categoryModels.size(); i++) {
            if(categoryModels.get(i).getCategoryName().equals(category)){
                CategoryIndex = i;
            }
            CategorySpinnerDate.add(categoryModels.get(i).getCategoryName());
            CategorySpinnerAdapter.notifyDataSetChanged();
        }
        CategorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.TaskDetailsCategorySpinner.setAdapter(CategorySpinnerAdapter);

        binding.TaskDetailsCategorySpinner.setSelection(CategoryIndex);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Toast.makeText(this, "onBackPressed", Toast.LENGTH_SHORT).show();
        changingData();
    }
    private void changingData() {
        ArrayList<String> sub_task_String = new ArrayList<>();
        ArrayList<Boolean> sub_task_Check = new ArrayList<>();
        for (int i = 0; i < task_sub_models.size(); i++) {
            sub_task_String.add(task_sub_models.get(i).getSub_task());
            sub_task_Check.add(task_sub_models.get(i).isTrue());
        }
        TaskModel taskModel = new TaskModel();
        taskModel.setId(id);
        taskModel.setTask(binding.TaskTitle.getText().toString());
        taskModel.setNotes(binding.TaskNotes.getText().toString());
        taskModel.setCategory(binding.TaskDetailsCategorySpinner.getSelectedItem().toString());
        taskModel.setTaskCheck(binding.TaskCheck.isChecked());
        taskModel.setSub_Task(sub_task_String);
        taskModel.setSub_check(sub_task_Check);
        taskDatabaseHelper.taskModelDAO().UpdateTaskModel(taskModel);
        finish();
    }

}