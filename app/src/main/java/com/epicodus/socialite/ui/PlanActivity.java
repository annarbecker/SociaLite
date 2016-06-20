package com.epicodus.socialite.ui;

import android.app.Activity;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;


import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PlanActivity extends AppCompatActivity implements View.OnClickListener{
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
    private Long mMillisecondDate;
    private String latLong;
    private String image;
    private String mEventCreateDate;
    private String event;

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
        mEventEditText.setText(mSharedPreferences.getString(Constants.PREFERENCES_EVENT, null));

        mCreateButton.setOnClickListener(this);
        mInviteButton.setOnClickListener(this);
        mSelectDateButton.setOnClickListener(this);
        mSelectTimeButton.setOnClickListener(this);
        mPickLocationButton.setOnClickListener(this);
        mMyLocation.setKeyListener(null);
        mDateEditText.setKeyListener(null);
        mTimeEditText.setKeyListener(null);


        getEventImage();
        mEventEditText.requestFocus();
        mEventEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


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

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    public void onClick(View v) {
        if(v == mCreateButton) {
            mEventCreateDate = mSharedPreferences.getString(Constants.PREFERENCES_CREATE_EVENT, null);
            String event = mEventEditText.getText().toString();
            String location = mMyLocation.getText().toString();
            String date = mDateEditText.getText().toString();
            String time = mTimeEditText.getText().toString();
            mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

            Event newEvent = new Event(event, location, date, time, latLong, image, mMillisecondDate, mEventCreateDate);

            if(!event.equals("") && !location.equals("") && !date.equals("") && !time.equals("") && !latLong.equals("")) {
                Intent intent = new Intent(PlanActivity.this, ConfirmActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("newEvent", Parcels.wrap(newEvent));
                startActivity(intent);
                finish();

                String userUid = mSharedPreferences.getString(Constants.KEY_UID, null);
                Firebase userEventsFirebaseRef = new Firebase(Constants.FIREBASE_URL_USER_EVENT).child(userUid);
                Firebase pushRef = userEventsFirebaseRef.push();
                String eventPushId = pushRef.getKey();
                newEvent.setPushId(eventPushId);
                pushRef.setValue(newEvent);

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
                Date currentDate = new Date();
                Long eventCreated = currentDate.getTime();
                mEventCreateDate = eventCreated.toString();
                event = mEventEditText.getText().toString();
                addToSharedPreferences(event);
                addTimeToSharedPreferences(mEventCreateDate);

                Intent intent = new Intent(PlanActivity.this, SearchContactsActivity.class);
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

                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, monthOfYear, dayOfMonth);
                            mMillisecondDate = calendar.getTimeInMillis();
                            Log.d("date", mMillisecondDate+"");
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
                            }
                            if(hourOfDay > 12 && minute >= 10) {
                                mTimeEditText.setText((hourOfDay - 12) + ":" + minute + " PM");
                            }
                            if(hourOfDay == 12 && minute < 10 || hourOfDay < 12 && minute < 10) {
                                mTimeEditText.setText(hourOfDay + ":0" + minute + " PM");
                            }
                            if(hourOfDay == 12 && minute > 10 || hourOfDay < 12 && minute > 10) {
                                mTimeEditText.setText(hourOfDay + ":" + minute + " PM");
                            }
                            if(hourOfDay < 12 && minute < 10) {
                                mTimeEditText.setText((hourOfDay) + ":0" + minute + " AM");
                            }
                            if(hourOfDay < 12 && minute >= 10) {
                                mTimeEditText.setText((hourOfDay) + ":" + minute + " AM");
                            }
                            if(hourOfDay == 0 && minute >= 10) {
                                mTimeEditText.setText((hourOfDay  + 12) + ":" + minute + " AM");
                            }
                            if(hourOfDay == 0 && minute < 10) {
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

        if(id == R.id.action_add) {
            mEditor.putString(Constants.PREFERENCES_EVENT, "").apply();
            mEditor.putString(Constants.INVITEE_PHONE_NUMBERS, "").apply();
            mEditor.putString(Constants.PREFERENCES_CREATE_EVENT, "").apply();
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

    private void addToSharedPreferences(String event) {
        mEditor.putString(Constants.PREFERENCES_EVENT, event).apply();
    }

    private void addTimeToSharedPreferences(String createDate) {
        mEditor.putString(Constants.PREFERENCES_CREATE_EVENT, createDate).apply();
    }
}
