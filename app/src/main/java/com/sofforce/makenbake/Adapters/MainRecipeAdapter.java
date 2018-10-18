package com.sofforce.makenbake.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sofforce.makenbake.All_interfaces.ActivityClickListener;
import com.sofforce.makenbake.Models.RecipeInfo;
import com.sofforce.makenbake.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainRecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // all the global variables
    public final static String RECIPE_INFO_PASSED = "recipeInfo";
    private final List<RecipeInfo> recipeList;
    private Context context;
    private final ActivityClickListener clickListener;


    public MainRecipeAdapter(Context context, List<RecipeInfo> recipeList) {
        this.recipeList = recipeList;
        this.context = context;
        this.clickListener = (ActivityClickListener) context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate( context, R.layout.fragment_recipe_card, null );

        return new ViewHolder( view );
    }



    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        ViewHolder viewHolder = (ViewHolder) holder;
        RecipeInfo recipeInfo = recipeList.get( position );
        viewHolder.theRecipeName.setText( recipeInfo.getName() );
        viewHolder.howManyServings.setText(new StringBuilder().append( recipeInfo.getSteps().size())
                .append(" ")
                .append("servings").toString());


    }

    @Override
    public int getItemCount()
    {

        return recipeList.size();
    }



            //this is the inner viewholder class
            public class ViewHolder extends RecyclerView.ViewHolder {

                @BindView(R.id.recipe_card_name)
                TextView theRecipeName;
                @BindView(R.id.recipe_card_servings)
                TextView howManyServings;
                @BindView( R.id.img_food )
                ImageView food_image;

                ViewHolder(View view) {
                    super(view);
                    ButterKnife.bind( this, view );

                }

                @OnClick(R.id.individualCards)
                public void onTouch() {
                    clickListener.onItemClick( getAdapterPosition(), food_image );

                }


            }

}
