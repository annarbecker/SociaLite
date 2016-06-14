package com.epicodus.socialite.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
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
import java.util.Calendar;


import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PlanActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = PlanActivity.class.getSimpleName();

    @Bind(R.id.eventEditText) EditText mEventEditText;
    @Bind(R.id.inviteButton) Button mInviteButton;
    @Bind(R.id.selectdate) Button mSelectDateButton;
    @Bind(R.id.selecttime) Button mSelectTimeButton;
    @Bind(R.id.dateEditText) EditText mDateEditText;
    @Bind(R.id.timeEditText) EditText mTimeEditText;
    @Bind(R.id.createButton) Button mCreateButton;
    @Bind(R.id.pickLocationButton) Button mPickLocationButton;
    @Bind(R.id.myLocation) AutoCompleteTextView mMyLocation;
    @Bind(R.id.toolbar) Toolbar topToolBar;


    private int mYear, mMonth, mDay, mHour, mMinute;

    private String latLong;
    public String image;

    private PlacePicker.IntentBuilder mBuilder;
    private static final int PLACE_PICKER_FLAG = 1;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        ButterKnife.bind(this);

        setTitle(null);
        setSupportActionBar(topToolBar);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        mCreateButton.setOnClickListener(this);
        mInviteButton.setOnClickListener(this);
        mSelectDateButton.setOnClickListener(this);
        mSelectTimeButton.setOnClickListener(this);
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
            mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

            Intent intent = new Intent(PlanActivity.this, ConfirmActivity.class);
            Event newEvent = new Event(event, location, date, time, latLong, image);

            if(!event.equals("") && !location.equals("") && !date.equals("") && !time.equals("") && !latLong.equals("")) {
                intent.putExtra("newEvent", Parcels.wrap(newEvent));
                startActivity(intent);

                String userUid = mSharedPreferences.getString(Constants.KEY_UID, null);
                Firebase userEventsFirebaseRef = new Firebase(Constants.FIREBASE_URL_EVENT).child(userUid);
                Firebase pushRef = userEventsFirebaseRef.push();
                String eventPushId = pushRef.getKey();
                newEvent.setPushId(eventPushId);
                pushRef.setValue(newEvent);

                mEventEditText.setText("");
                mMyLocation.setText("");
                mMyLocation.setText("");
                mDateEditText.setText("");
                mTimeEditText.setText("");
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Almost!")
                        .setMessage("Please fill out all event fields")
                        .setPositiveButton("OK", null)
                        .create()
                        .show();
            }
        }
        if(v == mInviteButton) {
            if(mEventEditText.getText().toString().equals("")) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this).setTitle("Please enter an event name");

                final EditText eventPrompt = new EditText(this);
                alertDialogBuilder.setView(eventPrompt);

                alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String eventPromptEditText = eventPrompt.getText().toString();
                        mEventEditText.setText(eventPromptEditText);
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            } else {
                Intent intent = new Intent(PlanActivity.this, SearchContactsActivity.class);
                String event = mEventEditText.getText().toString();
                addToSharedPreferences(event);
                startActivity(intent);
            }

        }
        if(v == mSelectDateButton){
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            mDateEditText.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if(v == mSelectTimeButton) {
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            if(hourOfDay > 12 && minute < 10) {
                                mTimeEditText.setText((hourOfDay - 12) + ":0" + minute + " PM");
                            } if(hourOfDay > 12 && minute >= 10) {
                                mTimeEditText.setText((hourOfDay - 12) + ":" + minute + " PM");
                            } if(hourOfDay == 12 && minute < 10 || hourOfDay < 12 && minute < 10) {
                                mTimeEditText.setText(hourOfDay + ":0" + minute + " PM");
                            } if(hourOfDay == 12 && minute > 10 || hourOfDay < 12 && minute > 10) {
                                hourOfDay = hourOfDay - 12;
                                mTimeEditText.setText(hourOfDay + ":" + minute + " PM");
                            } if(hourOfDay < 12 && minute < 10) {
                                mTimeEditText.setText(hourOfDay + ":0" + minute + " AM");
                            } if(hourOfDay < 12 && minute >= 10) {
                                mTimeEditText.setText(hourOfDay + ":" + minute + " AM");
                            } if(hourOfDay == 0 && minute >= 10) {
                                mTimeEditText.setText(hourOfDay + ":" + minute + " AM");
                            } if(hourOfDay == 0 && minute < 10) {
                                mTimeEditText.setText((hourOfDay + 12) + ":0" + minute + " AM");
                            }
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();

        }
        if(v == mPickLocationButton) {
            try {
                mBuilder = new PlacePicker.IntentBuilder();
                Intent intent = mBuilder.build(PlanActivity.this);
                startActivityForResult(intent, PLACE_PICKER_FLAG);

            } catch (GooglePlayServicesRepairableException e) {
                Toast.makeText(PlanActivity.this, "Google Play Services is not available.",
                        Toast.LENGTH_LONG)
                        .show();
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
                    mMyLocation.setText(place.getName() + "\n" + place.getAddress());
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
        if(id == R.id.action_home) {
            Intent intent = new Intent(PlanActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void addToSharedPreferences(String location) {
        mEditor.putString(Constants.PREFERENCES_EVENT, location).apply();
    }
}
