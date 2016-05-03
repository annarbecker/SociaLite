package com.epicodus.socialite.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epicodus.socialite.R;
import com.epicodus.socialite.models.Event;
import com.epicodus.socialite.util.FirebaseRecyclerAdapter;
import com.firebase.client.Query;

/**
 * Created by arbecker on 5/2/16.
 */

public class FirebaseEventAdapter extends FirebaseRecyclerAdapter<EventViewHolder, Event> {

    public FirebaseEventAdapter(Query query, Class<Event> itemClass) {
        super(query, itemClass);
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_item, parent, false);
        return new EventViewHolder(view, getItems());
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        holder.bindEvent(getItem(position));
    }

    @Override
    protected void itemAdded(Event item, String key, int position) {

    }

    @Override
    protected void itemChanged(Event oldItem, Event newItem, String key, int position) {

    }

    @Override
    protected void itemRemoved(Event item, String key, int position) {

    }

    @Override
    protected void itemMoved(Event item, String key, int oldPosition, int newPosition) {

    }
}