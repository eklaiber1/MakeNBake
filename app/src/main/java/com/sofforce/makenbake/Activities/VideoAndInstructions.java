package com.sofforce.makenbake.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.sofforce.makenbake.All_interfaces.OnFragmentListener;
import com.sofforce.makenbake.Fragments.DetailedVideoFragment;
import com.sofforce.makenbake.R;
import com.sofforce.makenbake.Utilities.ConstantsForApp;
import com.sofforce.makenbake.Utilities.MyInstanceLifetime;

public class VideoAndInstructions extends AppCompatActivity implements OnFragmentListener {

    //strings for the log.d
    final static String  ACTIVITY = "ACTIVITY";
    final static String  ON_FRAGMENT_RECREATED = "ON_FRAGMENT_CREATED";
    final static String  ON_RESUME = "ON_RESUME";
    final static String  ON_PAUSED = "ON_PAUSED";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d( ACTIVITY,  "onCreate: in" );

        super.onCreate(savedInstanceState);
        setContentView(R.layout.videos_and_instructions);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putInt(ConstantsForApp.STEPS_ARG,
                    getIntent().getIntExtra(ConstantsForApp.STEPS_ARG, -1));

            setTitle(getIntent().getStringExtra(ConstantsForApp.RECIPE_NAME_ARG));

            DetailedVideoFragment fragment = new DetailedVideoFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }

        Log.d( ACTIVITY,  "onCreate: out" );

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

        Log.d( ON_FRAGMENT_RECREATED,  " out" );

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


}
