package com.epicodus.socialite;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by arbecker on 5/2/16.
 */
public class SocialiteApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}