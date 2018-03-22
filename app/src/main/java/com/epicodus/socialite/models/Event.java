package com.epicodus.socialite.models;


import org.parceler.Parcel;


@Parcel
public class Event {
    protected String name;
    protected String location;
    protected String date;
    protected String time;
    protected String latLong;
    protected String image;
    protected String pushId;
    protected long millisecondDate;
    protected long dateInverse;
    protected long createEventTimestamp = System.currentTimeMillis();
    protected boolean alert = false;
    protected String organizer;

    public Event() {}

    public Event(String name, String location, String date, String time, String latLong,
                  String image, Long millisecondDate, long createEventTimestamp) {
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

    public void setLatLong(String latLong) {
        this.latLong = latLong;
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

    public long getCreateEventTimestamp() {
        return createEventTimestamp;
    }

    public boolean getAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
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

    public void setMillisecondDate(Long millisecondDate) {
        this.millisecondDate = millisecondDate;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
