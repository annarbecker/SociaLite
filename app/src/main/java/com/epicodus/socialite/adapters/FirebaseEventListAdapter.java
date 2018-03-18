package com.epicodus.socialite.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epicodus.socialite.R;
import com.epicodus.socialite.models.Event;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;


public class FirebaseEventListAdapter extends FirebaseRecyclerAdapter<Event, EventViewHolder> {
    private DatabaseReference mRef;
    private ChildEventListener mChildEventListener;
    private Context mContext;
    private ArrayList<Event> mEvents = new ArrayList<>();

    public FirebaseEventListAdapter(Class<Event> modelClass, int modelLayout,
                                    Class<EventViewHolder> viewHolderClass, Query ref,
                                    Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);

        mRef = ref.getRef();
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mEvents.add(dataSnapshot.getValue(Event.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_item, parent, false);
        return new EventViewHolder(view, mEvents);
    }


    @Override
    protected void populateViewHolder(final EventViewHolder eventViewHolder, Event event, int posiiton) {
        eventViewHolder.bindEvent(event);
    }
}