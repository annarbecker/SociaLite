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

    public Event(Event event) {
        this.name = event.getName();
        this.location = event.getLocation();
        this.date = event.getDate();
        this.time = event.getTime();
        this.latLong = event.getLatLong();
        this.image = event.getImage();
        this.pushId = event.getPushId();
        this.millisecondDate = event.getMillisecondDate();
        this.dateInverse = event.getDateInverse();
        this.createEventTimestamp = event.getCreateEventTimestamp();
        this.alert = event.getAlert();
        this.organizer = event.getOrganizer();
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
