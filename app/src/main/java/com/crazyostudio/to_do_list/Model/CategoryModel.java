package com.crazyostudio.to_do_list.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "category")
public class CategoryModel {
    @PrimaryKey (autoGenerate = true)
    private int id;
    @ColumnInfo(name="categoryName")
    private String categoryName;


    public CategoryModel(int id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }
    @Ignore
    public CategoryModel(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
