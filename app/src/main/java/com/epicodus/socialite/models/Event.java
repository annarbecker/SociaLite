package com.epicodus.socialite.models;

import java.util.ArrayList;

/**
 * Created by arbecker on 4/28/16.
 */

public class Event {
    private String mName;
    private String mLocation;
    private String mDate;
    private String mTime;
    private ArrayList<Person> mInvitees;


    public Event (String name, String location, String date, String time) {
        this.mName = name;
        this.mLocation = location;
        this.mDate = date;
        this.mTime = time;
        this.mInvitees = new ArrayList<>();
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

    public ArrayList<Person> getInvitees() {
        return mInvitees;
    }

    public void addInvitee(Person invitee) {
        this.mInvitees.add(invitee);
    }

}
