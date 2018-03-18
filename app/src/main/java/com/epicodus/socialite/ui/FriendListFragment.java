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
import com.epicodus.socialite.adapters.FirebaseUserListAdapter;
import com.epicodus.socialite.adapters.UserViewHolder;
import com.epicodus.socialite.models.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FriendListFragment extends Fragment{
    private Query mQuery;
    private DatabaseReference mFirebaseFriendsRef;
    private FirebaseUserListAdapter mAdapter;
    @BindView(R.id.friendRecyclerView) RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseFriendsRef = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_URL_USERS_LIST);
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
        mQuery = FirebaseDatabase.getInstance().getReference(friend);
    }

    private void setUpRecyclerView() {
        mAdapter = new FirebaseUserListAdapter(User.class, R.layout.person_list_item,
                UserViewHolder.class, mQuery, this.getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }
}
