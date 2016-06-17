package com.epicodus.socialite.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.socialite.Constants;
import com.epicodus.socialite.R;
import com.epicodus.socialite.models.Person;
import com.epicodus.socialite.ui.ConfirmActivity;
import com.epicodus.socialite.ui.EventDetailFragment;
import com.epicodus.socialite.ui.SearchContactsActivity;
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
    @Bind(R.id.nameTextView2) TextView mNameTextView2;

    private Context mContext;
    private ArrayList<Person> mPersons = new ArrayList<>();
    private String name;


    public PersonViewHolder(View itemView, ArrayList<Person> persons) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = itemView.getContext();
        mPersons = persons;
        mNameTextView.setVisibility(View.INVISIBLE);



        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = getLayoutPosition();
                name = mPersons.get(itemPosition).getName();
                mPersons.get(itemPosition).confirmInvite();
                if(mPersons.get(itemPosition).getGoing()) {
                    Log.d("NEW CONTACT RSVPD", name);
                    //update record in firebase
                }

            }
        });


        if(mContext.getClass().getSimpleName().equals(ConfirmActivity.class.getSimpleName())) {
            itemView.setClickable(false);
            mNameTextView.setVisibility(View.VISIBLE);
        }
    }

    public void bindPerson(Person person) {
        mNameTextView.setText(person.getName());
        mNameTextView2.setText(person.getName());
    }


}