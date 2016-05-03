package com.epicodus.socialite;

/**
 * Created by arbecker on 4/29/16.
 */
public class Constants {
    public static final String UNSPLASH_API_KEY = BuildConfig.UNSPLASH_API_KEY;
    public static final String PHOTO_BASE_URL = "https://api.unsplash.com/photos/random";
    public static final String CLIENT_ID_PARAM = "client_id";
    public static final String FIREBASE_URL = BuildConfig.FIREBASE_ROOT_URL;
    public static final String FIREBASE_EVENT_NEW = "event";
    public static final String FIREBASE_URL_EVENT = FIREBASE_URL + "/" + FIREBASE_EVENT_NEW;
    public static final String FIREBASE_PERSON_NEW = "person";
    public static final String FIREBASE_URL_PERSON = FIREBASE_URL + "/" + FIREBASE_PERSON_NEW;



}
