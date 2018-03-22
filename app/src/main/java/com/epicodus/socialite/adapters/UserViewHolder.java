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
    private String phone;
    private SharedPreferences mSharedPreferences;
    private String mCurrentUser;
    private String organizer;

    private Event event;

    public UserViewHolder(final View itemView, ArrayList<User> users, final Event event) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = itemView.getContext();
        mUsers = users;
        this.event = event;

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        mCurrentUser = mSharedPreferences.getString(Constants.KEY_UID, null);
        organizer = mSharedPreferences.getString(Constants.KEY_USER_NAME, null);
        mNameCheckBox.setVisibility(View.INVISIBLE);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = getLayoutPosition();
                name = mUsers.get(itemPosition).getName();
                phone = mUsers.get(itemPosition).getEmail();
                String eventTimestampString = Long.toString(UserViewHolder.this.event
                        .getCreateEventTimestamp());
                String uid = mUsers.get(itemPosition).getPushId();

                Person newContact = new Person(name, eventTimestampString);
                newContact.setPhone(phone);
                Toast.makeText(mContext, newContact.getName() + " added to your event",
                        Toast.LENGTH_SHORT).show();

                DatabaseReference inviteeFirebaseRef = FirebaseDatabase.getInstance().getReference()
                        .child(Long.toString(event.getCreateEventTimestamp()));
                DatabaseReference pushRef = inviteeFirebaseRef.push();
                String pushId = pushRef.getKey();
                newContact.setPushId(pushId);
                pushRef.setValue(newContact);

                if(mUsers.get(itemPosition).getPushId().equals(mCurrentUser)) {
                } else {
                    Event newEvent = new Event(event.getName(), event.getLocation(), event.getDate(),
                            event.getTime(), event.getLatLong(), event.getImage(),
                            event.getMillisecondDate());
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