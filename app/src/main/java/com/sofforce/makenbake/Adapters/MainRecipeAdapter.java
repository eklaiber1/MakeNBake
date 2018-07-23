package com.sofforce.makenbake.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sofforce.makenbake.Models.RecipeInfo;
import com.sofforce.makenbake.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainRecipeAdapter extends RecyclerView.Adapter<MainRecipeAdapter.ViewHolder> {



    private List<RecipeInfo> recipeList = new ArrayList<>();
    private Context context;


    public MainRecipeAdapter(Context context, List<RecipeInfo> recipeList) {
        this.recipeList = recipeList;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from( parent.getContext() )
                .inflate( R.layout.fragment_recipe_card, parent, false );

        return new ViewHolder( v );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        RecipeInfo recipeInfo = recipeList.get( position );

        holder.theRecipeName.setText( recipeInfo.getName() );
        holder.howManyServings.setText( (int) recipeInfo.getServings() );

    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.recipe_card_name)
        TextView theRecipeName;
        @BindView(R.id.recipe_card_servings)
        TextView howManyServings;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind( this, view );
        }


    }

}
