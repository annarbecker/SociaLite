package com.epicodus.socialite.adapters;

/**
 * Created by arbecker on 5/4/16.
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epicodus.socialite.R;
import com.epicodus.socialite.models.Person;
import com.epicodus.socialite.util.FirebaseRecyclerAdapter;
import com.firebase.client.Query;

public class FirebasePersonListAdapter extends FirebaseRecyclerAdapter<PersonViewHolder, Person> {

    public FirebasePersonListAdapter(Query query, Class<Person> itemClass) {
        super(query, itemClass);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.person_list_item, parent, false);
        return new PersonViewHolder(view, getItems());
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        holder.bindPerson(getItem(position));
    }

    @Override
    protected void itemAdded(Person item, String key, int position) {

    }

    @Override
    protected void itemChanged(Person oldItem, Person newItem, String key, int position) {

    }

    @Override
    protected void itemRemoved(Person item, String key, int position) {

    }

    @Override
    protected void itemMoved(Person item, String key, int oldPosition, int newPosition) {

    }
}
