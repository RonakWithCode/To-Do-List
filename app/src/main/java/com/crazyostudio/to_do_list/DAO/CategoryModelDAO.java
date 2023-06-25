package com.crazyostudio.to_do_list.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.crazyostudio.to_do_list.Model.CategoryModel;

import java.util.List;

@Dao
public interface CategoryModelDAO {

    @Query("select * from category")
    List<CategoryModel> getAllCategory();

    @Insert
    void insertAll(CategoryModel users);

    @Update
    void UpdateAll(CategoryModel users);

    @Delete
    void delete(CategoryModel user);
}
