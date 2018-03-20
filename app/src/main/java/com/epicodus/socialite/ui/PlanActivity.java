package com.epicodus.socialite.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PlanActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.eventEditText) EditText mEventEditText;
    @BindView(R.id.inviteButton) Button mInviteButton;
    @BindView(R.id.selectdate) Button mSelectDateButton;
    @BindView(R.id.selecttime) Button mSelectTimeButton;
    @BindView(R.id.dateEditText) EditText mDateEditText;
    @BindView(R.id.timeEditText) EditText mTimeEditText;
    @BindView(R.id.createButton) Button mCreateButton;
    @BindView(R.id.pickLocationButton) Button mPickLocationButton;
    @BindView(R.id.myLocation) AutoCompleteTextView mMyLocation;
    @BindView(R.id.toolbar) Toolbar topToolBar;

    private Long mMillisecondDate;
    private String latLong;
    private String image;
    private String mEventCreateDate;
    private String date;
    private String time;
    private String location;
    private String alert;

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
        mTimeEditText.setText(mSharedPreferences.getString(Constants.PREFERENCES_TIME, null));
        mDateEditText.setText(mSharedPreferences.getString(Constants.PREFERENCES_DATE, null));
        mMyLocation.setText(mSharedPreferences.getString(Constants.PREFERENCES_LOCATION, null));

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
            location = mMyLocation.getText().toString();
            date = mDateEditText.getText().toString();
            time = mTimeEditText.getText().toString();
            alert = "no";


            if(this.requiredFieldsAreEmpty()) {
                new AlertDialog.Builder(this)
                        .setTitle("Almost!")
                        .setMessage("Please fill out all event fields & add invitees")
                        .setPositiveButton("OK", null)
                        .create()
                        .show();
            } else {
                Long milliSecondDateLong = Long.valueOf(mSharedPreferences.getString(
                        Constants.PREFERENCES_MILLISECOND_DATE, null));
                Event newEvent = new Event(event, location, date, time, latLong, image,
                        milliSecondDateLong, mEventCreateDate, alert);
                newEvent.setOrganizer(mSharedPreferences.getString(Constants.KEY_USER_NAME, null));

                Intent intent = new Intent(PlanActivity.this, ConfirmActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("newEvent", Parcels.wrap(newEvent));
                startActivity(intent);
                finish();

                String userUid = mSharedPreferences.getString(Constants.KEY_UID, null);
                DatabaseReference userEventsFirebaseRef = FirebaseDatabase.getInstance()
                        .getReference(Constants.FIREBASE_URL_USER_EVENT).child(userUid);
                DatabaseReference pushRef = userEventsFirebaseRef.push();
                String eventPushId = pushRef.getKey();
                newEvent.setPushId(eventPushId);
                pushRef.setValue(newEvent);
            }
        }
        if(v == mInviteButton) {
            if(this.requiredFieldsAreEmpty()) {
                new AlertDialog.Builder(this)
                        .setTitle("Almost!")
                        .setMessage("Please fill out all event fields")
                        .setPositiveButton("OK", null)
                        .create()
                        .show();

            } else {
                Date currentDate = new Date();
                Long eventCreated = currentDate.getTime();
                mEventCreateDate = eventCreated.toString();

                this.addValueToSharedPreferences(Constants.PREFERENCES_EVENT,
                        mEventEditText.getText().toString());
                this.addValueToSharedPreferences(Constants.PREFERENCES_CREATE_EVENT, mEventCreateDate);
                this.addValueToSharedPreferences(Constants.PREFERENCES_DATE,
                        mDateEditText.getText().toString());
                this.addValueToSharedPreferences(Constants.PREFERENCES_TIME,
                        mTimeEditText.getText().toString());
                this.addValueToSharedPreferences(Constants.PREFERENCES_LOCATION,
                        mMyLocation.getText().toString());
                this.addValueToSharedPreferences(Constants.PREFERENCES_LAT_LONG, latLong);
                this.addValueToSharedPreferences(Constants.PREFERENCES_IMAGE, image);

                Intent intent = new Intent(PlanActivity.this, SearchContactsActivity.class);
                startActivity(intent);
            }

        }
        if(v == mSelectDateButton){
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            mDateEditText.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);

                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, monthOfYear, dayOfMonth);
                            mMillisecondDate = calendar.getTimeInMillis();

                            addValueToSharedPreferences(Constants.PREFERENCES_MILLISECOND_DATE,
                                    mMillisecondDate.toString());
                        }
                    }, year, month, day);
            datePickerDialog.show();
        }
        if(v == mSelectTimeButton) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

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
                    }, hour, minute, false);
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
            mEditor.clear();
            this.goToNextActivity(PlanActivity.class);
        }
        if(id == R.id.action_view){
            this.goToNextActivity(SavedEventsActivity.class);
        }
        if(id == R.id.action_home) {
            this.goToNextActivity(MainActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }

    private void addValueToSharedPreferences(String key, String value) {
        mEditor.putString(key, value).apply();
    }

    private boolean requiredFieldsAreEmpty() {
        if(mEventEditText.getText().toString().isEmpty() || mMyLocation.getText().toString().isEmpty()
                || mDateEditText.getText().toString().isEmpty() || mTimeEditText.getText().toString().isEmpty()
                || mEventCreateDate.isEmpty()) {
            return true;
        }
        return false;
    }

    private void goToNextActivity(Class nextActivity) {
        Intent intent = new Intent(PlanActivity.this, nextActivity);
        startActivity(intent);
    }
}
