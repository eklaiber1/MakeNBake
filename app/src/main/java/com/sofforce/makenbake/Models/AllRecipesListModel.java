package com.sofforce.makenbake.Models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.sofforce.makenbake.database.ApplicationDb;

import java.util.List;

public class AllRecipesListModel extends AndroidViewModel {

    private final LiveData<List<RecipeInfo>> listLiveData;

    public AllRecipesListModel(Application application) {
        super(application);
        listLiveData = ApplicationDb.getInstance(application).recipeExtract().getAll();
    }

    public LiveData<List<RecipeInfo>> getListLiveData() {

        return listLiveData;
    }
}
