package com.epicodus.socialite.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.socialite.Constants;
import com.epicodus.socialite.R;
import com.epicodus.socialite.models.Person;
import com.firebase.client.Firebase;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by arbecker on 5/4/16.
 */
public class PersonViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.nameTextView) TextView mNameTextView;

    private Context mContext;
    private ArrayList<Person> mPersons = new ArrayList<>();
    private SharedPreferences mSharedPreferences;

    private String name;
    private String email;
    private String event;


    public PersonViewHolder(View itemView, ArrayList<Person> persons) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = itemView.getContext();
        mPersons = persons;

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        event = mSharedPreferences.getString(Constants.PREFERENCES_EVENT, null);

        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int itemPosition = getLayoutPosition();
                    name = mPersons.get(itemPosition).getName();
                    email = mPersons.get(itemPosition).getEmail();

                Person newContact = new Person(name, email, event);
                Log.d("NEW CONTACT ADDED", newContact.getName());

                Firebase ref = new Firebase(Constants.FIREBASE_URL_PERSON);
                ref.push().setValue(newContact);
            }
        });

    }

    public void bindPerson(Person person) {
        mNameTextView.setText(person.getName());
    }


}