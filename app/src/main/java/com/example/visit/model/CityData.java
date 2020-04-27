package com.example.visit.model;

public class CityData {
    private String state;
    private String district;
    private String cityId;
    private String city;

    public CityData() {
    }

    public CityData(String state, String district, String cityId, String city) {
        this.state = state;
        this.district = district;
        this.cityId = cityId;
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
