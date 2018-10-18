package com.sofforce.makenbake.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sofforce.makenbake.Models.StepsToTake;

import java.lang.reflect.Type;
import java.util.List;

public class StepsConvertor {

    @TypeConverter
    public static String encode(List<StepsToTake> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<StepsToTake> decode(String value) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<StepsToTake>>() {
        }.getType();
        return gson.fromJson(value, listType);
    }
}
