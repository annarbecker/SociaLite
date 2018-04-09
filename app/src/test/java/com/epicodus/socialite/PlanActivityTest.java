package com.epicodus.socialite;

import android.content.Context;
import android.os.Build;
import android.widget.TextView;

import com.epicodus.socialite.ui.PlanActivity;
import com.google.firebase.FirebaseApp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.internal.bytecode.ClassHandler;

import static junit.framework.Assert.assertTrue;

/**
 * Created by arbecker on 4/21/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class PlanActivityTest {
    private PlanActivity activity;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(PlanActivity.class).create().get();
    }

    @Test
    public void validateTextViewContent() {
        TextView textView3 = (TextView) activity.findViewById(R.id.textView3);
        if (textView3 != null) {
            assertTrue("New Event".equals(textView3.getText().toString()));
        }
    }
}
