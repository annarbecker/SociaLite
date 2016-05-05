package com.epicodus.socialite.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.epicodus.socialite.Constants;
import com.epicodus.socialite.R;
import com.epicodus.socialite.adapters.FirebasePersonListAdapter;
import com.epicodus.socialite.models.Event;
import com.epicodus.socialite.models.Person;
import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ConfirmActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = ConfirmActivity.class.getSimpleName();
    @Bind(R.id.imageView) ImageView mImage;
    @Bind(R.id.userEventTextView) TextView mUserEventTextView;
    @Bind(R.id.userLocationTextView) TextView mUserLocationTextView;
    @Bind(R.id.userDateTextView) TextView mUserDateTextView;
    @Bind(R.id.userTimeTextView) TextView mUserTimeTextView;
    @Bind(R.id.homeButton) Button mHomeButton;
    @Bind(R.id.toolbar) Toolbar topToolBar;
    @Bind(R.id.personRecyclerView) RecyclerView mRecyclerView;


    private String mLatLong;
    private Event newEvent;
    private String mLocation;

    private Query mQuery;
    private Firebase mFirebasePersonRef;
    private FirebasePersonListAdapter mAdapter;
    private Firebase mFirebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        ButterKnife.bind(this);

        setTitle(null);
        setSupportActionBar(topToolBar);

        newEvent = Parcels.unwrap(getIntent().getParcelableExtra("newEvent"));

        mUserEventTextView.setText(newEvent.getName());
        mUserLocationTextView.setText(newEvent.getLocation());
        mUserDateTextView.setText(newEvent.getDate());
        mUserTimeTextView.setText(newEvent.getTime());
        mLatLong = (newEvent.getLatLong());
        mLocation = (newEvent.getLocation());
        mHomeButton.setOnClickListener(this);
        mUserLocationTextView.setOnClickListener(this);

        Picasso.with(ConfirmActivity.this).load(newEvent.getImage()).into(mImage);

        mFirebasePersonRef = new Firebase(Constants.FIREBASE_URL_PERSON);
        mFirebaseRef = new Firebase(Constants.FIREBASE_URL);
        setUpFirebaseQuery();
        setUpRecyclerView();
    }

    @Override
    public void onClick(View v) {
        if (v == mHomeButton) {
            Intent intent = new Intent(ConfirmActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Toast.makeText(ConfirmActivity.this, "your event has been shared", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_add){
            Intent intent = new Intent(ConfirmActivity.this, PlanActivity.class);
            startActivity(intent);
        }
        if(id == R.id.action_view){
            Intent intent = new Intent(ConfirmActivity.this, SavedEventsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpFirebaseQuery() {
        mQuery = mFirebasePersonRef.orderByChild("event").equalTo(newEvent.getName());

    }

    private void setUpRecyclerView() {
        mAdapter = new FirebasePersonListAdapter(mQuery, Person.class);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }
}