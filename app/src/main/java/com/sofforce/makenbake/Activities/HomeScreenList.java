package com.sofforce.makenbake.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.sofforce.makenbake.Adapters.MainRecipeAdapter;
import com.sofforce.makenbake.All_interfaces.ClientBase;
import com.sofforce.makenbake.All_interfaces.Service_Api;
import com.sofforce.makenbake.ConnectionDetector;
import com.sofforce.makenbake.Models.RecipeInfo;
import com.sofforce.makenbake.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



/*
*
* this is my new baking app
* */

public class HomeScreenList extends AppCompatActivity {


    @BindView(R.id.main_homeScreen )
    RecyclerView recipeRecycler;


    private MainRecipeAdapter adapter;
    private List<RecipeInfo> recipeList;

    //this is the log variable for the activity lifecycle
    private static final String ACTIVITY = "ACTIVITY_LIFECYCLE";

    //this is to check whether there is  a connection to the internet
    ConnectionDetector cd =  new ConnectionDetector(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d( ACTIVITY,  "onCreate: in" );

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home_screen );
        ButterKnife.bind( this );

        //intializing the recyclerview and the id that goes with it
        recipeRecycler.setHasFixedSize( true );
        recipeRecycler.setLayoutManager( new LinearLayoutManager( this ) );

        //intializing the recipelist
        recipeList = new ArrayList<>(  );

        //intializing the adapter and the setting it to the recipeRecycler
        adapter = new MainRecipeAdapter( this, recipeList );
       //recipeRecycler.setAdapter( adapter );




        //the check for the internet connection
        if (cd.isConnected()) {
            Toast.makeText(HomeScreenList.this, "Pick a recipe of your choice", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(HomeScreenList.this, "You are not connected", Toast.LENGTH_SHORT).show();

        }

        loadGson();



        Log.d( ACTIVITY,  "onCreate: out" );

    }




    private void loadGson() {

        Service_Api service_api = ClientBase.getService();
        Call<JsonArray> loadRecipeCall = service_api.getRecipeList();

        loadRecipeCall.enqueue(new Callback<JsonArray>() {

            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                try{

                    String recipeString =  response.body().toString();

                    Type listOfRecipes = new TypeToken<List<RecipeInfo>>() {}.getType();
                    recipeList = getRecipeListFromJson( recipeString, listOfRecipes );

                    recipeRecycler.setItemAnimator( new DefaultItemAnimator() );
                    recipeRecycler.setAdapter( adapter );


                } catch (Exception e ) {
                    Log.d( "OnResponse", "there is an error" );
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

                Toast.makeText( HomeScreenList.this, "ERROR Ocurred", Toast.LENGTH_SHORT ).show();

            }
        } );

    }

    public static <T> List<T> getRecipeListFromJson(String jsonString, Type type) {
        if (!isValid(jsonString)){
            return null;
        }
        return new Gson().fromJson( jsonString, type );
    }

    public static boolean isValid(String json) {

        try{
            new JsonParser().parse(json);
            return true;
        }
        catch (JsonSyntaxException jse) {
            return false;
        }
    }





}
