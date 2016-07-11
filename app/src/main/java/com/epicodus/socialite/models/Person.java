package com.epicodus.socialite.models;


public class Person {
    String name;
    String phone;
    String event;
    String rsvp;
    String pushId;

    public Person(){}

    public Person(String name, String event, String rsvp) {
        this.name = name;
        this.event = event;
        this.rsvp = rsvp;
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

    public String getrsvp() {
        return rsvp;
    }

    public void setrsvp(String rsvp) {this.rsvp = rsvp;}

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

}
