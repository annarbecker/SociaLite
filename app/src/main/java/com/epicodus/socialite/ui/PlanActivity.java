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
import java.text.SimpleDateFormat;
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

    private Event newEvent;
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

        this.newEvent = new Event();

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        mCreateButton.setOnClickListener(this);
        mInviteButton.setOnClickListener(this);
        mSelectDateButton.setOnClickListener(this);
        mSelectTimeButton.setOnClickListener(this);
        mPickLocationButton.setOnClickListener(this);
        mMyLocation.setKeyListener(null);
        mDateEditText.setKeyListener(null);
        mTimeEditText.setKeyListener(null);

        this.getEventImage();
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
                String image = unsplashService.getImage();
                PlanActivity.this.newEvent.setImage(image);

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
        if (v == mCreateButton) {
            this.createEvent();
        }
        if (v == mInviteButton) {
            this.setInvitees();
        }
        if (v == mSelectDateButton) {
            this.setEventDate();
        }
        if (v == mSelectTimeButton) {
            this.setEventTime();
        }
        if (v == mPickLocationButton) {
            this.setEventLocation();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PLACE_PICKER_FLAG:
                    // get latitute and longitude of location
                    Place place = PlacePicker.getPlace(this, intent);
                    String latLong = place.getLatLng().toString();
                    latLong = latLong.substring(10);
                    latLong = latLong.substring(0, latLong.length() - 1);

                    this.newEvent.setLatLong(latLong);

                    mMyLocation.setVisibility(View.VISIBLE);
                    String location = place.getName() + "\n" + place.getAddress();
                    mMyLocation.setText(location);

                    this.newEvent.setLocation(location);
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

        if (id == R.id.action_add) {
            this.goToNextActivity(PlanActivity.class);
        }
        if (id == R.id.action_view){
            this.goToNextActivity(SavedEventsActivity.class);
        }
        if (id == R.id.action_home) {
            this.goToNextActivity(MainActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean requiredFieldsAreEmpty() {
        if (mEventEditText.getText().toString().isEmpty()
                || mMyLocation.getText().toString().isEmpty()
                || mDateEditText.getText().toString().isEmpty()
                || mTimeEditText.getText().toString().isEmpty()) {
            return true;
        }
        return false;
    }

    private void goToNextActivity(Class nextActivity) {
        Intent intent = new Intent(PlanActivity.this, nextActivity);
        startActivity(intent);
    }

    private void setEventLocation() {
        try {
            mBuilder = new PlacePicker.IntentBuilder();
            Intent intent = mBuilder.build(PlanActivity.this);
            startActivityForResult(intent, PLACE_PICKER_FLAG);

        }
        catch (GooglePlayServicesRepairableException e) {
            Toast.makeText(PlanActivity.this, "Google Play Services is not available.",
                    Toast.LENGTH_LONG)
                    .show();
        }
        catch (GooglePlayServicesNotAvailableException e) {
            Toast.makeText(PlanActivity.this, "Google Play Services is not available.",
                    Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void setEventTime() {
        final Calendar calendar = Calendar.getInstance();
        int calendarHour = calendar.get(Calendar.HOUR_OF_DAY);
        final int calendarMinute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");

                        String time = dateFormat.format(calendar.getTime());
                        mTimeEditText.setText(time);

                        PlanActivity.this.newEvent.setTime(time);
                    }
                }, calendarHour, calendarMinute, false);
        timePickerDialog.show();
    }

    private void setEventDate() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                        String eventDate = dateFormat.format(new Date(calendar.getTimeInMillis()));

                        mDateEditText.setText(eventDate);

                        PlanActivity.this.newEvent.setDate(eventDate);
                        PlanActivity.this.newEvent.setMillisecondDate(calendar.getTimeInMillis());
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void setInvitees() {
        if (this.requiredFieldsAreEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("Almost!")
                    .setMessage("Please fill out all event fields")
                    .setPositiveButton("OK", null)
                    .create()
                    .show();
        }
        else {
            // save the event
            this.newEvent.setName(mEventEditText.getText().toString());
            this.newEvent.setOrganizer(mSharedPreferences.getString(Constants.KEY_USER_NAME, null));

            String userUid = mSharedPreferences.getString(Constants.KEY_UID, null);
            DatabaseReference userEventsFirebaseRef = FirebaseDatabase.getInstance()
                    .getReference(Constants.FIREBASE_URL_USER_EVENT).child(userUid);
            DatabaseReference pushRef = userEventsFirebaseRef.push();
            String eventPushId = pushRef.getKey();
            newEvent.setPushId(eventPushId);
            pushRef.setValue(newEvent);

            mEditor.putString("EventPushId", eventPushId).apply();

            this.goToNextActivity(SearchContactsActivity.class);
        }
    }

    private void createEvent() {
        this.newEvent.setName(mEventEditText.getText().toString());

        if (this.requiredFieldsAreEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("Almost!")
                    .setMessage("Please fill out all event fields & add invitees")
                    .setPositiveButton("OK", null)
                    .create()
                    .show();
        }
        else {
            this.newEvent.setOrganizer(mSharedPreferences.getString(Constants.KEY_USER_NAME, null));

            Intent intent = new Intent(PlanActivity.this, ConfirmActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("newEvent", Parcels.wrap(newEvent));
            startActivity(intent);
            finish();
        }
    }
}
