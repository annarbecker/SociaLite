package com.epicodus.socialite.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.socialite.R;
import com.epicodus.socialite.models.Event;
import com.epicodus.socialite.ui.EventDetailActivity;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by arbecker on 4/28/16.
 */
public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder> {
    public static final String TAG = EventListAdapter.class.getSimpleName();
    private ArrayList<Event> mEvents = new ArrayList<>();
    private Context mContext;

    public EventListAdapter(Context context, ArrayList<Event> events) {
        mContext = context;
        mEvents = events;
    }

    @Override
    public EventListAdapter.EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item, parent, false);
        EventViewHolder viewHolder = new EventViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EventListAdapter.EventViewHolder holder, int position) {
        holder.bindEvent(mEvents.get(position));
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }
    public class EventViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.eventImageView) ImageView mEventImageView;
        @Bind(R.id.eventNameTextView) TextView mNameTextView;
        @Bind(R.id.dateTextView) TextView mDateTextView;
        @Bind(R.id.timeTextView) TextView mTimeTextView;
        private Context mContext;


        public EventViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();

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
        }
    }
}