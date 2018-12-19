package com.sofforce.makenbake.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.sofforce.makenbake.Adapters.StepsAdapter;
import com.sofforce.makenbake.All_interfaces.ActivityClickListener;
import com.sofforce.makenbake.All_interfaces.OnFragmentListener;
import com.sofforce.makenbake.Fragments.DetailedVideoFragment;
import com.sofforce.makenbake.Models.RecipeInfo;
import com.sofforce.makenbake.R;
import com.sofforce.makenbake.Utilities.ConstantsForApp;
import com.sofforce.makenbake.Utilities.MyInstanceLifetime;

import butterknife.BindView;
import butterknife.ButterKnife;


/*
* In this activity will show the recipe ingredients and steps to take it will also show the
* exoplayer when the user taps on the step.
* */

public class IngredientsAndSteps extends AppCompatActivity implements ActivityClickListener, OnFragmentListener {

    //strings for the log.d
    final static String  ACTIVITY = "ACTIVITY";
    final static String  ON_OPTIONS_SELECTED = "ON_OPTIONS_SELECTED";
    final static String  ON_FRAGMENT_RECREATED = "ON_FRAGMENT_CREATED";
    final static String  ON_ITEM_CLICKED = "ON_ITEM_CLICKED";
    final static String  ON_RESUME = "ON_RESUME";
    final static String  ON_PAUSED = "ON_PAUSED";



    private boolean isInTwoPane;
    private Context context;
    public RecipeInfo recipeInfoClass;

    @BindView( R.id.item_list )
    RecyclerView recyclerView;

    @BindView( R.id.toolbar )
    Toolbar toolbar;



    /*
    * this is the oncreate method that will create the views when this activity is launched
    * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d( ACTIVITY,  "onCreate: in" );

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_ingredients_list );
        ButterKnife.bind( this );

        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        if (findViewById(R.id.item_detail_container) != null) {
            isInTwoPane = true;
        }

        context = this;
        recipeInfoClass = MyInstanceLifetime.getAppInstance().getRecipeInfo();

        if (null != recipeInfoClass) {
            setTitle(recipeInfoClass.getName());
            final StepsAdapter adapter = new StepsAdapter( recipeInfoClass.getSteps(), context, recipeInfoClass.getIngredients(), isInTwoPane);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    recyclerView.setAdapter(adapter);
                }
            }, 200);
        } else
            Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show();


        Log.d( ACTIVITY,  "onCreate: out" );

    }

    /*
     * this method will take you back to the home screen when tapped
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d( ON_OPTIONS_SELECTED,  "onOptionsItemSelected: in" );

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        Log.d( ON_OPTIONS_SELECTED,  "onOptionsItemSelected: out" );

        return true;

    }

    /*
     *this is from the interface class OnFragmentListener. it takes the instance of the app and
     * passes to the bundle
     * */
    @Override
    public void onFragmentRecreate(long seekTo, boolean isPlaying) {
        Log.d( ON_FRAGMENT_RECREATED,  " in" );

        Bundle bundle = new Bundle();
        bundle.putLong(ConstantsForApp.VIDEO_SEEK_TO, seekTo);
        bundle.putBoolean(ConstantsForApp.VIDEO_IS_PLAYING, isPlaying);
        MyInstanceLifetime.getAppInstance().setBundle(bundle);

        Log.d( ON_FRAGMENT_RECREATED,  " in" );

    }

    /*
     *this method comes from the ActivityClickListener interface and it passes the info
     * to the detailedVideoFragment where it loads up the video so that it will be played
     * */
    @Override
    public void onItemClick(int pos, ImageView imageView) {
        Log.d( ON_ITEM_CLICKED,  " in" );


        if (isInTwoPane) {

            //MyInstanceLifetime.getAppInstance().setBundle(null);

            Bundle bundle = new Bundle();
            bundle.putInt(ConstantsForApp.STEPS_ARG, pos);
            DetailedVideoFragment fragment = new DetailedVideoFragment();
            fragment.setArguments(bundle);
            ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(context, VideoAndInstructions.class);
            intent.putExtra(ConstantsForApp.STEPS_ARG, pos);
            intent.putExtra(ConstantsForApp.RECIPE_NAME_ARG, recipeInfoClass.getName());
            startActivity( intent);
        }
        Log.d( ON_ITEM_CLICKED,  " out" );

    }



    /*
     *this is part of the activity life cycle for when the device switches orientation
     * */
    @Override
    protected void onResume() {
        Log.d( ON_RESUME,  " in" );

        super.onResume();

        Log.d( ON_RESUME,  " out" );

    }


    /*
     * this is part of the activity life cycle for when the device is in the background
     * */
    @Override
    protected void onPause() {
        Log.d( ON_PAUSED,  " in" );

        super.onPause();
        Log.d( ON_PAUSED,  " out" );

    }


    /*
    * this is for the sharedPreference button that is going to update the widget
    */
    public void passIngreToWidget(View view) {

        SharedPreferences shPrefer = getSharedPreferences( "widgetData", Context.MODE_PRIVATE );

        SharedPreferences.Editor editor =  shPrefer.edit();
    }



}





