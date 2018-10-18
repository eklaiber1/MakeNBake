package com.sofforce.makenbake.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.sofforce.makenbake.Models.RecipeInfo;

import java.util.List;


@Dao
public interface RecipeExtract {

        @Query("SELECT * FROM recipes")
        LiveData<List<RecipeInfo>> getAll();

        @Insert
        void insertAll(List<RecipeInfo> movieDataList);

        @Query("DELETE FROM recipes")
        void deleteAll();
}
