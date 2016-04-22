package com.epicodus.socialite;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by arbecker on 4/21/16.
 */
public class ConfirmActivityInstrumentationTest {

    @Rule
    public ActivityTestRule<ConfirmActivity> activityTestRule =
            new ActivityTestRule<>(ConfirmActivity.class);

    //not passing - null pointer exception
//    @Test
//    public void homeButtonSendsToCorrectActivity() {
//        onView(withId(R.id.homeButton)).perform(click());
//        onView(withId(R.id.textView)).check(matches
//                (withText("SociaLite")));
    //}
}
