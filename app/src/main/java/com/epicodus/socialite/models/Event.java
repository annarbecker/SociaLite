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
    private String mImage;

    public Event() {}

    public Event (String name, String location, String date, String time, ArrayList<String> invitees, String latLong, String image) {
        this.mName = name;
        this.mLocation = location;
        this.mDate = date;
        this.mTime = time;
        this.mInvitees = invitees;
        this.mLatLong = latLong;
        this.mImage = image;
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

    public String getImage() {
        return mImage;
    }


//    public ArrayList<Person> getInvitees() {
//        return mInvitees;
//    }

//    public void addInvitee(Person invitee) {
//        this.mInvitees.add(invitee);
//    }

    public void setName(String name) {
        this.mName = name;
    }

    public void setDate(String date) {
        this.mDate = date;
    }

    public void setTime(String time) {
        this.mTime = time;
    }

    public void setLocation(String location) {
        this.mLocation = location;
    }

    public void setImage(String image) {
        this.mImage = image;
    }



}
