package com.sofforce.makenbake.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sofforce.makenbake.Adapters.StepsAdapter;
import com.sofforce.makenbake.Models.StepsToTake;
import com.sofforce.makenbake.R;

import java.util.ArrayList;
import java.util.List;


public class StepsList extends AppCompatActivity {

    private RecyclerView stepsRecycler;
    private RecyclerView.Adapter adapter;
    private List<StepsToTake> stepsList01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_steps_list );


        stepsRecycler = (RecyclerView) findViewById( R.id.steps_recycler );
        stepsRecycler.setHasFixedSize( true );
        stepsRecycler.setLayoutManager( new LinearLayoutManager( this ) );

        stepsList01 = new ArrayList<>(  );

        adapter =  new StepsAdapter(stepsList01, this  );

        stepsRecycler.setAdapter( adapter );







    }










}
