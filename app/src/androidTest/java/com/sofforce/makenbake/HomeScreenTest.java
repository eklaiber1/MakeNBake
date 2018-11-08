package com.sofforce.makenbake;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.sofforce.makenbake.Activities.HomeScreenList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class HomeScreenTest {

    @Rule public ActivityTestRule<HomeScreenList> homeScreenListActivityTestRule
            = new ActivityTestRule<> (HomeScreenList.class);


    @Test
    public void onclick_recipe_card_to_the_next_activity() {
        //1.find the view

        //2.perform action on view

        //3.check if the view does what you expect

        try {
            // because of slow emulator made 10sec delay
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.items_in_recycler)).check(matches(isDisplayed()));


    }

}
