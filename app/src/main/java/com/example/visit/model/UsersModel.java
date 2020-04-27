package com.example.visit.model;

import java.util.concurrent.atomic.AtomicInteger;

public class UsersModel {

    private static final AtomicInteger count = new AtomicInteger(0);
    private int userId;
    private String imageId;
    private String name;
    private String  email;
    private String phone;
    private String whomToMeet;
    private String purposeToMeet;
    private String address;
    private String state;
    private String city;
    private String district;
    private String imageUrl;


    public UsersModel() {
    }


    public UsersModel(String imageUrl,String imageId,String name, String email, String phone, String whomToMeet, String purposeToMeet, String address, String state, String city, String district) {
        userId = count.incrementAndGet();
        this.imageId = imageId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.whomToMeet = whomToMeet;
        this.purposeToMeet = purposeToMeet;
        this.address = address;
        this.state = state;
        this.city = city;
        this.district = district;
        this.imageUrl = imageUrl;
    }


    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWhomToMeet() {
        return whomToMeet;
    }

    public void setWhomToMeet(String whomToMeet) {
        this.whomToMeet = whomToMeet;
    }

    public String getPurposeToMeet() {
        return purposeToMeet;
    }

    public void setPurposeToMeet(String purposeToMeet) {
        this.purposeToMeet = purposeToMeet;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
