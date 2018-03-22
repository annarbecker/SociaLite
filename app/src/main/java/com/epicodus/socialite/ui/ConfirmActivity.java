package com.epicodus.socialite.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;

import com.epicodus.socialite.Constants;
import com.epicodus.socialite.R;
import com.epicodus.socialite.adapters.FirebasePersonListAdapter;
import com.epicodus.socialite.adapters.PersonViewHolder;
import com.epicodus.socialite.models.Event;
import com.epicodus.socialite.models.Person;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfirmActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = ConfirmActivity.class.getSimpleName();
    @BindView(R.id.imageView) ImageView mImage;
    @BindView(R.id.userEventTextView) TextView mUserEventTextView;
    @BindView(R.id.userLocationTextView) TextView mUserLocationTextView;
    @BindView(R.id.userDateTextView) TextView mUserDateTextView;
    @BindView(R.id.userTimeTextView) TextView mUserTimeTextView;
    @BindView(R.id.toolbar) Toolbar topToolBar;
    @BindView(R.id.personRecyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.fab) FloatingActionButton mShareButton;

    private String mLatLong;
    private Event newEvent;
    private String mLocation;
    private Query mQuery;
    private DatabaseReference mFirebasePersonRef;
    private FirebasePersonListAdapter mAdapter;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        ButterKnife.bind(this);

        setTitle(null);
        setSupportActionBar(topToolBar);

        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/bario.ttf");
        mUserEventTextView.setTypeface(myCustomFont);


        newEvent = Parcels.unwrap(getIntent().getParcelableExtra("newEvent"));

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        mUserEventTextView.setText(newEvent.getName());
        mUserLocationTextView.setText(newEvent.getLocation());
        mUserDateTextView.setText(newEvent.getDate());
        mUserTimeTextView.setText(newEvent.getTime());
        mLatLong = (newEvent.getLatLong());
        mLocation = (newEvent.getLocation());
        mShareButton.setOnClickListener(this);
        mUserLocationTextView.setOnClickListener(this);

        userName = mSharedPreferences.getString(Constants.KEY_USER_NAME, null);

        Picasso.with(ConfirmActivity.this).load(newEvent.getImage()).into(mImage);

        String eventTimestampString = Long.toString(newEvent.getCreateEventTimestamp());
        mFirebasePersonRef = FirebaseDatabase.getInstance().getReference(eventTimestampString);
        setUpFirebaseQuery();
        setUpRecyclerView();
    }

    @Override
    public void onClick(View v) {
        if (v == mShareButton) {
            new AlertDialog.Builder(this)
                    .setTitle("Share This Event")
                    .setMessage("SociaLite users will receive invitations in their account\n\nFriends without SociaLite will receive invitations via text")
                    .setPositiveButton("Send SociaLite & Text Invitations", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intentHome = new Intent(ConfirmActivity.this, MainActivity.class);
                            startActivity(intentHome);

                            String message = userName + " invited you to: \n" + newEvent.getName() + "\n" + newEvent.getDate() + " at " + newEvent.getTime() + "\n" +newEvent.getLocation() + "\n \n Invite sent via SociaLite";
                            String phoneNumbers = mSharedPreferences.getString(Constants.INVITEE_PHONE_NUMBERS, null);
                            Uri smsUri = Uri.parse("smsto:" + phoneNumbers);
                            Intent intent = new Intent(Intent.ACTION_SENDTO, smsUri);
                            intent.putExtra("sms_body", message);

                            if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivity(intent);
                            }
                        }
                    })
                    .setNeutralButton("Just Send SociaLite Invitations", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intentHome = new Intent(ConfirmActivity.this, MainActivity.class);
                            intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intentHome);
                        }
                    })
                    .create()
                    .show();
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
            mEditor.putString(Constants.PREFERENCES_EVENT, "").apply();
            mEditor.putString(Constants.PREFERENCES_DATE, "").apply();
            mEditor.putString(Constants.PREFERENCES_TIME, "").apply();
            mEditor.putString(Constants.PREFERENCES_LOCATION, "").apply();
            mEditor.putString(Constants.PREFERENCES_LAT_LONG, "").apply();
            mEditor.putString(Constants.PREFERENCES_CREATE_EVENT, "").apply();
            mEditor.putString(Constants.PREFERENCES_IMAGE, "").apply();
            mEditor.putString(Constants.PREFERENCES_MILLISECOND_DATE, "").apply();
            mEditor.putString(Constants.INVITEE_PHONE_NUMBERS, "").apply();

            Intent intent = new Intent(ConfirmActivity.this, PlanActivity.class);
            startActivity(intent);
        }
        if(id == R.id.action_view){
            Intent intent = new Intent(ConfirmActivity.this, SavedEventsActivity.class);
            startActivity(intent);
        }
        if(id == R.id.action_home) {
            Intent intent = new Intent(ConfirmActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpFirebaseQuery() {
        mQuery = mFirebasePersonRef;
    }

    private void setUpRecyclerView() {
        mAdapter = new FirebasePersonListAdapter(Person.class, R.id.personRecyclerView,
                PersonViewHolder.class, mQuery, this.getApplicationContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }
}