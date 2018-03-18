package com.epicodus.socialite.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epicodus.socialite.R;
import com.epicodus.socialite.models.Event;
import com.epicodus.socialite.models.Person;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class FirebasePersonListAdapter extends FirebaseRecyclerAdapter<Person, PersonViewHolder> {
    private DatabaseReference mRef;
    private ChildEventListener mChildEventListener;
    private Context mContext;
    private ArrayList<Person> mPersons = new ArrayList<>();

    public FirebasePersonListAdapter(Class<Person> modelClass, int modelLayout,
                                     Class<PersonViewHolder> viewHolderClass, Query ref,
                                     Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);

        mRef = ref.getRef();
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mPersons.add(dataSnapshot.getValue(Person.class));
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

    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.person_list_item, parent, false);
        return new PersonViewHolder(view, mPersons);
    }

    @Override
    protected void populateViewHolder(final PersonViewHolder personViewHolder, Person person, int posiiton) {
        personViewHolder.bindPerson(person);
    }
}
