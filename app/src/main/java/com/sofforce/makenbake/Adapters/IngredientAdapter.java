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

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    private List<Ingredients> ingredientsList;
    private Context context;

    public IngredientAdapter(List<Ingredients> ingredientsList, Context context) {
        this.ingredientsList = ingredientsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from( parent.getContext() )
                .inflate( R.layout.detailed_ingredients, parent, false);

        return new ViewHolder( v );

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Ingredients ingredients = ingredientsList.get( position );

        holder.theQuantity.setText( (int) ingredients.getQuantity());
        holder.theMeasure.setText( ingredients.getMeasure() );
        holder.theIngredient.setText( ingredients.getIngredient() );

    }

    @Override
    public int getItemCount() {
        return ingredientsList.size() ;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {



        public TextView theQuantity;
        public TextView theMeasure;
        public TextView theIngredient;

        public ViewHolder(View itemView) {
            super( itemView );

            theQuantity = (TextView) itemView.findViewById( R.id.quantity );
            theMeasure = (TextView) itemView.findViewById( R.id.measure );
            theIngredient = (TextView) itemView.findViewById( R.id.ingredients );

        }
    }
}
