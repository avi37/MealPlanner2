package com.example.admin.mealplanner2new.Models;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface WordDao {

    @Insert
    void insert(Exercise word);

    @Query("Select *  FROM exercise")
    List<Exercise> getAllData();


}