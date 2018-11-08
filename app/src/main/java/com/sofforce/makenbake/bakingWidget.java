package com.sofforce.makenbake;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.sofforce.makenbake.Activities.HomeScreenList;
import com.sofforce.makenbake.Models.Ingredients;
import com.sofforce.makenbake.Utilities.ConstantsForApp;
import com.sofforce.makenbake.Utilities.MyInstanceLifetime;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class bakingWidget extends AppWidgetProvider {

    private RemoteViews remoteViews;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {

            remoteViews = new RemoteViews( context.getPackageName(), R.layout.baking_widget );
            appWidgetManager.updateAppWidget( appWidgetId, remoteViews );

            Intent intent = new Intent( context, HomeScreenList.class );
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,0);
            remoteViews.setOnClickPendingIntent( R.id.table_image, pendingIntent );


        }
    }



    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive( context, intent );

        final String ingredientFeed = intent.getAction();
        remoteViews = new RemoteViews( context.getPackageName(), R.layout.baking_widget );
        Intent homeScreenLaunch = new Intent( context, HomeScreenList.class );
        PendingIntent pendingIntent = PendingIntent.getActivity( context, 0, homeScreenLaunch, 0 );
        remoteViews.setOnClickPendingIntent( R.id.table_image, pendingIntent );

        if (ingredientFeed.equals( AppWidgetManager.ACTION_APPWIDGET_OPTIONS_CHANGED ) ||
                ingredientFeed.equals( ConstantsForApp.ACTION_DATA_UPDATE )) {
            remoteViews.setTextViewText( R.id.txt_ingredients, MyInstanceLifetime.getRecipeName( context ) );
            List<Ingredients> list =  MyInstanceLifetime.getAppInstance().getIngredients( context );
            StringBuilder buildString = new StringBuilder(  );
            for (Ingredients model : list)
                buildString.append(model.getIngredient() + " " + model.getMeasure() + " " + model.getQuantity() + "\n");
            remoteViews.setTextViewText( R.id.text, buildString.toString() );


            AppWidgetManager.getInstance(context).updateAppWidget(
                    new ComponentName(context, bakingWidget.class), remoteViews);

        }


    }
}

