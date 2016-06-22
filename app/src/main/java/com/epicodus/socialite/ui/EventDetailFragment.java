package com.epicodus.socialite.ui;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.socialite.Constants;
import com.epicodus.socialite.R;
import com.epicodus.socialite.adapters.FirebasePersonListAdapter;
import com.epicodus.socialite.models.Event;
import com.epicodus.socialite.models.Person;
import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;


public class EventDetailFragment extends Fragment implements View.OnClickListener{
    @Bind(R.id.eventImageView) ImageView mImageLabel;
    @Bind(R.id.eventNameTextView) TextView mNameLabel;
    @Bind(R.id.organizerTextView) TextView mOrganizerLabel;
    @Bind(R.id.dateTextView) TextView mDateLabel;
    @Bind(R.id.timeTextView) TextView mTimeLabel;
    @Bind(R.id.addressTextView) TextView mAddressLabel;
    @Bind(R.id.personRecyclerView) RecyclerView mRecyclerView;

    private Event mEvent;
    private Context mContext;
    private Query mQuery;
    private Firebase mFirebasePersonRef;
    private FirebasePersonListAdapter mAdapter;

    public static EventDetailFragment newInstance(Event event) {
        EventDetailFragment eventDetailFragment = new EventDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("event", Parcels.wrap(event));
        eventDetailFragment.setArguments(args);
        return eventDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEvent = Parcels.unwrap(getArguments().getParcelable("event"));
        mContext = getContext();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_event_detail, container, false);
        ButterKnife.bind(this, view);

        Typeface myCustomFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/bario.ttf");

        mNameLabel.setTypeface(myCustomFont);
        mNameLabel.setText(mEvent.getName());
        mOrganizerLabel.setText("Event Organizer: " +mEvent.getOrganizer());
        mDateLabel.setText(mEvent.getDate());
        mTimeLabel.setText(mEvent.getTime());
        mAddressLabel.setText(mEvent.getLocation());
        Picasso.with(mContext)
                .load(mEvent.getImage())
                .resize(400, 300)
                .centerCrop()
                .into(mImageLabel);

        mAddressLabel.setOnClickListener(this);

        mFirebasePersonRef = new Firebase(Constants.FIREBASE_URL + "/" + mEvent.getCreateEventTimestamp());
        setUpFirebaseQuery();
        setUpRecyclerView();

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v == mAddressLabel) {
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + mEvent.getLatLong() + "?q=(" + mEvent.getLocation() + ")"));
            startActivity(mapIntent);
        }
    }

    private void setUpFirebaseQuery() {
        mQuery = mFirebasePersonRef;
    }

    private void setUpRecyclerView() {
        mAdapter = new FirebasePersonListAdapter(mQuery, Person.class);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }
}
