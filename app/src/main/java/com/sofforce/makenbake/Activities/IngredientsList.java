package com.sofforce.makenbake.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sofforce.makenbake.Adapters.IngredientAdapter;
import com.sofforce.makenbake.Models.Ingredients;
import com.sofforce.makenbake.R;

import java.util.ArrayList;
import java.util.List;


public class IngredientsList extends AppCompatActivity {

    private RecyclerView ingredientRecycler;
    private RecyclerView.Adapter adapter;
    private List<Ingredients> ingredientList01;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_ingredients_list );

        ingredientRecycler = (RecyclerView) findViewById( R.id.ingredient_recycler );
        ingredientRecycler.setHasFixedSize( true );
        ingredientRecycler.setLayoutManager( new LinearLayoutManager( this ) );

        ingredientList01 = new ArrayList<>(  );

        adapter =  new IngredientAdapter(ingredientList01, this  );

        ingredientRecycler.setAdapter( adapter );



    }
}
