package com.sofforce.makenbake.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.sofforce.makenbake.database.IngredientsConvertor;
import com.sofforce.makenbake.database.StepsConvertor;

import java.util.List;


/*
 * this class is going to be the MVC model  for the json to parse.
 * All the quantity, measures, and ingredients will go here
 * */
@Entity(tableName = "recipes")
public class RecipeInfo  {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int uid;
    private String id;
    private String name;
    private String servings;

    @ColumnInfo(name = "ingredients")
    @TypeConverters(IngredientsConvertor.class)
    private List<Ingredients> ingredients;

    @ColumnInfo(name = "steps")
    @TypeConverters(StepsConvertor.class)
    private List<StepsToTake> steps;

    @NonNull
    public int getUid() {
        return uid;
    }

    public void setUid(@NonNull int uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public List<StepsToTake> getSteps() {
        return steps;
    }

    public void setSteps(List<StepsToTake> steps) {
        this.steps = steps;
    }


    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }






}
