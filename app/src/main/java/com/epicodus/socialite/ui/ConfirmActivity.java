package com.epicodus.socialite.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.epicodus.socialite.R;
import com.epicodus.socialite.models.Event;
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
    @Bind(R.id.listView) ListView mListView;
    @Bind(R.id.homeButton) Button mHomeButton;
    @Bind(R.id.toolbar) Toolbar topToolBar;

    private String mLatLong;
    private Event newEvent;
    private String mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        ButterKnife.bind(this);

        setTitle(null);
        setSupportActionBar(topToolBar);
        topToolBar.setLogoDescription(getResources().getString(R.string.logo_desc));

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

//        String inviteesString = intent.getStringExtra("inviteeArray");
//        String[] inviteesList = inviteesString.split(", ");
//
//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, inviteesList);
//        mListView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v == mHomeButton) {
            Intent intent = new Intent(ConfirmActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Toast.makeText(ConfirmActivity.this, "your event has been shared", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
        if(v == mUserLocationTextView) {
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + mLatLong + "?q=(" + newEvent.getLocation() + ")"));
            startActivity(mapIntent);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_add){
            Intent intent = new Intent(ConfirmActivity.this, ConfirmActivity.class);
            startActivity(intent);
            Toast.makeText(ConfirmActivity.this, "Add", Toast.LENGTH_LONG).show();
        }
        if(id == R.id.action_view){
            Intent intent = new Intent(ConfirmActivity.this, EventListActivity.class);
            startActivity(intent);
            Toast.makeText(ConfirmActivity.this, "View", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}