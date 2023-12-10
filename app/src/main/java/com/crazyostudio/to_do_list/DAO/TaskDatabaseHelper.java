package com.crazyostudio.to_do_list.DAO;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.crazyostudio.to_do_list.Converters.ArrayListConverters;
import com.crazyostudio.to_do_list.Model.TaskModel;

@Database(entities = TaskModel.class,exportSchema = false ,version = 1)
@TypeConverters(ArrayListConverters.class)

public abstract class TaskDatabaseHelper extends RoomDatabase {

    private static final String Task_DB_NAME = "TaskDb";
    private static TaskDatabaseHelper TaskInstance;

    public static synchronized TaskDatabaseHelper TasGetDB(Context context) {
        if (TaskInstance==null)
        {
            TaskInstance = Room.databaseBuilder(context,TaskDatabaseHelper.class,Task_DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return TaskInstance;
    }

    public abstract TaskModelDAO taskModelDAO();


}