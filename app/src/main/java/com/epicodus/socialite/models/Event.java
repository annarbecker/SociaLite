package com.epicodus.socialite.models;


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
    String latLong;
    String image;
    String pushId;
    Long millisecondDate;
    Long dateInverse;
    String createEventTimestamp;


    public Event() {}

    public Event (String name, String location, String date, String time, String latLong, String image, Long millisecondDate, String createEventTimestamp) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.time = time;
        this.latLong = latLong;
        this.image = image;
        this.millisecondDate = millisecondDate;
        this.dateInverse = millisecondDate*-1;
        this.createEventTimestamp = createEventTimestamp;
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

    public void setName(String name) {
        this.name = name;
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

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getCreateEventTimestamp() {
        return createEventTimestamp;
    }
}
