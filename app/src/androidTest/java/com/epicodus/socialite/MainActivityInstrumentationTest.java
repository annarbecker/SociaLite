package com.epicodus.socialite;

import android.support.test.rule.ActivityTestRule;

import com.epicodus.socialite.ui.MainActivity;

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
public class MainActivityInstrumentationTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void makePlansButtonSendsToCorrectActivity() {
        onView(withId(R.id.makePlansButton)).perform(click());
        onView(withId(R.id.textView3)).check(matches
                (withText("New Event")));
    }
}
