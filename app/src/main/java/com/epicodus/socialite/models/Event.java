package com.epicodus.socialite.models;

import com.google.android.gms.maps.model.LatLng;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by arbecker on 4/28/16.
 */
@Parcel
public class Event {
    private String mName;
    private String mLocation;
    private String mDate;
    private String mTime;
    private ArrayList<String> mInvitees;
    private String mLatLong;

    public Event() {}

    public Event (String name, String location, String date, String time, ArrayList<String> invitees, String latLong) {
        this.mName = name;
        this.mLocation = location;
        this.mDate = date;
        this.mTime = time;
        this.mInvitees = invitees;
        this.mLatLong = latLong;
    }

    public String getName() {
        return mName;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getDate() {
        return mDate;
    }

    public String getTime() {
        return mTime;
    }

    public String getLatLong() {
        return mLatLong;
    }

    public void setLatLong(String mLatLong) {
        this.mLatLong = mLatLong;
    }



//    public ArrayList<Person> getInvitees() {
//        return mInvitees;
//    }

//    public void addInvitee(Person invitee) {
//        this.mInvitees.add(invitee);
//    }

}
