package com.sofforce.makenbake;

import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.sofforce.makenbake.Activities.IngredientsAndSteps;
import com.sofforce.makenbake.Models.Ingredients;
import com.sofforce.makenbake.Models.RecipeInfo;
import com.sofforce.makenbake.Models.StepsToTake;
import com.sofforce.makenbake.Utilities.MyInstanceLifetime;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class IngredientsNStepsTest {


    @Rule
    public ActivityTestRule<IngredientsAndSteps> mIngreNsteps = new ActivityTestRule<>( IngredientsAndSteps.class );


    @Before
    public void grab_the_intent_with_id() {
        MyInstanceLifetime theInstance = MyInstanceLifetime.getAppInstance();
        theInstance.setRecipesModel( theRecipeData() );
        Intent intent =  new Intent();
        mIngreNsteps.launchActivity( intent );

    }

    @Test
    public void the_idling_test() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onData(withId(R.id.item_list)).check(matches(isDisplayed()));

    }


    private RecipeInfo theRecipeData() {
        RecipeInfo recipeInfo = new RecipeInfo();
        recipeInfo.setName( "JennyPie" );

        Ingredients inModel = new Ingredients();
        inModel.setIngredient( "sazon" );
        inModel.setMeasure( "1" );
        inModel.setQuantity( "teaspoon" );
        ArrayList<Ingredients> ingredientsArrayList = new ArrayList<>(  );
        ingredientsArrayList.add( inModel );

        StepsToTake stepsToTake = new StepsToTake();
        stepsToTake.setVideoURL( "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4" );
        stepsToTake.setThumbnailURL( "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda20_7-add-cream-mix-creampie/7-add-cream-mix-creampie.mp4" );
        stepsToTake.setShortDescription( "Go and boil something so that it cooks" );
        ArrayList<StepsToTake> stepsToTakeArrayList =  new ArrayList<>(  );

        recipeInfo.setIngredients( ingredientsArrayList );
        recipeInfo.setSteps( stepsToTakeArrayList );

        return recipeInfo;
    }


}
