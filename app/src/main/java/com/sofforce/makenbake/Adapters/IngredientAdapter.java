package com.sofforce.makenbake.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sofforce.makenbake.Models.Ingredients;
import com.sofforce.makenbake.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/*
* this class assembles the data and put it in a recyclviewer and displays it to
* the screen
* */
public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    //strings for the log.d files
    final static String ON_CREATE_VIEWHOLDER = "onCreateViewHolder";
    final static String ON_BIND_VIEWHOLDER =  "onBindViewHolder";
    final static String GET_ITEM_COUNT = "getItemCount";

    private List<Ingredients> ingredientsList;
    private Context context;


    /*
     *this is the constructor for the class
     * */
    public IngredientAdapter(Context context, List<Ingredients> ingredientsList) {
        this.context = context;
        this.ingredientsList = ingredientsList;
    }


    /*
     *this method inflates the layout to the current activity
     * */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.d( ON_CREATE_VIEWHOLDER,  "in" );


        View v = LayoutInflater.from( parent.getContext() )
                .inflate( R.layout.the_list_of_ingredients, parent, false);

        Log.d( ON_CREATE_VIEWHOLDER,  "OUT" );

        return new ViewHolder( v );

    }


    /*
     *this method connects the views to the data that is passed in to it
     * */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Log.d( ON_BIND_VIEWHOLDER,  "in" );


        Ingredients ingredients = ingredientsList.get( position );

        holder.theQuantity.setText( ingredients.getQuantity());
        holder.theMeasure.setText( ingredients.getMeasure() );
        holder.theIngredient.setText( ingredients.getIngredient() );

        Log.d( ON_BIND_VIEWHOLDER,  "OUT" );

    }


    /*
     *this class counts list so that the appropriate count shows on the screen
     * */
    @Override
    public int getItemCount() {
        Log.d( GET_ITEM_COUNT,  "in" );

        Log.d( GET_ITEM_COUNT,  "out" );

        return ingredientsList.size() ;
    }


    /*
     *this class connects the Id to the views for this file
     * */
    public  class ViewHolder extends RecyclerView.ViewHolder {


        @BindView( R.id.quantity )
        public TextView theQuantity;

        @BindView( R.id.measure )
        public TextView theMeasure;

        @BindView( R.id.ingredients )
        public TextView theIngredient;

         ViewHolder(View itemView) {
            super( itemView );
            ButterKnife.bind( this, itemView );

        }
    }
}
