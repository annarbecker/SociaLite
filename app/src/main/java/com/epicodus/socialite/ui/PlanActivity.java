package com.epicodus.socialite.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.epicodus.socialite.Constants;
import com.epicodus.socialite.R;
import com.epicodus.socialite.models.Event;
import com.epicodus.socialite.services.UnsplashService;
import com.firebase.client.Firebase;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;


import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PlanActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = PlanActivity.class.getSimpleName();

    @Bind(R.id.eventEditText) EditText mEventEditText;
    @Bind(R.id.inviteButton) Button mInviteButton;
    @Bind(R.id.dateEditText) EditText mDateEditText;
    @Bind(R.id.timeEditText) EditText mTimeEditText;
    @Bind(R.id.createButton) Button mCreateButton;
    @Bind(R.id.pickLocationButton) Button mPickLocationButton;
    @Bind(R.id.myLocation) AutoCompleteTextView mMyLocation;
    @Bind(R.id.toolbar) Toolbar topToolBar;

    private String latLong;
    public String image;

    private PlacePicker.IntentBuilder mBuilder;
    private static final int PLACE_PICKER_FLAG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        ButterKnife.bind(this);

        setTitle(null);
        setSupportActionBar(topToolBar);

        mCreateButton.setOnClickListener(this);
        mInviteButton.setOnClickListener(this);
        mPickLocationButton.setOnClickListener(this);
        getEventImage();
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
                 image = unsplashService.getImage();

                PlanActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == mCreateButton) {
            String event = mEventEditText.getText().toString();
            String location = mMyLocation.getText().toString();
            String date = mDateEditText.getText().toString();
            String time = mTimeEditText.getText().toString();

            Intent intent = new Intent(PlanActivity.this, ConfirmActivity.class);
            Event newEvent = new Event(event, location, date, time, latLong, image);
            intent.putExtra("newEvent", Parcels.wrap(newEvent));
            startActivity(intent);

            Firebase ref = new Firebase(Constants.FIREBASE_URL_EVENT);
            ref.push().setValue(newEvent);
            Toast.makeText(PlanActivity.this, "Event Saved", Toast.LENGTH_SHORT).show();
        }
        if(v == mInviteButton) {
            Intent intent = new Intent(PlanActivity.this, SearchContactsActivity.class);
            startActivity(intent);
        }
        if(v == mPickLocationButton) {
            try {
                mBuilder = new PlacePicker.IntentBuilder();
                Intent intent = mBuilder.build(PlanActivity.this);
                startActivityForResult(intent, PLACE_PICKER_FLAG);

            } catch (GooglePlayServicesRepairableException e) {
                GooglePlayServicesUtil
                        .getErrorDialog(e.getConnectionStatusCode(), PlanActivity.this, 0);
            } catch (GooglePlayServicesNotAvailableException e) {
                Toast.makeText(PlanActivity.this, "Google Play Services is not available.",
                        Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PLACE_PICKER_FLAG:
                    Place place = PlacePicker.getPlace(this, intent);
                    latLong = place.getLatLng().toString();
                    latLong = latLong.substring(10);
                    latLong = latLong.substring(0, latLong.length() - 1);

                    mMyLocation.setVisibility(View.VISIBLE);
                    mMyLocation.setText(place.getName() + ", " + place.getAddress());
                    break;
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_add){
            Intent intent = new Intent(PlanActivity.this, PlanActivity.class);
            startActivity(intent);
        }
        if(id == R.id.action_view){
            Intent intent = new Intent(PlanActivity.this, SavedEventsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
