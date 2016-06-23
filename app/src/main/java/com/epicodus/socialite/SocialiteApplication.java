package com.epicodus.socialite;

import android.app.Application;

import com.firebase.client.Firebase;


public class SocialiteApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}