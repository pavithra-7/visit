package com.example.visit.model;

public class UsersModel {

    private String depID;
    private String name;
    private String state;
    private String city;
    private String dist;
    private String address;
    private String whomtoMeet;
    private String purposetoMeet;
    private String mobile;
    private String email;
    private String profile;

    public UsersModel() {
    }

    public UsersModel(String depID, String name, String state, String city, String dist, String address, String whomtoMeet, String purposetoMeet, String mobile, String email, String profile) {
        this.depID = depID;
        this.name = name;
        this.state = state;
        this.city = city;
        this.dist = dist;
        this.address = address;
        this.whomtoMeet = whomtoMeet;
        this.purposetoMeet = purposetoMeet;
        this.mobile = mobile;
        this.email = email;
        this.profile = profile;
    }

    public String getDepID() {
        return depID;
    }

    public void setDepID(String depID) {
        this.depID = depID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWhomtoMeet() {
        return whomtoMeet;
    }

    public void setWhomtoMeet(String whomtoMeet) {
        this.whomtoMeet = whomtoMeet;
    }

    public String getPurposetoMeet() {
        return purposetoMeet;
    }

    public void setPurposetoMeet(String purposetoMeet) {
        this.purposetoMeet = purposetoMeet;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
