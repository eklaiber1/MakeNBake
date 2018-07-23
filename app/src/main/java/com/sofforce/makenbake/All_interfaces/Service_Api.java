package com.sofforce.makenbake.All_interfaces;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service_Api {


    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<JsonArray> getRecipeList();

}
