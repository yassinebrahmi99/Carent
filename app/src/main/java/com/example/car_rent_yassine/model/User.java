package com.example.car_rent_yassine.model;

public class User {

    String email;
    String name;
    String phonenumber;

    public User(String email, String name, String phonenumber) {
        this.email = email;
        this.name = name;
        this.phonenumber = phonenumber;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
