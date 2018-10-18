package com.sofforce.makenbake.Utilities;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sofforce.makenbake.Models.Ingredients;
import com.sofforce.makenbake.Models.RecipeInfo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MyInstanceLifetime extends Application {


    private static MyInstanceLifetime mInstance;
    private RequestQueue mRequestQueue;
    private RecipeInfo recipeInfo;
    private Bundle bundle = new Bundle();

    public static MyInstanceLifetime getAppInstance(){
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = (MyInstanceLifetime) getApplicationContext();
    }

    private synchronized RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mInstance, new HurlStack());
        }
        return mRequestQueue;
    }

    public synchronized <T> void addToRequestQueue(Request<T> req) {
        req.setTag("data");
        req.setShouldCache(false);
        req.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(req);
    }

    public RecipeInfo getRecipeInfo() {
        return recipeInfo;
    }

    public void setRecipesModel(RecipeInfo recipeInfo) {
        this.recipeInfo = recipeInfo;
    }

    public void saveIngredients(Context context, List<Ingredients> list) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Ingredients>>() {
        }.getType();
        String res = gson.toJson(list, listType);
        SharedPreferences.Editor prefs = context.getSharedPreferences("MakeNBake", 0).edit();
        prefs.putString("Ingredients", res);
        prefs.commit();
    }

    public void saveRecipeName(Context context, String name) {
        SharedPreferences prefs = context.getSharedPreferences("MakeNBake", 0);
        prefs.edit().putString("Recipe Name", name).commit();
    }

    public static String getRecipeName(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("makenBake", 0);
        return prefs.getString("Recipe Name", "not available");
    }

    public List<Ingredients> getIngredients(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("makenBake", 0);
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Ingredients>>() {
        }.getType();

        String res = prefs.getString("Ingredients", "not available");
        if (res.equals("not available"))
            return new ArrayList<>();
        else
            return gson.fromJson(prefs.getString("Ingredients", "not available"), listType);
    }



    public Bundle getBundle() {

        return bundle;
    }



    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }









}
