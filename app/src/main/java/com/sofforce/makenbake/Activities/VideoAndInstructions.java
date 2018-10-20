package com.sofforce.makenbake.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.sofforce.makenbake.All_interfaces.OnFragmentListener;
import com.sofforce.makenbake.Fragments.DetailedVideoFragment;
import com.sofforce.makenbake.R;
import com.sofforce.makenbake.Utilities.MyInstanceLifetime;

public class VideoAndInstructions extends AppCompatActivity implements OnFragmentListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videos_and_instructions);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putInt("video_pos",
                    getIntent().getIntExtra("video_pos", -1));

            setTitle(getIntent().getStringExtra("recipe"));

            DetailedVideoFragment fragment = new DetailedVideoFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frameLayout, fragment)
                    .commit();
        }
    }





    @Override
    public void onFragmentRecreate(long seekTo, boolean isPlaying) {
        Bundle bundle = new Bundle();
        bundle.putLong("seek_to", seekTo);
        bundle.putBoolean("video_is_playing", isPlaying);
        MyInstanceLifetime.getAppInstance().setBundle(bundle);
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
