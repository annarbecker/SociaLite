package com.epicodus.socialite.models;

import com.google.android.gms.maps.model.LatLng;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by arbecker on 4/28/16.
 */
@Parcel
public class Event {
    String name;
    String location;
    String date;
    String time;
    List<String> invitees;
    String latLong;
    String image;
    String pushId;
    Long millisecondDate;
    Long dateInverse;



    public Event() {}

    public Event (String name, String location, String date, String time, String latLong, String image, Long millisecondDate) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.time = time;
        this.latLong = latLong;
        this.image = image;
        this.millisecondDate = millisecondDate;
        this.dateInverse = millisecondDate*-1;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getLatLong() {
        return latLong;
    }

    public String getImage() {
        return image;
    }


//    public ArrayList<Person> getInvitees() {
//        return mInvitees;
//    }

//    public void addInvitee(Person invitee) {
//        this.mInvitees.add(invitee);
//    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public Long getMillisecondDate() {
        return millisecondDate;
    }

    public void setMillisecondDate(Long millisecondDate) {
        this.millisecondDate = millisecondDate;
    }

}
