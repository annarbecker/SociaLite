package com.epicodus.socialite.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epicodus.socialite.R;
import com.epicodus.socialite.models.User;
import com.epicodus.socialite.util.FirebaseRecyclerAdapter;
import com.firebase.client.Query;

/**
 * Created by arbecker on 6/20/16.
 */
public class FirebaseUserListAdapter extends FirebaseRecyclerAdapter<UserViewHolder, User> {

    public FirebaseUserListAdapter(Query query, Class<User> itemClass) {
        super(query, itemClass);
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.person_list_item, parent, false);
        return new UserViewHolder(view, getItems());
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.bindUser(getItem(position));
    }

    @Override
    protected void itemAdded(User item, String key, int position) {

    }

    @Override
    protected void itemChanged(User oldItem, User newItem, String key, int position) {

    }

    @Override
    protected void itemRemoved(User item, String key, int position) {

    }

    @Override
    protected void itemMoved(User item, String key, int oldPosition, int newPosition) {

    }
}
