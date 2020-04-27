package com.example.visit.model;

public class DistrictData {
    private String state;
    private String distId;
    private String districtname;


    public DistrictData() {
    }

    public DistrictData(String state, String distId, String districtname) {
        this.state = state;
        this.distId = distId;
        this.districtname = districtname;
    }



    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistId() {
        return distId;
    }

    public void setDistId(String distId) {
        this.distId = distId;
    }

    public String getDistrictname() {
        return districtname;
    }

    public void setDistrictname(String districtname) {
        this.districtname = districtname;
    }

   }
