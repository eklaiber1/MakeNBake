package com.sofforce.makenbake.Fragments;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.sofforce.makenbake.All_interfaces.OnFragmentListener;
import com.sofforce.makenbake.Models.StepsToTake;
import com.sofforce.makenbake.R;
import com.sofforce.makenbake.Utilities.MyInstanceLifetime;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetailedVideoFragment extends Fragment {

    private StepsToTake stepsModel;
    private SimpleExoPlayer simpleExoPlayer;
    Unbinder unbinder;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_desc)
    TextView txtDesc;
    @BindView(R.id.cardView)
    CardView cardView;
    @BindView(R.id.player)
    SimpleExoPlayerView mPlayerView;
    @BindView(R.id.txt_no_video)
    TextView txt_no_video;
    @BindView(R.id.img_sad_chef)
    ImageView img_sad_chef;


    private long seekTo = 0;
    private boolean isPlaying = false;
    private OnFragmentListener onFragmentListener;
    private MyInstanceLifetime myInstanceLifetime;

    public DetailedVideoFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onFragmentListener = (OnFragmentListener) context;
        myInstanceLifetime = MyInstanceLifetime.getAppInstance();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int pos = getArguments().getInt("video_pos", -1);
        if (pos != -1) {
            stepsModel = myInstanceLifetime.getAppInstance().getRecipeInfo().getSteps().get(pos);
        }
        if (myInstanceLifetime.getBundle() != null) {
            seekTo = myInstanceLifetime.getBundle().getLong("seekTo", 0);
            isPlaying = myInstanceLifetime.getBundle().getBoolean("isPlaying", false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.video_fragment, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        txtTitle.setText(stepsModel.getShortDescription());
        txtDesc.setText(stepsModel.getDescription());
        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.videocamera01));

        if (stepsModel.getThumbnailURL().equals("NA")
                &&stepsModel.getVideoURL().equals("NA")) {
            img_sad_chef.setVisibility(View.VISIBLE);
            txt_no_video.setVisibility(View.VISIBLE);
        }
        return rootView;
    }

    private void initializePlayer(Uri uri) {
        if (simpleExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(simpleExoPlayer);

            String userAgent = Util.getUserAgent(getContext(), getContext().getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(getContext(), userAgent)
                    , new DefaultExtractorsFactory(), null, null);
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(isPlaying);
            simpleExoPlayer.seekTo(seekTo);
        }
    }

    private void saveCurrentVideoState() {
        seekTo = simpleExoPlayer.getContentPosition();
        isPlaying = simpleExoPlayer.getPlayWhenReady();
        onFragmentListener.onFragmentRecreate(seekTo, isPlaying);
    }

    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    private void pausePlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.setPlayWhenReady(false);
            simpleExoPlayer.getPlaybackState();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > Build.VERSION_CODES.M) {
            initializePlayer(Uri.parse(stepsModel.getVideoURL()));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= Build.VERSION_CODES.M || simpleExoPlayer == null)) {
            initializePlayer(Uri.parse(stepsModel.getVideoURL()));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        saveCurrentVideoState();
        pausePlayer();
        if (Util.SDK_INT <= Build.VERSION_CODES.M) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > Build.VERSION_CODES.M) {
            releasePlayer();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }




}
