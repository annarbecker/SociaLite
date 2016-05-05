package com.epicodus.socialite.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.socialite.R;
import com.epicodus.socialite.models.Person;

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


    public PersonViewHolder(View itemView, ArrayList<Person> persons) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = itemView.getContext();
        mPersons = persons;

    }

    public void bindPerson(Person person) {
        mNameTextView.setText(person.getName());
    }
}