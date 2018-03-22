package com.epicodus.socialite.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.socialite.Constants;
import com.epicodus.socialite.R;
import com.epicodus.socialite.models.Event;
import com.epicodus.socialite.models.Person;
import com.epicodus.socialite.models.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UserViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.nameTextView) TextView mNameTextView;
    @BindView(R.id.nameTextView2) CheckBox mNameCheckBox;

    private Context mContext;
    private ArrayList<User> mUsers = new ArrayList<>();
    private String name;
    private String event;
    private String rsvp;
    private String phone;
    private SharedPreferences mSharedPreferences;
    private String mEventCreatedDate;
    private String eventName;
    private String time;
    private String date;
    private String location;
    private String latLong;
    private String image;
    private Long millisecondDate;
    private String mCurrentUser;
    private String organizer;

    public UserViewHolder(final View itemView, ArrayList<User> users) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = itemView.getContext();
        mUsers = users;

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        mCurrentUser = mSharedPreferences.getString(Constants.KEY_UID, null);
        mEventCreatedDate = mSharedPreferences.getString(Constants.PREFERENCES_CREATE_EVENT, null);
        eventName = mSharedPreferences.getString(Constants.PREFERENCES_EVENT, null);
        time = mSharedPreferences.getString(Constants.PREFERENCES_TIME, null);
        date = mSharedPreferences.getString(Constants.PREFERENCES_DATE, null);
        location = mSharedPreferences.getString(Constants.PREFERENCES_LOCATION, null);
        latLong = mSharedPreferences.getString(Constants.PREFERENCES_LAT_LONG, null);
        image = mSharedPreferences.getString(Constants.PREFERENCES_IMAGE, null);
        millisecondDate = Long.valueOf(mSharedPreferences.getString(Constants.PREFERENCES_MILLISECOND_DATE, null));
        organizer = mSharedPreferences.getString(Constants.KEY_USER_NAME, null);
        mNameCheckBox.setVisibility(View.INVISIBLE);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = getLayoutPosition();
                name = mUsers.get(itemPosition).getName();
                phone = mUsers.get(itemPosition).getEmail();
                event = mEventCreatedDate;
                String uid = mUsers.get(itemPosition).getPushId();

                Person newContact = new Person(name, event);
                newContact.setPhone(phone);
                Toast.makeText(mContext, newContact.getName() + " added to your event", Toast.LENGTH_SHORT).show();

                DatabaseReference inviteeFirebaseRef = FirebaseDatabase.getInstance().getReference().child(event);
                DatabaseReference pushRef = inviteeFirebaseRef.push();
                String pushId = pushRef.getKey();
                newContact.setPushId(pushId);
                pushRef.setValue(newContact);

                if(mUsers.get(itemPosition).getPushId().equals(mCurrentUser)) {
                } else {
                    Event newEvent = new Event(eventName, location, date, time, latLong, image,
                            millisecondDate, mEventCreatedDate);
                    newEvent.setAlert(true);

                    DatabaseReference userEventsFirebaseRef = FirebaseDatabase.getInstance()
                            .getReference(Constants.FIREBASE_URL_USER_EVENT).child(uid);
                    DatabaseReference eventPushRef = userEventsFirebaseRef.push();
                    newEvent.setPushId(eventPushRef.getKey());
                    newEvent.setOrganizer(organizer);
                    eventPushRef.setValue(newEvent);
                }
            }
        });
    }

    public void bindUser(User user) {
        mNameCheckBox.setChecked(false);
        mNameTextView.setText(user.getName());
        mNameCheckBox.setText(user.getName());
    }
}