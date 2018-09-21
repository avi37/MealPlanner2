package com.example.admin.mealplanner2new.Models;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Exercise.class,CategoryMaster.class}, version = 1,exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {

   public abstract WordDao wordDao();

   private static volatile WordRoomDatabase INSTANCE;

   public static WordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WordRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class, "exercise_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}