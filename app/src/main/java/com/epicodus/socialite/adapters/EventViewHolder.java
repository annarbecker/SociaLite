package com.epicodus.socialite.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.socialite.R;
import com.epicodus.socialite.models.Event;
import com.epicodus.socialite.ui.EventDetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EventViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.eventImageView) ImageView mEventImageView;
    @BindView(R.id.eventNameTextView) TextView mNameTextView;
    @BindView(R.id.dateTextView) TextView mDateTextView;
    @BindView(R.id.timeTextView) TextView mTimeTextView;

    private Context mContext;
    private ArrayList<Event> mEvents = new ArrayList<>();


    public EventViewHolder(View itemView, ArrayList<Event> events) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = itemView.getContext();
        mEvents = events;

        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int itemPosition = getLayoutPosition();
                Intent intent = new Intent(mContext, EventDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("events", Parcels.wrap(mEvents));
                mContext.startActivity(intent);
            }
        });
    }

    public void bindEvent(Event event) {
        mNameTextView.setText(event.getName());
        mDateTextView.setText(event.getDate());
        mTimeTextView.setText(event.getTime());
        Picasso.with(mContext)
                .load(event.getImage())
                .resize(300, 300)
                .centerCrop()
                .into(mEventImageView);
    }
}

