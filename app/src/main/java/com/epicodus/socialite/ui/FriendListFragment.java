package com.epicodus.socialite.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epicodus.socialite.Constants;
import com.epicodus.socialite.R;
import com.epicodus.socialite.adapters.FirebasePersonListAdapter;
import com.epicodus.socialite.adapters.FirebaseUserListAdapter;
import com.epicodus.socialite.models.Person;
import com.epicodus.socialite.models.User;
import com.firebase.client.Firebase;
import com.firebase.client.Query;

import butterknife.Bind;
import butterknife.ButterKnife;


public class FriendListFragment extends Fragment{
    private Query mQuery;
    private Firebase mFirebaseFriendsRef;
    private FirebaseUserListAdapter mAdapter;
    @Bind(R.id.friendRecyclerView) RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseFriendsRef = new Firebase(Constants.FIREBASE_URL_USERS_LIST);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_list, container, false);
        ButterKnife.bind(this, view);

        setUpFirebaseQuery();
        setUpRecyclerView();

        return view;
    }

    private void setUpFirebaseQuery() {
        String friend = mFirebaseFriendsRef.toString();
        mQuery = new Firebase(friend);
    }

    private void setUpRecyclerView() {
        mAdapter = new FirebaseUserListAdapter(mQuery, User.class);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }
}
