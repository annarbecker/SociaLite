package com.epicodus.socialite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import butterknife.Bind;
import butterknife.ButterKnife;

public class ConfirmActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.userEventTextView) TextView mUserEventTextView;
    @Bind(R.id.userLocationTextView) TextView mUserLocationTextView;
    @Bind(R.id.userDateTextView) TextView mUserDateTextView;
    @Bind(R.id.userTimeTextView) TextView mUserTimeTextView;
    @Bind(R.id.listView) ListView mListView;
    @Bind(R.id.homeButton) Button mHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String event = intent.getStringExtra("event");
        String location = intent.getStringExtra("location");
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");
        mUserEventTextView.setText(event);
        mUserLocationTextView.setText(location);
        mUserDateTextView.setText(date);
        mUserTimeTextView.setText(time);

        mHomeButton.setOnClickListener(this);

        String inviteesString = intent.getStringExtra("inviteeArray");
        String[] inviteesList = inviteesString.split(", ");

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, inviteesList);
        mListView.setAdapter(adapter);
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
}