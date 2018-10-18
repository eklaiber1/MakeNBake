package com.sofforce.makenbake.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sofforce.makenbake.Models.Ingredients;
import com.sofforce.makenbake.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    private List<Ingredients> ingredientsList;
    private Context context;

    public IngredientAdapter(Context context, List<Ingredients> ingredientsList) {
        this.context = context;
        this.ingredientsList = ingredientsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from( parent.getContext() )
                .inflate( R.layout.the_list_of_ingredients, parent, false);

        return new ViewHolder( v );

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Ingredients ingredients = ingredientsList.get( position );

        holder.theQuantity.setText( ingredients.getQuantity());
        holder.theMeasure.setText( ingredients.getMeasure() );
        holder.theIngredient.setText( ingredients.getIngredient() );

    }

    @Override
    public int getItemCount() {
        return ingredientsList.size() ;
    }

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
