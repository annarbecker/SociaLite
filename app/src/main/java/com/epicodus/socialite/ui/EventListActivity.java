//package com.epicodus.socialite.ui;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.Toolbar;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.Toast;
//
//import com.epicodus.socialite.R;
//import com.epicodus.socialite.adapters.EventListAdapter;
//import com.epicodus.socialite.models.Event;
//
//import java.util.ArrayList;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//
///**
// * Created by arbecker on 4/28/16.
// */
//
//public class EventListActivity extends AppCompatActivity {
//    public static final String TAG = EventListActivity.class.getSimpleName();
//    public ArrayList<Event> mEvents = new ArrayList<>();
//    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
//    @Bind(R.id.toolbar) Toolbar topToolBar;
//    private EventListAdapter mAdapter;
//
////    String[] allEventNames = new String[] {"Movie Night", "Beyonce Concert", "Brunch", "Hiking"};
////    String[] allEventDates = new String[] {"Tuesday, May 2, 2016", "Wednesday, May 18, 2016", "Sunday, May 8, 2016", "Friday, May 15, 2016"};
////    String[] allEventTimes = new String[] {"8:00pm", "4:00pm", "12:00pm", "3:30pm"};
////    String[] allEventLocations = new String[] {"2284 NW Everett Street, Portland, OR 97203", "400 SW 6th Avenue, Portland, OR 97204", "10750 SW Wedgewood Street, Portland, OR 97225", "50000 Historic Columbia River Highway, Bridal Veil, OR 97010"};
////    String[] allEventImages = new String[] {"https://images.unsplash.com/photo-1440404653325-ab127d49abc1?crop=entropy&fit=crop&fm=jpg&h=825&ixjsv=2.1.0&ixlib=rb-0.3.5&q=80&w=875", "https://images.unsplash.com/photo-1459749411175-04bf5292ceea?crop=entropy&fit=crop&fm=jpg&h=825&ixjsv=2.1.0&ixlib=rb-0.3.5&q=80&w=875", "https://images.unsplash.com/photo-1450152021501-598b36b17449?crop=entropy&fit=crop&fm=jpg&h=825&ixjsv=2.1.0&ixlib=rb-0.3.5&q=80&w=875", "https://images.unsplash.com/photo-1440431261965-1df24284dcff?crop=entropy&fit=crop&fm=jpg&h=825&ixjsv=2.1.0&ixlib=rb-0.3.5&q=80&w=875"};
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_events_list);
//        ButterKnife.bind(this);
//
//        setTitle(null);
//        setSupportActionBar(topToolBar);
//
////        for (int i =0; i<allEventNames.length; i++) {
////            Event event = new Event();
////            event.setName(allEventNames[i]);
////            event.setDate(allEventDates[i]);
////            event.setTime(allEventTimes[i]);
////            event.setLocation(allEventLocations[i]);
////            event.setImage(allEventImages[i]);
////            mEvents.add(event);
////        }
//
//        RecyclerView.LayoutManager layoutManager =
//                new LinearLayoutManager(EventListActivity.this);
//        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setHasFixedSize(true);
//        mAdapter = new EventListAdapter(this, mEvents);
//        mRecyclerView.setAdapter(mAdapter);
//    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if(id == R.id.action_add){
//            Intent intent = new Intent(EventListActivity.this, PlanActivity.class);
//            startActivity(intent);
//        }
//        if(id == R.id.action_view){
//            Intent intent = new Intent(EventListActivity.this, EventListActivity.class);
//            startActivity(intent);
//        }
//        return super.onOptionsItemSelected(item);
//    }
//}
//
//
//
