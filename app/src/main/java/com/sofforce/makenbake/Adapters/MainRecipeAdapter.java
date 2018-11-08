package com.sofforce.makenbake.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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


/*
 *this is the recycleview adapter for the homescreen activity, when it is launched
 */
public class MainRecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //strings for the log.d files
    final static String ON_CREATE_VIEWHOLDER = "onCreateViewHolder";
    final static String ON_BIND_VIEWHOLDER =  "onBindViewHolder";
    final static String GET_ITEM_COUNT = "getItemCount";

    // all the global variables
    public final static String RECIPE_INFO_PASSED = "recipeInfo";
    private final List<RecipeInfo> recipeList;
    private Context context;
    private final ActivityClickListener clickListener;

    /*
     * this is the constructor
     */
    public MainRecipeAdapter(Context context, List<RecipeInfo> recipeList) {
        this.recipeList = recipeList;
        this.context = context;
        this.clickListener = (ActivityClickListener) context;

    }

    /*
     *this method inflates the layout to the current activity
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d( ON_CREATE_VIEWHOLDER,  "in" );

        View view = View.inflate( context, R.layout.fragment_recipe_card, null );

        Log.d( ON_CREATE_VIEWHOLDER,  "out" );

        return new ViewHolder( view );
    }


    /*
     *this method connects the views to the data that is passed in to it
     */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        Log.d( ON_BIND_VIEWHOLDER,  "in" );


        ViewHolder viewHolder = (ViewHolder) holder;
        RecipeInfo recipeInfo = recipeList.get( position );
        viewHolder.theRecipeName.setText( recipeInfo.getName() );
        viewHolder.howManyServings.setText(new StringBuilder().append( recipeInfo.getSteps().size())
                .append(" ")
                .append("servings").toString());

        Log.d( ON_BIND_VIEWHOLDER,  "out" );

    }

    /*
     *this class counts list so that the appropriate count shows on the screen
     */
    @Override
    public int getItemCount()
    {
        Log.d( GET_ITEM_COUNT,  "in" );
        Log.d( GET_ITEM_COUNT,  "out" );

        return recipeList.size();
    }


            //this class connects the Id to the views for this file
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
