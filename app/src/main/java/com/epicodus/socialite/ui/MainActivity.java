package com.epicodus.socialite.ui;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.epicodus.socialite.Constants;
import com.epicodus.socialite.R;
import com.epicodus.socialite.models.Event;
import com.epicodus.socialite.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.makePlansButton) Button mMakePlansButton;
    @BindView(R.id.textView) TextView mTextView;
    @BindView(R.id.viewEventsButton) Button mViewEventsButton;
    @BindView(R.id.toolbar) Toolbar topToolBar;
    @BindView(R.id.welcomeTextView)TextView mWelcomeTextView;

    private DatabaseReference mSavedEventRef;
    private DatabaseReference mFirebaseRef;
    private ValueEventListener mSavedEventRefListener;
    private String mUId;
    private DatabaseReference mUserRef;
    private SharedPreferences mSharedPreferences;
    private ValueEventListener mUserRefListener;
    private SharedPreferences.Editor mEditor;
    private Context mContext;
    private String eventName;
    private String eventDate;
    private String eventTime;
    private String eventLocation;
    private String eventId;
    private long createEventTimestamp;
    private String latLong;
    private Long millisecondDate;
    private String image;
    private String organizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mContext = this;
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();
        mUId = mSharedPreferences.getString(Constants.KEY_UID, null);
        mUserRef = FirebaseDatabase.getInstance().getReference("users").child(mUId);

        mMakePlansButton.setOnClickListener(this);
        mViewEventsButton.setOnClickListener(this);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/bario.ttf");
        mTextView.setTypeface(myCustomFont);

        setTitle(null);
        setSupportActionBar(topToolBar);

        mFirebaseRef = FirebaseDatabase.getInstance().getReference();

        mSavedEventRef = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_URL_USER_EVENT + "/" + mUId);

        mSavedEventRefListener = mSavedEventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null) {
                    for(DataSnapshot eventSnapshot: dataSnapshot.getChildren()) {
                        final Event newEvent = eventSnapshot.getValue(Event.class);
                        eventName = newEvent.getName();
                        eventDate = newEvent.getDate();
                        eventTime = newEvent.getTime();
                        eventLocation = newEvent.getLocation();
                        eventId = newEvent.getPushId();
                        createEventTimestamp = newEvent.getCreateEventTimestamp();
                        latLong = newEvent.getLatLong();
                        millisecondDate = newEvent.getMillisecondDate();
                        image = newEvent.getImage();
                        organizer = newEvent.getOrganizer();

                        if(newEvent.getAlert()) {
                            new AlertDialog.Builder(mContext)
                                    .setCancelable(false)
                                    .setTitle("New Event Invite From " + organizer)
                                    .setMessage(eventName + "\n" + eventDate + " at " + eventTime + "\n" + eventLocation)
                                    .setPositiveButton("See Full Invitation", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            updateAlert();
                                            Intent intent = new Intent(MainActivity.this, SavedEventsActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton("View Later", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            updateAlert();
                                        }
                                    })
                                    .create()
                                    .show();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mUserRefListener = mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                mWelcomeTextView.setText(user.getName());
                addToSharedPreferences(user.getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSavedEventRef.removeEventListener(mSavedEventRefListener);
    }

    @Override
    public void onClick(View v) {
        if(v == mMakePlansButton) {
            mEditor.putString(Constants.PREFERENCES_CREATE_EVENT, "").apply();
            mEditor.putString(Constants.INVITEE_PHONE_NUMBERS, "").apply();
            Intent intent = new Intent(MainActivity.this, PlanActivity.class);
            startActivity(intent);
        }if (v == mViewEventsButton) {
            Intent intent = new Intent(MainActivity.this, SavedEventsActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_logout, menu);
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_add){
            mEditor.putString(Constants.PREFERENCES_CREATE_EVENT, "").apply();
            mEditor.putString(Constants.INVITEE_PHONE_NUMBERS, "").apply();
            Intent intent = new Intent(MainActivity.this, PlanActivity.class);
            startActivity(intent);
        }
        if(id == R.id.action_view){
            Intent intent = new Intent(MainActivity.this, SavedEventsActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        if(id == R.id.action_home) {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    protected void logout() {
        FirebaseAuth.getInstance().signOut();
        takeUserToLoginScreenOnUnAuth();
    }

    private void takeUserToLoginScreenOnUnAuth() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void addToSharedPreferences(String name) {
        mEditor.putString(Constants.KEY_USER_NAME, name).apply();
    }

    public void updateAlert() {
        DatabaseReference firebaseRef = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_URL_USER_EVENT);
        DatabaseReference eventListRef = firebaseRef.child(mUId);
        DatabaseReference eventRef = eventListRef.child(eventId);
        Map<String,Object> eventMap = new HashMap<String,Object>();
        eventMap.put("alert", "no");
        eventMap.put("createEventTimestamp", createEventTimestamp);
        eventMap.put("date", eventDate);
        eventMap.put("image", image);
        eventMap.put("latLong", latLong);
        eventMap.put("location", eventLocation);
        eventMap.put("millisecondDate", millisecondDate);
        eventMap.put("name", eventName);
        eventMap.put("organizer", organizer);
        eventMap.put("pushId", eventId);
        eventMap.put("time", eventTime);
        eventRef.updateChildren(eventMap);
        Log.d("updated", "event updated");
    }
}