package com.sofforce.makenbake.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.sofforce.makenbake.Models.RecipeInfo;

import java.util.List;


/**
 * in this class you are going to access the recipes in arrays that are going to be passed to the
 * homescreen and is going to be clickable
 */


public class RecipesArrayList extends AndroidViewModel {


    private final LiveData<List<RecipeInfo>> recipesLiveData;

    public RecipesArrayList( Application application) {
        super( application );
        recipesLiveData = ApplicationDb.getInstance( application ).recipeExtract().getAll();
    }


    public LiveData<List<RecipeInfo>> getRecipesLiveData() {
        return recipesLiveData;
    }
}
