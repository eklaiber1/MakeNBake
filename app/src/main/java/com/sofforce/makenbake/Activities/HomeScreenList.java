package com.sofforce.makenbake.Activities;

import android.app.ProgressDialog;
import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sofforce.makenbake.Adapters.MainRecipeAdapter;
import com.sofforce.makenbake.All_interfaces.ActivityClickListener;
import com.sofforce.makenbake.BuildConfig;
import com.sofforce.makenbake.Models.AllRecipesListModel;
import com.sofforce.makenbake.Models.RecipeInfo;
import com.sofforce.makenbake.R;
import com.sofforce.makenbake.Utilities.ConnectionDetector;
import com.sofforce.makenbake.Utilities.MyInstanceLifetime;
import com.sofforce.makenbake.database.ApplicationDb;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



/*
*
* this is launching activity for the application
* */

public class HomeScreenList extends AppCompatActivity implements ActivityClickListener {

    //these are all the global variables for this class
    @BindView(R.id.items_in_recycler )
    RecyclerView recipeRecycler;
    @BindView( R.id.textView )
    TextView textView;

    private Context mContext;
    private ProgressDialog progressDialog;
    private MyInstanceLifetime myInstanceLifetime;
    private ApplicationDb applicationDb;
    private int mAppWidgetId;
    private boolean hasContext;

    //initializing the recipelist
    private List<RecipeInfo> recipeList;

    //this is the log variable for the activity lifecycle
    private static final String ACTIVITY_ON_CREATE = "ACTIVITY_OnCreate";
    private static final String ACTIVITY_LOADGSON = "ACTIVITY_loadGson";
    private static final String ACTIVITY_SHOWLIVELIST = "ACTIVITY_showLiveList";
    private static final String ACTIVITY_ACTIVATELIST = "ACTIVITY_activateList";
    private static final String ACTIVITY_ONCHANGED = "ACTIVITY_onChanged";
    private static final String ACTIVITY_ONITEMCLICK = "ACTIVITY_onItemClicked";

    //this is for the shared Preferences that will be passed to the baking widget
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor  mEditor;


    //this is to check whether there is  a connection to the internet
    ConnectionDetector cd =  new ConnectionDetector(this);


    //the onCreate method is executed when the activity is launched,
    // everything in this method gets created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d( ACTIVITY_ON_CREATE,  "onCreate: in" );

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main_homescreen);
        ButterKnife.bind( this );
        mContext = this;
        myInstanceLifetime = MyInstanceLifetime.getAppInstance();
        applicationDb = ApplicationDb.getInstance( mContext );

        mPreferences = PreferenceManager.getDefaultSharedPreferences( this );
        mEditor = mPreferences.edit();

        textView.setText( R.string.pick_a_choice );


        //the check for the internet connection
        if (cd.isConnected()) {
            Toast.makeText(HomeScreenList.this, "Welcome to MakeNBake", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(HomeScreenList.this, "You are not connected", Toast.LENGTH_SHORT).show();

        }

        if (getIntent() != null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                mAppWidgetId = extras.getInt(
                        AppWidgetManager.EXTRA_APPWIDGET_ID,
                        AppWidgetManager.INVALID_APPWIDGET_ID );
            }
        }



        int widthRatio = 2;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            widthRatio = 3;
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, widthRatio);
        recipeRecycler.setLayoutManager(gridLayoutManager);



        showLiveList();
        loadGson();


        Log.d( ACTIVITY_ON_CREATE,  "onCreate: out" );

    }


    //this is where  you retrieve the data from the URL and save it in the application instance
    private void loadGson() {
        Log.d( ACTIVITY_LOADGSON,  "loadGson: in" );


        StringRequest stingRequest = new StringRequest( Request.Method.GET, BuildConfig.BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONArray recipeData =  new JSONArray( response );
                    applicationDb.recipeExtract().deleteAll();
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<RecipeInfo>>() {}.getType();
                    recipeList =  gson.fromJson( recipeData.toString(), listType );
                    applicationDb.recipeExtract().insertAll(recipeList);
                    activateList();


                } catch (JSONException e ) {
                    progressDialog.dismiss();
                    Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        } );

        myInstanceLifetime.addToRequestQueue( stingRequest );
        Log.d( ACTIVITY_LOADGSON,  "loadGson: out" );

    }


    //this is where you pass the data to the recylcerview and show the list in the view
    private void showLiveList() {
        Log.d( ACTIVITY_SHOWLIVELIST,  "showLiveList: in" );

        AllRecipesListModel model = ViewModelProviders.of((FragmentActivity) mContext).get(AllRecipesListModel.class);
        model.getListLiveData().observe(HomeScreenList.this, observer);

        Log.d( ACTIVITY_SHOWLIVELIST,  "showLiveList: out" );
    }


    //all the content that was retrieved in the loadGson method is
    // passed to this method so that the adapter can parse it
    private void activateList() {

        Log.d( ACTIVITY_ACTIVATELIST,  "activate_List: in" );

        if (recipeList.size() != 0) {
            MainRecipeAdapter adapter = new MainRecipeAdapter(mContext, recipeList);
            recipeRecycler.setAdapter(adapter);
        }
        Log.d( ACTIVITY_ACTIVATELIST,  "activate_List: out" );

    }


    //this method passes the entire list to the array that them passed it to the activity list
    final Observer<List<RecipeInfo>> observer = new Observer<List<RecipeInfo>>() {

        @Override
        public void onChanged(@Nullable List<RecipeInfo> list) {
            Log.d( ACTIVITY_ONCHANGED,  "activate_onChanged: in" );

            if (list.size()>0) {
                recipeList = new ArrayList<>();
                recipeList.addAll(list);
                activateList();
            }
            Log.d( ACTIVITY_ONCHANGED,  "activate_onChanged: out" );

        }
    };

    //this method is activated whenever the user clicks on the recipe card it takes them to the
    // next activity which shows all the details for the recipe that was clicked
    @Override
    public void onItemClick(int pos, ImageView imageView) {
        Log.d( ACTIVITY_ONITEMCLICK,  "activate_onItemClick: in" );

            myInstanceLifetime.setRecipesModel( recipeList.get( pos ) );
            Intent intent = new Intent( mContext, IngredientsAndSteps.class );
            startActivity( intent );

            if (intent.getExtras() != null) {
                myInstanceLifetime.saveRecipeName( mContext, recipeList.get( pos ).getName() );
                myInstanceLifetime.saveIngredients( mContext, recipeList.get( pos ).getIngredients() );
//                Intent otherIntent = new Intent( mContext, bakingWidget.class );
//                intent.setAction( ConstantsForApp.ACTION_DATA_UPDATE );
//                intent.putExtra( AppWidgetManager.EXTRA_APPWIDGET_ID,
//                        mAppWidgetId );
//                setResult( RESULT_OK, otherIntent );
//                sendBroadcast( otherIntent );
//                ((AppCompatActivity) mContext).finish();

                String recName = recipeList.get( pos ).getName();
                List recIngre = recipeList.get( pos ).getIngredients();
                mEditor.putString( "widRecName", recName );
                mEditor.putString( "widRecIngre", recIngre.toString() );
                mEditor.apply();
            }
        Log.d( ACTIVITY_ONITEMCLICK,  "activate_onItemClick: out" );

    }





}
