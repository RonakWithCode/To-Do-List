package com.crazyostudio.to_do_list.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crazyostudio.to_do_list.Adapter.TaskAdapter;
import com.crazyostudio.to_do_list.Adapter.Task_Sub_Adapter;
import com.crazyostudio.to_do_list.DAO.CategoryDatabaseHelper;
import com.crazyostudio.to_do_list.DAO.TaskDatabaseHelper;
import com.crazyostudio.to_do_list.Model.CategoryModel;
import com.crazyostudio.to_do_list.Model.TaskModel;
import com.crazyostudio.to_do_list.Model.Task_Sub_Model;
import com.crazyostudio.to_do_list.Model.onCheck;
import com.crazyostudio.to_do_list.R;
import com.crazyostudio.to_do_list.databinding.AddcategorymenuboxBinding;
import com.crazyostudio.to_do_list.databinding.AddnewtaskboxBinding;
import com.crazyostudio.to_do_list.databinding.FragmentTaskBinding;

import java.util.ArrayList;
import java.util.Date;

public class TaskFragment extends Fragment implements onCheck {
    FragmentTaskBinding binding;
    ArrayList<String> CategorySpinnerDate;
    ArrayAdapter<String> CategorySpinnerAdapter;
    TaskAdapter taskAdapter;
    public TaskFragment(){}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTaskBinding.inflate(inflater,container,false);
          // Load Task
        CategorySetData(); // Check if Category is Entity set CategoryData
        DateSpinner(); // Load Data Spinner
        CategorySpinner(); // Load Data Category
        FilterSpinner(); // Load Data Filter
        binding.AddTaskFAB.setOnClickListener(v->{
            ShowAddTaskDialog();
        });
        return binding.getRoot();
    }
    @SuppressLint("NotifyDataSetChanged")
    private void ShowAddTaskDialog() {
        AddnewtaskboxBinding taskBinding;
        taskBinding = AddnewtaskboxBinding.inflate(getLayoutInflater());
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(taskBinding.getRoot());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.newcategoryboxbg);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.CategorySpinnerAnimation;
        ArrayList<Task_Sub_Model> task_sub_models = new ArrayList<>();
        Task_Sub_Adapter task_sub_adapter;
        task_sub_adapter = new Task_Sub_Adapter(task_sub_models,getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        taskBinding.ListSubTask.setLayoutManager(layoutManager);
        taskBinding.ListSubTask.setAdapter(task_sub_adapter);

        taskBinding.InputForTask.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (taskBinding.InputForTask.getText().toString().isEmpty()) {
//                    taskBinding.saveTask.setTextColor(Integer.getInteger("#ccdcf6"));
                    taskBinding.saveTask.setVisibility(View.INVISIBLE);
                }else {
                    taskBinding.saveTask.setVisibility(View.VISIBLE);
                }
            }
        });
        taskBinding.addSubTask.setOnClickListener(view->{
            task_sub_models.add(new Task_Sub_Model("Sub Notes",false));
            task_sub_adapter.notifyDataSetChanged();
        });
        taskBinding.addNoteTask.setOnClickListener(notes->{
            taskBinding.InputForNote.setVisibility(View.VISIBLE);
        });
        taskBinding.cancelTask.setOnClickListener(cancel->dialog.dismiss());
        taskBinding.saveTask.setOnClickListener(cancel->{
            TaskDatabaseHelper TaskDatabaseHelper = com.crazyostudio.to_do_list.DAO.TaskDatabaseHelper.TasGetDB(getContext());
            ArrayList<String> sub_Task_ = new ArrayList<>();
            ArrayList<Boolean> sub_Check_ = new ArrayList<>();
            for (int i = 0; i < task_sub_models.size(); i++) {
                sub_Task_.add(task_sub_models.get(i).getSub_task());
                sub_Check_.add(task_sub_models.get(i).isTrue());
            }
            TaskDatabaseHelper.taskModelDAO().insertTaskModel(new TaskModel(taskBinding.InputForTask.getText().toString(),taskBinding.InputForNote.getText().toString(),taskBinding.CategorySpinnerAddTask.getSelectedItem().toString(),false,sub_Task_,sub_Check_,new Date(),false));
            taskAdapter.notifyDataSetChanged();
            GetTask();
            dialog.dismiss();
            });
        TaskCategorySpinner(taskBinding.CategorySpinnerAddTask);
        dialog.show();
    }
    private void FilterSpinner() {
        binding.filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                switch (position){
//                    case 0:
//                        Toast.makeText(getContext(), "0", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 1:
//                        Toast.makeText(getContext(), "1", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 2:
//                        Toast.makeText(getContext(), "2", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 3:
//                        Toast.makeText(getContext(), "3", Toast.LENGTH_SHORT).show();
//                        break;
//                }      switch (position){
//                    case 0:
//                        Toast.makeText(getContext(), "0", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 1:
//                        Toast.makeText(getContext(), "1", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 2:
//                        Toast.makeText(getContext(), "2", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 3:
//                        Toast.makeText(getContext(), "3", Toast.LENGTH_SHORT).show();
//                        break;
//                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String[] filter = getResources().getStringArray(R.array.filter);
        ArrayAdapter<String> filterAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, filter);
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.filterSpinner.setAdapter(filterAdapter);
    }
    private void CategorySpinner() {
        CategoryDatabaseHelper categoryDatabaseHelper = CategoryDatabaseHelper.getDB(getContext());
        binding.CategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                binding.CategorySpinner.getSelectedItem().toString();
                TaskDatabaseHelper taskDatabaseHelper = TaskDatabaseHelper.TasGetDB(getContext());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ArrayList<String> CategorySpinnerDate = new ArrayList<>();
        ArrayList<CategoryModel> categoryModels = (ArrayList<CategoryModel>) categoryDatabaseHelper.categoryModelDAO().getAllCategory();
        CategorySpinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, CategorySpinnerDate);
        for (int i = 0; i < categoryModels.size(); i++) {
            CategorySpinnerDate.add(categoryModels.get(i).getCategoryName());
            CategorySpinnerAdapter.notifyDataSetChanged();
        }
        CategorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.CategorySpinner.setAdapter(CategorySpinnerAdapter);

    }
    private void DateSpinner() {
        binding.dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                switch (position){
//                    case 0:
//                        Toast.makeText(getContext(), "0", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 1:
//                        Toast.makeText(getContext(), "1", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 2:
//                        Toast.makeText(getContext(), "2", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 3:
//                        Toast.makeText(getContext(), "3", Toast.LENGTH_SHORT).show();
//                        break;
//                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String[] Date = getResources().getStringArray(R.array.testArray);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, Date);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.dateSpinner.setAdapter(dataAdapter);
    }
    private void CategorySetData() {
        CategoryDatabaseHelper categoryDatabaseHelper = CategoryDatabaseHelper.getDB(getContext());
        if(categoryDatabaseHelper.categoryModelDAO().getAllCategory().isEmpty()) {
            categoryDatabaseHelper.categoryModelDAO().insertAll(new CategoryModel("No Category"));
            categoryDatabaseHelper.categoryModelDAO().insertAll(new CategoryModel("Work"));
            categoryDatabaseHelper.categoryModelDAO().insertAll(new CategoryModel("Personal"));
            categoryDatabaseHelper.categoryModelDAO().insertAll(new CategoryModel("Wishlist"));
            categoryDatabaseHelper.categoryModelDAO().insertAll(new CategoryModel("Birthday"));
            categoryDatabaseHelper.categoryModelDAO().insertAll(new CategoryModel("Add Category"));
        }
    }
    private void ShowAddCategoryBox() {
        AddcategorymenuboxBinding addcategorymenuboxBinding;
        addcategorymenuboxBinding = AddcategorymenuboxBinding.inflate(getLayoutInflater());
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(addcategorymenuboxBinding.getRoot());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.newcategoryboxbg);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.CategorySpinnerAnimation;
        addcategorymenuboxBinding.save.setOnClickListener(v->{
            if (addcategorymenuboxBinding.inputCategoryName.getText().toString().isEmpty()) {
                addcategorymenuboxBinding.inputCategoryName.setError("Input");

            }
            else {
                CategoryDatabaseHelper categoryDatabaseHelper = CategoryDatabaseHelper.getDB(getContext());
                categoryDatabaseHelper.categoryModelDAO().insertAll(
                        new CategoryModel(addcategorymenuboxBinding.inputCategoryName.getText().toString())
                );
                CategorySpinnerDate.add(addcategorymenuboxBinding.inputCategoryName.getText().toString());
                dialog.dismiss();
                CategorySpinnerAdapter.notifyDataSetChanged();
            }
        });
        addcategorymenuboxBinding.CANCEL.setOnClickListener(view->dialog.dismiss());
        dialog.show();
    }
    private void TaskCategorySpinner(Spinner categorySpinnerAddTask) {
        CategoryDatabaseHelper categoryDatabaseHelper = CategoryDatabaseHelper.getDB(getContext());
        CategorySpinnerDate = new ArrayList<>();
        ArrayList<CategoryModel> categoryModels = (ArrayList<CategoryModel>) categoryDatabaseHelper.categoryModelDAO().getAllCategory();
        CategorySpinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, CategorySpinnerDate);

        categorySpinnerAddTask.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getSelectedItem().toString().equals("Add Category")) {
                    ShowAddCategoryBox();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        for (int i = 0; i < categoryModels.size(); i++) {
            CategorySpinnerDate.add(categoryModels.get(i).getCategoryName());
            CategorySpinnerAdapter.notifyDataSetChanged();
        }
        CategorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinnerAddTask.setAdapter(CategorySpinnerAdapter);

    }
    @SuppressLint("NotifyDataSetChanged")
    private void GetTask(){
//        binding.TaskList.set
        TaskDatabaseHelper taskDatabaseHelper = TaskDatabaseHelper.TasGetDB(getContext());
        ArrayList<TaskModel> taskModels = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskModels, this  ,getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.TaskList.setLayoutManager(layoutManager);
        binding.TaskList.setAdapter(taskAdapter);
        taskModels.addAll(taskDatabaseHelper.taskModelDAO().getAllTaskModel());
        taskAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        GetTask();
        super.onResume();
    }

    @Override
    public void onBtnClickPin(TaskModel taskModel) {

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBtnClickDelete(TaskModel taskModel) {
        TaskDatabaseHelper taskDatabaseHelper = TaskDatabaseHelper.TasGetDB(getContext());
        taskDatabaseHelper.taskModelDAO().deleteTaskModel(taskModel);
        taskAdapter.notifyDataSetChanged();
    }
}