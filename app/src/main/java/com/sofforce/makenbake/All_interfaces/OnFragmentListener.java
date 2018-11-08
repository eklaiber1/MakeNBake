package com.sofforce.makenbake.All_interfaces;


/*
* this reusable interface is going to be used to pass the a bundle of
* information to the video to play at the right position
*/
public interface OnFragmentListener {

    void onFragmentRecreate(long seekTo, boolean isPlaying);

}
