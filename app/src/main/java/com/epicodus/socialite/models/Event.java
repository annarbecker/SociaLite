package com.epicodus.socialite.models;


import org.parceler.Parcel;


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
    String alert;
    String organizer;

    public Event() {}

    public Event (String name, String location, String date, String time, String latLong, String image, Long millisecondDate, String createEventTimestamp, String alert) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.time = time;
        this.latLong = latLong;
        this.image = image;
        this.millisecondDate = millisecondDate;
        this.dateInverse = millisecondDate*-1;
        this.createEventTimestamp = createEventTimestamp;
        this.alert = alert;
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

    public String getPushId() {
        return pushId;
    }

    public String getCreateEventTimestamp() {
        return createEventTimestamp;
    }

    public String getAlert() {
        return alert;
    }

    public Long getDateInverse() {
        return dateInverse;
    }

    public Long getMillisecondDate() {
        return millisecondDate;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }
}
