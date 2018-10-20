package com.sofforce.makenbake.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.sofforce.makenbake.Adapters.StepsAdapter;
import com.sofforce.makenbake.All_interfaces.ActivityClickListener;
import com.sofforce.makenbake.All_interfaces.OnFragmentListener;
import com.sofforce.makenbake.Fragments.DetailedVideoFragment;
import com.sofforce.makenbake.Models.RecipeInfo;
import com.sofforce.makenbake.R;
import com.sofforce.makenbake.Utilities.MyInstanceLifetime;

import butterknife.BindView;
import butterknife.ButterKnife;


/*
* In this activity will show the recipe ingredients and steps to take it will also show the
* exoplayer when the user taps on the step.
* */

public class IngredientsAndSteps extends AppCompatActivity implements ActivityClickListener, OnFragmentListener {


    final static String  ACTIVITY = "ACTIVITY";
    private boolean isInTwoPane;
    private Context context;
    public RecipeInfo recipeInfoClass;

    @BindView( R.id.item_list )
    RecyclerView recyclerView;

    @BindView( R.id.toolbar )
    Toolbar toolbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d( ACTIVITY,  "onCreate: in" );

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_ingredients_list );
        ButterKnife.bind( this );

        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        context = this;
        recipeInfoClass = MyInstanceLifetime.getAppInstance().getRecipeInfo();
//
//        if (findViewById(R.id.item_detail_container ) != null) {
//            isInTwoPane = true;
//        }

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public void onFragmentRecreate(long seekTo, boolean isPlaying) {
        Bundle bundle = new Bundle();
        bundle.putLong("seekTo", seekTo);
        bundle.putBoolean("isPlaying", isPlaying);
        MyInstanceLifetime.getAppInstance().setBundle(bundle);
    }

    @Override
    public void onItemClick(int pos, ImageView imageView) {

        if (isInTwoPane) {

            //MyInstanceLifetime.getAppInstance().setBundle(null);

            Bundle bundle = new Bundle();
            bundle.putInt("video_pos", pos);
            DetailedVideoFragment fragment = new DetailedVideoFragment();
            fragment.setArguments(bundle);
            ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(context, VideoAndInstructions.class);
            intent.putExtra("video_pos", pos);
            intent.putExtra("recipe", recipeInfoClass.getName());
            startActivity( intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }



}





