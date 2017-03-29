package com.epicodus.socialite;

import android.support.test.rule.ActivityTestRule;

import com.epicodus.socialite.ui.PlanActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;


/**
 * Created by arbecker on 4/21/16.
 */
public class PlanActivityInstrumentationTest {
    @Rule
    public ActivityTestRule<PlanActivity> activityTestRule =
            new ActivityTestRule<>(PlanActivity.class);


    @Test
    public void validatePlanEditText() {
        onView(withId(R.id.eventEditText)).perform(typeText("Dinner"))
                .check(matches(withText("Dinner")));
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.dateEditText)).perform(typeText("04/25/2016"), closeSoftKeyboard())
                .check(matches(withText("04/25/2016")));
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.timeEditText)).perform(typeText("6:00pm"), closeSoftKeyboard())
                .check(matches(withText("6:00pm")));
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void eventIsSentToConfirmActivity() {
        String event = "Dinner";
        String invitee = "Jane";
        String location = "1234 NW Alphabet Lane";
        String date = "04/25/2016";
        String time = "6:00pm";
        onView(withId(R.id.eventEditText)).perform(typeText(event));
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.dateEditText)).perform(typeText(date) ,closeSoftKeyboard());
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.timeEditText)).perform(typeText(time) ,closeSoftKeyboard());
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.createButton)).perform(click());
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.userEventTextView)).check(matches
                (withText(event)));
        onView(withId(R.id.userLocationTextView)).check(matches
                (withText(location)));
        onView(withId(R.id.userDateTextView)).check(matches
                (withText(date)));
        onView(withId(R.id.userTimeTextView)).check(matches
                (withText(time)));
    }
}
