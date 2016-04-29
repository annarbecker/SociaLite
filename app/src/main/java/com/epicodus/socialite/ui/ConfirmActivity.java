package com.epicodus.socialite.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.epicodus.socialite.R;
import com.epicodus.socialite.models.Event;
import com.epicodus.socialite.services.UnsplashService;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ConfirmActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = ConfirmActivity.class.getSimpleName();
    @Bind(R.id.imageView) ImageView mImage;
    @Bind(R.id.userEventTextView) TextView mUserEventTextView;
    @Bind(R.id.userLocationTextView) TextView mUserLocationTextView;
    @Bind(R.id.userDateTextView) TextView mUserDateTextView;
    @Bind(R.id.userTimeTextView) TextView mUserTimeTextView;
    @Bind(R.id.listView) ListView mListView;
    @Bind(R.id.homeButton) Button mHomeButton;

    private String mLatLong;
    private String mLocation;
    private Event newEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        ButterKnife.bind(this);
        getEventImage();

        newEvent = (Event) Parcels.unwrap(getIntent().getParcelableExtra("newEvent"));

        mUserEventTextView.setText(newEvent.getName());
        mUserLocationTextView.setText(newEvent.getLocation());
        mUserDateTextView.setText(newEvent.getDate());
        mUserTimeTextView.setText(newEvent.getTime());
        mLatLong = (newEvent.getLatLong());
        mLocation = (newEvent.getLocation());
        mHomeButton.setOnClickListener(this);
        mUserLocationTextView.setOnClickListener(this);

        Log.v(TAG, newEvent.getLocation());


//        String inviteesString = intent.getStringExtra("inviteeArray");
//        String[] inviteesList = inviteesString.split(", ");
//
//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, inviteesList);
//        mListView.setAdapter(adapter);

    }


    @Override
    public void onClick(View v) {
        if (v == mHomeButton) {
            Intent intent = new Intent(ConfirmActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Toast.makeText(ConfirmActivity.this, "your event has been shared", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        } if(v == mUserLocationTextView) {
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + mLatLong + "?q=(" + newEvent.getLocation() + ")"));
            startActivity(mapIntent);
        }
    }

    private void getEventImage() {
        final UnsplashService unsplashService = new UnsplashService();
        unsplashService.getPhotos(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                unsplashService.processPhotos(response);
                final String eventImage = unsplashService.getImage();

                ConfirmActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(mImage !=null) {
                            Picasso.with(ConfirmActivity.this).load(eventImage).into(mImage);
                        }
                    }
                });
            }
        });
    }
}