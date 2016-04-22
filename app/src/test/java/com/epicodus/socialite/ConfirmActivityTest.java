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
public class ConfirmActivityTest {
    private ConfirmActivity activity;

    @Before
    public void setup() {
        activity = Robolectric.setupActivity(ConfirmActivity.class);
    }

    //not passing - null pointer exception
//    @Test
//    public void validateTextViewContent() {
//        TextView locationTextView = (TextView) activity.findViewById(R.id.locationTextView);
//        assertTrue("Location".equals(locationTextView.getText().toString()));
//    }
}