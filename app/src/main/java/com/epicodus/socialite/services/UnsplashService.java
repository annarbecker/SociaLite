package com.epicodus.socialite.services;


import com.epicodus.socialite.Constants;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class UnsplashService {
    public static final String TAG = UnsplashService.class.getSimpleName();
    public static String imageUrl;

    public void getPhotos(Callback callback) {
        String UNSPLASH_API_KEY = Constants.UNSPLASH_API_KEY;
        String PHOTO_URL_BASE = Constants.PHOTO_BASE_URL;
        String CLIENT_ID_PARAM = Constants.CLIENT_ID_PARAM;

        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(PHOTO_URL_BASE).newBuilder();

        urlBuilder.addQueryParameter(CLIENT_ID_PARAM, UNSPLASH_API_KEY);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public void processPhotos(Response response) {
        try {
            String jsonData = response.body().string();
            if(response.isSuccessful()) {
                JSONObject photoDatabaseJSON = new JSONObject(jsonData);
                JSONObject urlsJSON = photoDatabaseJSON.getJSONObject("urls");
                UnsplashService.imageUrl = urlsJSON.getString("regular");
            }
        } catch(IOException e) {
            e.printStackTrace();
        } catch(JSONException j) {
            j.printStackTrace();
        }
    }

    public String getImage() {
        return UnsplashService.imageUrl;
    }


}
