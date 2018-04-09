package com.epicodus.socialite;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;


public class SocialiteApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // enable Firebase persistence for offline access
        if (!FirebaseApp.getApps(this).isEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
    }
}