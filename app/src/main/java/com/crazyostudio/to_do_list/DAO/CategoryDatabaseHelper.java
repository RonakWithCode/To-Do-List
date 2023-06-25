package com.crazyostudio.to_do_list.DAO;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.crazyostudio.to_do_list.Model.CategoryModel;

@Database(entities = CategoryModel.class,exportSchema = false ,version = 1)

public abstract class CategoryDatabaseHelper extends RoomDatabase {
    private static final String DB_NAME = "categorydb";
    private static CategoryDatabaseHelper instance;
    public static synchronized CategoryDatabaseHelper getDB(Context context){
        if (instance==null)
        {
            instance = Room.databaseBuilder(context,CategoryDatabaseHelper.class,DB_NAME)
                    .fallbackToDestructiveMigration().allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract CategoryModelDAO categoryModelDAO();


}
