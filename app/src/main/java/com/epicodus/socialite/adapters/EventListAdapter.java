//package com.epicodus.socialite.adapters;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.epicodus.socialite.R;
//import com.epicodus.socialite.models.Event;
//import com.epicodus.socialite.ui.EventDetailActivity;
//import com.squareup.picasso.Picasso;
//
//import org.parceler.Parcels;
//
//import java.util.ArrayList;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//
///**
// * Created by arbecker on 4/28/16.
// */
//public class EventListAdapter extends RecyclerView.Adapter<EventViewHolder> {
//    public static final String TAG = EventListAdapter.class.getSimpleName();
//    private ArrayList<Event> mEvents = new ArrayList<>();
//    private Context mContext;
//
//    public EventListAdapter(Context context, ArrayList<Event> events) {
//        mContext = context;
//        mEvents = events;
//    }
//
//    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item, parent, false);
//        EventViewHolder viewHolder = new EventViewHolder(view, mEvents);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(EventViewHolder holder, int position) {
//        holder.bindEvent(mEvents.get(position));
//    }
//
//    @Override
//    public int getItemCount() {
//        return mEvents.size();
//    }
//}