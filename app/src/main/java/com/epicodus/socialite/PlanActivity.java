package com.epicodus.socialite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlanActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = PlanActivity.class.getSimpleName();
    @Bind(R.id.eventEditText) EditText mEventEditText;
    @Bind(R.id.inviteeEditText) EditText mInviteeEditText;
    @Bind(R.id.inviteButton) Button mInviteButton;
    @Bind(R.id.dateEditText) EditText mDateEditText;
    @Bind(R.id.timeEditText) EditText mTimeEditText;
    @Bind(R.id.createButton) Button mCreateButton;
    @Bind(R.id.pickLocationButton) Button mPickLocationButton;
    @Bind(R.id.myLocation) AutoCompleteTextView mMyLocation;


    private List<String> inviteeArray = new ArrayList<String>();
    private String latLang;

    private PlacePicker.IntentBuilder mBuilder;
    private static final int PLACE_PICKER_FLAG = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        ButterKnife.bind(this);

        mCreateButton.setOnClickListener(this);
        mInviteButton.setOnClickListener(this);
        mPickLocationButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mCreateButton) {
            String event = mEventEditText.getText().toString();
            String location = mMyLocation.getText().toString();
            String date = mDateEditText.getText().toString();
            String time = mTimeEditText.getText().toString();
            Intent intent = new Intent(PlanActivity.this, ConfirmActivity.class);
            intent.putExtra("event", event);
            intent.putExtra("location", location);
            intent.putExtra("date", date);
            intent.putExtra("time", time);
            intent.putExtra("inviteeArray", TextUtils.join(", ", inviteeArray));
            intent.putExtra("latLang", latLang);
            startActivity(intent);
        } if(v == mInviteButton) {
            String invitee = mInviteeEditText.getText().toString();
            inviteeArray.add(invitee);
            mInviteeEditText.setText("");


        } if(v == mPickLocationButton) {
                try {
                    mBuilder = new PlacePicker.IntentBuilder();
                    Intent intent = mBuilder.build(PlanActivity.this);
                    // Start the Intent by requesting a result, identified by a request code.
                    startActivityForResult(intent, PLACE_PICKER_FLAG);

                } catch (GooglePlayServicesRepairableException e) {
                    GooglePlayServicesUtil
                            .getErrorDialog(e.getConnectionStatusCode(), PlanActivity.this, 0);
                } catch (GooglePlayServicesNotAvailableException e) {
                    Toast.makeText(PlanActivity.this, "Google Play Services is not available.",
                            Toast.LENGTH_LONG)
                            .show();
                }

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PLACE_PICKER_FLAG:
                    Place place = PlacePicker.getPlace(data, this);
                    latLang = place.getLatLng().toString();
                    mMyLocation.setVisibility(View.VISIBLE);
                    mMyLocation.setText(place.getName() + ", " + place.getAddress());
                    Log.i(TAG, latLang);
                    break;
            }
        }
    }
}
