package com.example.biotracker;

public class User
{
    int id;
    String userName, userDob, userHName, userPlace, userPincode, userDistrict, userMobile, userEmail;

    public User(int id, String userName, String userDob, String userHName, String userPlace, String userPincode, String userDistrict, String userMobile, String userEmail) {
        this.id = id;
        this.userName = userName;
        this.userDob = userDob;
        this.userHName = userHName;
        this.userPlace = userPlace;
        this.userPincode = userPincode;
        this.userDistrict = userDistrict;
        this.userMobile = userMobile;
        this.userEmail = userEmail;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserDob() {
        return userDob;
    }

    public String getUserHName() {
        return userHName;
    }

    public String getUserPlace() {
        return userPlace;
    }

    public String getUserPincode() {
        return userPincode;
    }

    public String getUserDistrict() {
        return userDistrict;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
