package com.epicodus.socialite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlanActivity extends AppCompatActivity {
    @Bind(R.id.eventEditText) EditText mEventEditText;
    @Bind(R.id.inviteeEditText) EditText mInviteeEditText;
    @Bind(R.id.inviteButton) Button mInviteButton;
    @Bind(R.id.locationEditText) EditText mLocationEditText;
    @Bind(R.id.dateEditText) EditText mDateEditText;
    @Bind(R.id.timeEditText) EditText mTimeEditText;
    @Bind(R.id.createButton) Button mCreateButton;

    private List<String> inviteeArray = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        ButterKnife.bind(this);

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String event = mEventEditText.getText().toString();
                String location = mLocationEditText.getText().toString();
                String date = mDateEditText.getText().toString();
                String time = mTimeEditText.getText().toString();
                Intent intent = new Intent(PlanActivity.this, ConfirmActivity.class);
                intent.putExtra("event", event);
                intent.putExtra("location", location);
                intent.putExtra("date", date);
                intent.putExtra("time", time);
                intent.putExtra("inviteeArray", TextUtils.join(", ", inviteeArray));
                startActivity(intent);
            }
        });

        mInviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String invitee = mInviteeEditText.getText().toString();
                inviteeArray.add(invitee);
                mInviteeEditText.setText("");
            }
        });
    }
}