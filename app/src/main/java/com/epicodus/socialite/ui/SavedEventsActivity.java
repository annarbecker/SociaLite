package com.epicodus.socialite.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.epicodus.socialite.Constants;
import com.epicodus.socialite.R;
import com.epicodus.socialite.adapters.FirebaseEventListAdapter;
import com.epicodus.socialite.models.Event;
import com.firebase.client.Firebase;
import com.firebase.client.Query;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SavedEventsActivity extends AppCompatActivity {
    private Query mQuery;
    private Firebase mFirebaseRestaurantsRef;
    private FirebaseEventListAdapter mAdapter;
    @Bind(R.id.toolbar) Toolbar topToolBar;
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);
        ButterKnife.bind(this);

        setTitle(null);
        setSupportActionBar(topToolBar);

        mFirebaseRestaurantsRef = new Firebase(Constants.FIREBASE_URL_EVENT);

        setUpFirebaseQuery();
        setUpRecyclerView();
    }

    private void setUpFirebaseQuery() {
        String location = mFirebaseRestaurantsRef.toString();
        mQuery = new Firebase(location);
    }

    private void setUpRecyclerView() {
        mAdapter = new FirebaseEventListAdapter(mQuery, Event.class);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
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
            Intent intent = new Intent(SavedEventsActivity.this, PlanActivity.class);
            startActivity(intent);
        }
        if(id == R.id.action_view){
            Intent intent = new Intent(SavedEventsActivity.this, SavedEventsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}