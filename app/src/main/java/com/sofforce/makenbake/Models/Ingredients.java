package com.sofforce.makenbake.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Ingredients implements Serializable, Parcelable {

    @SerializedName("quantity")
    @Expose
    private long quantity;
    @SerializedName("measure")
    @Expose
    private String measure;
    @SerializedName("ingredient")
    @Expose
    private String ingredient;
    public final static Parcelable.Creator<Ingredients> CREATOR = new Creator<Ingredients>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Ingredients createFromParcel(Parcel in) {
            return new Ingredients( in );
        }

        public Ingredients[] newArray(int size) {
            return (new Ingredients[size]);
        }

    };

    private final static long serialVersionUID = -2779476539551413285L;

    protected Ingredients(Parcel in) {
        this.quantity = ((long) in.readValue((long.class.getClassLoader())));
        this.measure = ((String) in.readValue((String.class.getClassLoader())));
        this.ingredient = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public Ingredients() {
    }

    /**
     *
     * @param measure
     * @param ingredient
     * @param quantity
     */
    public Ingredients(long quantity,
                       String measure,
                       String ingredient) {
        super();
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(quantity);
        dest.writeValue(measure);
        dest.writeValue(ingredient);
    }

    public int describeContents() {
        return 0;
    }






}
