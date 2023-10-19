package com.example.car_rent_yassine.model;

import java.io.Serializable;

public class Booking implements Serializable {
    String startdate,enddate;
    long duration;
    Car car;
    User user;
    String email;
    public Booking(String startdate, String enddate, long duration, Car car, String email) {
        this.startdate = startdate;
        this.enddate = enddate;
        this.duration = duration;
        this.car = car;
        this.email = email;
    }
    public Booking() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
