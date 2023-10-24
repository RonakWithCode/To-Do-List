package com.crazyostudio.to_do_list.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.crazyostudio.to_do_list.Model.TaskModel;

import java.util.List;

@Dao
public interface TaskModelDAO
{
    @Query("select * from Task_Model")
    List<TaskModel> getAllTaskModel();

    @Query("select * from Task_Model where id= :id")
    TaskModel getAllTaskModelById(int id);

    //    User getUserById(Long id);
    @Insert
    void insertTaskModel(TaskModel users);
    @Update
    void UpdateTaskModel(TaskModel users);

    @Delete
    void deleteTaskModel(TaskModel user);

}
