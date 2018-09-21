package com.example.admin.mealplanner2new.Models;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface WordDao {

    @Insert
    void insert(Exercise word);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCategory(CategoryMaster categoryMaster);

    @Query("Select *  FROM exercise")
    List<Exercise> getAllData();

    @Query("select * from cat_master where :cat_id=cat_id")
    CategoryMaster getCategory(String cat_id);


}