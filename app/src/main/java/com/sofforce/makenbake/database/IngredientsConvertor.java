package com.sofforce.makenbake.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sofforce.makenbake.Models.Ingredients;

import java.lang.reflect.Type;
import java.util.List;

public class IngredientsConvertor {

    @TypeConverter
    public static String encode(List<Ingredients> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<Ingredients> decode(String value) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Ingredients>>() {
        }.getType();
        return gson.fromJson(value, listType);
    }
}
