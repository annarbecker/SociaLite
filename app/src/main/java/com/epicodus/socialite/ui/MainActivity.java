package com.epicodus.socialite.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
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
import com.epicodus.socialite.models.User;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = MainActivity.class.getSimpleName();
    @Bind(R.id.makePlansButton) Button mMakePlansButton;
    @Bind(R.id.textView) TextView mTextView;
    @Bind(R.id.viewEventsButton) Button mViewEventsButton;
    @Bind(R.id.toolbar) Toolbar topToolBar;
    @Bind(R.id.welcomeTextView)TextView mWelcomeTextView;

    private Firebase mSavedEventRef;
    private Firebase mFirebaseRef;
    private ValueEventListener mSavedEventRefListener;
    private String mUId;
    private Firebase mUserRef;
    private SharedPreferences mSharedPreferences;
    private ValueEventListener mUserRefListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUId = mSharedPreferences.getString(Constants.KEY_UID, null);
        mUserRef = new Firebase(Constants.FIREBASE_URL_USERS).child(mUId);

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
                if(dataSnapshot.getValue() != null) {
                    String events = dataSnapshot.getValue().toString();
                    Log.d("Events added", events);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mUserRefListener = mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                mWelcomeTextView.setText(user.getName());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d(TAG, "Read failed");
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
        if(id == R.id.action_home) {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
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