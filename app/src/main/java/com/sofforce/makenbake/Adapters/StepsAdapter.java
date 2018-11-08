package com.sofforce.makenbake.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sofforce.makenbake.All_interfaces.ActivityClickListener;
import com.sofforce.makenbake.Models.Ingredients;
import com.sofforce.makenbake.Models.StepsToTake;
import com.sofforce.makenbake.R;
import com.sofforce.makenbake.Utilities.BitmapAsyncTask;
import com.sofforce.makenbake.Utilities.MyInstanceLifetime;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/*
* this steps recyclerview adapter class shows all the steps in the IngredientsAndSteps class
* */
public class StepsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //strings for the log.d files
    final static String ON_CREATE_VIEWHOLDER = "onCreateViewHolder";
    final static String ON_BIND_VIEWHOLDER =  "onBindViewHolder";
    final static String GET_ITEM_COUNT = "getItemCount";

    private static final int HEADER = 0;
    private static final int ITEM = 1;
    private List<StepsToTake> stepsToTakeList;
    private List<Ingredients> ingredientsList;
    private Context context;
    private final boolean isIn2Pane;
    private final ActivityClickListener theListener;

    public StepsAdapter(List<StepsToTake> stepsToTakeList, Context context, List<Ingredients> ingredientsList,
                        boolean isIn2Pane) {
        this.stepsToTakeList = stepsToTakeList;
        this.context = context;
        this.ingredientsList = ingredientsList;
        this.isIn2Pane = isIn2Pane;
        theListener = (ActivityClickListener) context;
    }

    /*
     *this method inflates the layout to the current activity
     * */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d( ON_CREATE_VIEWHOLDER,  "in" );

        if (viewType == HEADER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_food_ingredients, parent, false);
            return new IngredientsHolder(view);
        } else if (viewType == ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_food_steps, parent, false);
            return new StepsHolder(view);
        }
        Log.d( ON_CREATE_VIEWHOLDER,  "out" );

        return null;
    }

    /*
     *this method connects the views to the data that is passed in to it
     * */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        Log.d( ON_BIND_VIEWHOLDER,  "in" );

        if (position == 0 && stepsToTakeList.size() > 0 && isIn2Pane)
            theListener.onItemClick(position, null);

        if (holder instanceof IngredientsHolder) {
            final IngredientsHolder ingredientsHolder = (IngredientsHolder) holder;
            IngredientAdapter adapter = new IngredientAdapter(context, ingredientsList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            ingredientsHolder.recyclerView.setLayoutManager(linearLayoutManager);
            ingredientsHolder.recyclerView.setAdapter(adapter);

            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "font/ArchitectsDaughter-Regular.ttf" );
            ingredientsHolder.txt_ingredients.setTypeface(typeface);

            StringBuilder builder = new StringBuilder();
            builder.append(String.valueOf(ingredientsList.size()));
            builder.append(context.getString(R.string.space));
            builder.append(context.getString(R.string.ingredients));

            ingredientsHolder.txt_ingredients.setTypeface(typeface);
            ingredientsHolder.txt_ingredients.setText(builder.toString());

            } else if (holder instanceof StepsHolder) {
            final int pos = position - 1;
            final StepsHolder stepsHolder = (StepsHolder) holder;
            StringBuilder builder = new StringBuilder();
            builder.append(context.getString(R.string.step));
            builder.append(context.getString(R.string.space));
            builder.append(String.valueOf(Integer.parseInt(stepsToTakeList.get(pos).getId()) + 1));
            stepsHolder.txtStepNo.setText(builder.toString());
            stepsHolder.txtTitle.setText(stepsToTakeList.get(pos).getShortDescription());


            stepsHolder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyInstanceLifetime.getAppInstance().setBundle(null);
                    theListener.onItemClick(pos, null);
                }
            });


            Picasso.get().load(stepsToTakeList.get(pos).getThumbnailURL()).placeholder(R.drawable.videocamera01)
                    .error(R.drawable.videocamera01).into(stepsHolder.img_place_holder, new Callback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError(Exception e) {
                    //Thumbnail download of videos and cache it
                    BitmapAsyncTask.retrieveVideoFrameFromVideo(stepsToTakeList.get(pos).getVideoURL(), stepsHolder.img_place_holder);
                }
            });
        }

        Log.d( ON_BIND_VIEWHOLDER,  "out" );

    }

    /*
     *this class counts list so that the appropriate count shows on the screen
     * */
    @Override
    public int getItemCount() {
        Log.d( GET_ITEM_COUNT,  "in" );

        Log.d( GET_ITEM_COUNT,  "out" );

        return stepsToTakeList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return HEADER;
        return ITEM;
    }


    /*
     *this class connects the Id to the views for this file
     * */
    class IngredientsHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_ingredients)
        TextView txt_ingredients;
        @BindView(R.id.recycler)
        RecyclerView recyclerView;
        @BindView(R.id.container)
        LinearLayout container;
        @BindView(R.id.cardView)
        CardView cardView;

        IngredientsHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /*
     *this class connects the Id to the views for this file
     * */
    class StepsHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_step_no)
        TextView txtStepNo;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.container)
        LinearLayout container;
        @BindView(R.id.cardView)
        CardView cardView;
        @BindView(R.id.img_place_holder)
        ImageView img_place_holder;



        StepsHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
