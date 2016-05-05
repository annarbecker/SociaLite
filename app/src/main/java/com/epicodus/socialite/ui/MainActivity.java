package com.epicodus.socialite.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.socialite.Constants;
import com.epicodus.socialite.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.makePlansButton) Button mMakePlansButton;
    @Bind(R.id.textView) TextView mTextView;
    @Bind(R.id.viewEventsButton) Button mViewEventsButton;
    @Bind(R.id.toolbar) Toolbar topToolBar;

    private Firebase mSavedEventRef;
    private Firebase mFirebaseRef;
    private ValueEventListener mSavedEventRefListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mMakePlansButton.setOnClickListener(this);
        mViewEventsButton.setOnClickListener(this);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/bario.ttf");
        mTextView.setTypeface(myCustomFont);

        setTitle(null);
        setSupportActionBar(topToolBar);

        mSavedEventRef = new Firebase(Constants.FIREBASE_URL_EVENT);
        mFirebaseRef = new Firebase(Constants.FIREBASE_URL);

        mSavedEventRefListener = mSavedEventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String events = dataSnapshot.getValue().toString();
                Log.d("Events added", events);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

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
        return super.onOptionsItemSelected(item);
    }

    protected void logout() {
        mFirebaseRef.unauth();
        takeUserToLoginScreenOnUnAuth();
    }

    private void takeUserToLoginScreenOnUnAuth() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}