package com.sofforce.makenbake.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


/*
 * this class is going to be the MVC model  for the json to parse.
 * All the quantity, measures, and ingredients will go here
 * */

public class RecipeInfo implements Serializable, Parcelable {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ingredients")
    @Expose
    private List<Ingredients> ingredients = null;
    @SerializedName("steps")
    @Expose
    private List<StepsToTake> steps = null;
    @SerializedName("servings")
    @Expose
    private long servings;
    @SerializedName("image")
    @Expose
    private String image;
    public final static Parcelable.Creator<RecipeInfo> CREATOR = new Creator<RecipeInfo>() {


        @SuppressWarnings({
                "unchecked"
        })
        public RecipeInfo createFromParcel(Parcel in) {
            return new RecipeInfo(in);
        }

        public RecipeInfo[] newArray(int size) {
            return (new RecipeInfo[size]);
        }

    };

    private final static long serialVersionUID = -4496692576621736715L;

    protected RecipeInfo(Parcel in) {
        this.id = ((long) in.readValue((long.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.ingredients, (Ingredients.class.getClassLoader()));
        in.readList(this.steps, (StepsToTake.class.getClassLoader()));
        this.servings = ((long) in.readValue((long.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public RecipeInfo() {
    }

    /**
     *
     * @param ingredients
     * @param id
     * @param servings
     * @param name
     * @param image
     * @param steps
     */
    public RecipeInfo(long id,
                      String name,
                      List<Ingredients> ingredients,
                      List<StepsToTake> steps,
                      long servings,
                      String image) {
        super();
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public long getServings() {
        return servings;
    }

    public void setServings(long servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeList(ingredients);
        dest.writeList(steps);
        dest.writeValue(servings);
        dest.writeValue(image);
    }

    public int describeContents() {
        return 0;
    }





}
