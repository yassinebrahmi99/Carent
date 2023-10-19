package com.example.adminappcarent.model;

import android.net.Uri;

import java.io.Serializable;

public class Car implements Serializable {
    private int price;
    private int seats;
    private String manufacturer;
    private String model;
    private boolean availability;
    private String vehicleImageURL;
    private int doors;
    private String transmission;
    String carID;


    public Car(String carID, int price, int seats, String manufacturer, String model, boolean availability, String vehicleImageURL, int doors, String transmission) {
        this.carID = carID;
        this.price = price;
        this.seats = seats;
        this.manufacturer = manufacturer;
        this.model = model;
        this.availability = availability;
        this.vehicleImageURL = vehicleImageURL;
        this.transmission = transmission;
        this.doors = doors;
    }
    public Car( ) {

    }
    public int getDoors() {
        return doors;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public int getPrice() {
        return price;
    }

    public int getSeats() {
        return seats;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public boolean isAvailability() {
        return availability;
    }

    public String getVehicleImageURL() {
        return vehicleImageURL;
    }

    public void setVehicleImageURL(String vehicleImageURL) {
        this.vehicleImageURL = vehicleImageURL;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

}
