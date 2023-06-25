package com.crazyostudio.to_do_list.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "Task_Model")

public class TaskModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name="task")
    String task;
    @ColumnInfo(name="notes")
    String notes;
    @ColumnInfo(name="category")
    String Category;
    @ColumnInfo(name="TaskCheck")
    Boolean TaskCheck;
    @ColumnInfo(name = "sub_check")
    ArrayList<Boolean> sub_check;
    @ColumnInfo(name = "sub_Task")
    ArrayList<String>  sub_Task;

    public TaskModel(){}
    public TaskModel(int id, String task, String notes, String category, Boolean taskCheck, ArrayList<String> sub_Task, ArrayList<Boolean>  sub_check) {
        this.id = id;
        this.task = task;
        this.notes = notes;
        Category = category;
        TaskCheck = taskCheck;
        this.sub_Task = sub_Task;
        this.sub_check = sub_check;
    }
    @Ignore
    public TaskModel(String task, String notes, String category, Boolean taskCheck, ArrayList<String> sub_Task, ArrayList<Boolean>  sub_check) {
        this.task = task;
        this.notes = notes;
        Category = category;
        TaskCheck = taskCheck;
        this.sub_Task = sub_Task;
        this.sub_check = sub_check;
    }
    public ArrayList<String> getSub_Task() {
        return sub_Task;
    }
    public void setSub_Task(ArrayList<String> sub_Task) {
        this.sub_Task = sub_Task;
    }

    public ArrayList<Boolean> getSub_check() {
        return sub_check;
    }

    public void setSub_check(ArrayList<Boolean> sub_check) {
        this.sub_check = sub_check;
    }

    public Boolean getTaskCheck() {
        return TaskCheck;
    }
    public void setTaskCheck(Boolean taskCheck) {
        TaskCheck = taskCheck;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTask() {
        return task;
    }
    public void setTask(String task) {
        this.task = task;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public String getCategory() {
        return Category;
    }
    public void setCategory(String category) {
        Category = category;
    }
}