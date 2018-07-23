package com.sofforce.makenbake.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sofforce.makenbake.Models.StepsToTake;
import com.sofforce.makenbake.R;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {

    private List<StepsToTake> stepsToTakeList;
    private Context context;

    public StepsAdapter(List<StepsToTake> stepsToTakeList1, Context context) {
        this.stepsToTakeList = stepsToTakeList1;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from( parent.getContext() )
                .inflate( R.layout.detailed_steps_to_take, parent, false);

        return new ViewHolder( v );

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        StepsToTake stepsToTake = stepsToTakeList.get( position );

        holder.theSteps.setText( (int) stepsToTake.getId() );
        holder.theShortDescription.setText( stepsToTake.getShortDescription() );
        holder.theDescription.setText( stepsToTake.getDescription() );

    }

    @Override
    public int getItemCount() {
        return stepsToTakeList.size() ;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {



        public TextView theSteps;
        public TextView theShortDescription;
        public TextView theDescription;

        public ViewHolder(View itemView) {
            super( itemView );

            theSteps = (TextView) itemView.findViewById( R.id.quantity );
            theShortDescription = (TextView) itemView.findViewById( R.id.measure );
            theDescription = (TextView) itemView.findViewById( R.id.ingredients );

        }
    }
}
