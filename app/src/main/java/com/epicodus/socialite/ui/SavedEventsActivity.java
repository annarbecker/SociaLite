package com.epicodus.socialite.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.epicodus.socialite.Constants;
import com.epicodus.socialite.R;
import com.epicodus.socialite.adapters.EventViewHolder;
import com.epicodus.socialite.adapters.FirebaseEventListAdapter;
import com.epicodus.socialite.models.Event;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedEventsActivity extends AppCompatActivity {
    private Query mQuery;
    private DatabaseReference mFirebaseEventsRef;
    private FirebaseEventListAdapter mAdapter;
    @BindView(R.id.toolbar) Toolbar topToolBar;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);
        ButterKnife.bind(this);

        setTitle(null);
        setSupportActionBar(topToolBar);

        mFirebaseEventsRef = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_URL_USER_EVENT);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        setUpFirebaseQuery();
        setUpRecyclerView();
    }


    private void setUpFirebaseQuery() {
        String userUid = mSharedPreferences.getString(Constants.KEY_UID, null);
        mQuery = mFirebaseEventsRef.child(userUid).orderByChild("dateInverse");
    }

    private void setUpRecyclerView() {
        mAdapter = new FirebaseEventListAdapter(Event.class, R.layout.event_list_item,
                EventViewHolder.class, mQuery, this.getApplicationContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
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
            Intent intent = new Intent(SavedEventsActivity.this, PlanActivity.class);
            startActivity(intent);
        }
        if(id == R.id.action_view){
            Intent intent = new Intent(SavedEventsActivity.this, SavedEventsActivity.class);
            startActivity(intent);
        }
        if(id == R.id.action_home) {
            Intent intent = new Intent(SavedEventsActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}