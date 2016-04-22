package com.epicodus.socialite;

import android.os.Build;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertTrue;

/**
 * Created by arbecker on 4/21/16.
 */
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class PlanActivityTest {
    private PlanActivity activity;

    @Before
    public void setup() {
        activity = Robolectric.setupActivity(PlanActivity.class);
    }

    @Test
    public void validateTextViewContent() {
        TextView textView3 = (TextView) activity.findViewById(R.id.textView3);
        assertTrue("New Event".equals(textView3.getText().toString()));
    }
}
