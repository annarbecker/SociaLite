package com.epicodus.socialite;

/**
 * Created by arbecker on 4/29/16.
 */
public class Constants {
    public static final String UNSPLASH_API_KEY = BuildConfig.UNSPLASH_API_KEY;
    public static final String PHOTO_BASE_URL = "https://api.unsplash.com/photos/random";
    public static final String CLIENT_ID_PARAM = "client_id";

    public static final String FIREBASE_URL = BuildConfig.FIREBASE_ROOT_URL;
    public static final String FIREBASE_URL_USER_EVENT = FIREBASE_URL + "/" + "user events";
    public static final String FIREBASE_LOCATION_USERS = "users";
    public static final String KEY_UID = "UID";
    public static final String FIREBASE_URL_USERS = FIREBASE_URL + "/" + FIREBASE_LOCATION_USERS;
    public static final String KEY_USER_EMAIL = "email";
    public static final String KEY_USER_NAME = "name";
    public static final String FIREBASE_URL_USERS_LIST = "https://socialite-ab.firebaseio.com/users";
    public static final String INVITEE_PHONE_NUMBERS = "phoneNumbers";

    public static final String PREFERENCES_EVENT = "event";
    public static final String PREFERENCES_CREATE_EVENT = "create event time";
}
