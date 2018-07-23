package com.sofforce.makenbake.All_interfaces;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientBase {


    /*
    * this is the class that will call the base URL of the API and
    * set it up to the appended request to be parsed
    *
    * */



    final static String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";
    private static Service_Api service;

    public static Service_Api getService() {


        if (service == null) {

            Gson gson = new GsonBuilder()
                    .create();

            //this is the retofit Interface call to get the recipe info
            Retrofit builder = new Retrofit.Builder()
                    .baseUrl( BASE_URL )
                    .addConverterFactory( GsonConverterFactory.create(gson) )
                    .build();


            service = builder.create( Service_Api.class );
        }
            return service;


    }
}
