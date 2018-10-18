package com.sofforce.makenbake.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.sofforce.makenbake.Models.RecipeInfo;


@Database( entities = {RecipeInfo.class}, version = 1, exportSchema = false)
public abstract class ApplicationDb extends RoomDatabase {


    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "recipesdb";
    private static ApplicationDb instance;

    public static ApplicationDb getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                instance = Room.databaseBuilder(context.getApplicationContext()
                        , ApplicationDb.class, ApplicationDb.DATABASE_NAME)
                        .allowMainThreadQueries().build();
            }
        }
        return instance;
    }

    public abstract RecipeExtract recipeExtract();

}
