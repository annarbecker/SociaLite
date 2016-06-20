package com.epicodus.socialite.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ConfirmActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = ConfirmActivity.class.getSimpleName();
    @Bind(R.id.imageView) ImageView mImage;
    @Bind(R.id.userEventTextView) TextView mUserEventTextView;
    @Bind(R.id.userLocationTextView) TextView mUserLocationTextView;
    @Bind(R.id.userDateTextView) TextView mUserDateTextView;
    @Bind(R.id.userTimeTextView) TextView mUserTimeTextView;
    @Bind(R.id.shareButton) Button mShareButton;
    @Bind(R.id.toolbar) Toolbar topToolBar;
    @Bind(R.id.personRecyclerView) RecyclerView mRecyclerView;
//    @Bind(R.id.screenImageView) ImageView mScreenImageView;
//    @Bind(R.id.capture_screen_shot) Button mTakeScreenShotButton;


    private String mLatLong;
    private Event newEvent;
    private String mLocation;
    Bitmap mbitmap;

    private Query mQuery;
    private Firebase mFirebasePersonRef;
    private FirebasePersonListAdapter mAdapter;
    private Firebase mFirebaseRef;
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

        mFirebasePersonRef = new Firebase(Constants.FIREBASE_URL_PERSON  + "/" + newEvent.getCreateEventTimestamp());
        mFirebaseRef = new Firebase(Constants.FIREBASE_URL);
        setUpFirebaseQuery();
        setUpRecyclerView();
    }

    //.setMessage("SociaLite friends will receive in app invitations\n\nFriends without the app will be sent an invite via text and link to download SociaLite")

//    public void screenShot(View view) {
//        mbitmap = getBitmapOFRootView(mTakeScreenShotButton);
//        mScreenImageView.setImageBitmap(mbitmap);
//        createImage(mbitmap);
//    }
//
//    public Bitmap getBitmapOFRootView(View v) {
//        View rootview = v.getRootView();
//        rootview.setDrawingCacheEnabled(true);
//        Bitmap bitmap1 = rootview.getDrawingCache();
//        return bitmap1;
//    }
//
//    public void createImage(Bitmap bmp) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        bmp.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
//        File file = new File(Environment.getExternalStorageDirectory() +
//                "/capturedscreenandroid.jpg");
//        Log.d("image file", file+"");
//        try {
//            file.createNewFile();
//            FileOutputStream outputStream = new FileOutputStream(file);
//            outputStream.write(bytes.toByteArray());
//            outputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }



    @Override
    public void onClick(View v) {
        if (v == mShareButton) {
            new AlertDialog.Builder(this)
                    .setTitle("Share This Event")
                    .setMessage("Friends will receive their invitation via text")
                    .setPositiveButton("Send Invitation", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intentHome = new Intent(ConfirmActivity.this, MainActivity.class);
                            intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intentHome);

                            String message = userName + " invited you to: \n" + newEvent.getName() + "\n" + newEvent.getDate() + " at " + newEvent.getTime() + "\n" +newEvent.getLocation() + "\n \n Download SociaLite at: www.google.com";
                            String phoneNumbers = mSharedPreferences.getString(Constants.INVITEE_PHONE_NUMBERS, null);
                            Log.v("phoneNumbers", phoneNumbers);
                            Uri smsUri = Uri.parse("smsto:" + phoneNumbers);
                            Intent intent = new Intent(Intent.ACTION_SENDTO, smsUri);
                            intent.putExtra("sms_body", message);

                            if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivity(intent);
                            }
                        }
                    })
                    .setNeutralButton("Don't Send", new DialogInterface.OnClickListener() {
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
            mEditor.putString(Constants.INVITEE_PHONE_NUMBERS, "").apply();
            mEditor.putString(Constants.PREFERENCES_CREATE_EVENT, "").apply();
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
        mAdapter = new FirebasePersonListAdapter(mQuery, Person.class);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }
}