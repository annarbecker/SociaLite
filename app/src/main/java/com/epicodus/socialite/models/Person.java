package com.epicodus.socialite.models;


public class Person {
    private String name;
    private String phone;
    private String event;
    private boolean rsvp = false;
    private String pushId;

    public Person(){}

    public Person(String name, String event) {
        this.name = name;
        this.event = event;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEvent() {
        return event;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean getRsvp() {
        return rsvp;
    }

    public void setRsvp(boolean rsvp) {this.rsvp = rsvp;}

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

}
