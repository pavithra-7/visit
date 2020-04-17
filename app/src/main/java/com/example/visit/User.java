package com.example.visit;

public class User {
    private String usname;
    private String state2;
    private String city2;
    private String district2;
    private String usaddress;
    private String usmeet;
    private String purposevisit;
    private String uscontact;
    private String usmail;

    public User(){

    }

    public User(String usname, String state2, String city2, String district2, String usaddress,
                String usmeet, String purposevisit, String uscontact, String usmail) {
        this.usname = usname;
        this.state2 = state2;
        this.city2 = city2;
        this.district2 = district2;
        this.usaddress = usaddress;
        this.usmeet = usmeet;
        this.purposevisit = purposevisit;
        this.uscontact = uscontact;
        this.usmail = usmail;
    }

    public String getUsname() {
        return usname;
    }

    public void setUsname(String usname) {
        this.usname = usname;
    }

    public String getState2() {
        return state2;
    }

    public void setState2(String state2) {
        this.state2 = state2;
    }

    public String getCity2() {
        return city2;
    }

    public void setCity2(String city2) {
        this.city2 = city2;
    }

    public String getDistrict2() {
        return district2;
    }

    public void setDistrict2(String district2) {
        this.district2 = district2;
    }

    public String getUsaddress() {
        return usaddress;
    }

    public void setUsaddress(String usaddress) {
        this.usaddress = usaddress;
    }

    public String getUsmeet() {
        return usmeet;
    }

    public void setUsmeet(String usmeet) {
        this.usmeet = usmeet;
    }

    public String getPurposevisit() {
        return purposevisit;
    }

    public void setPurposevisit(String purposevisit) {
        this.purposevisit = purposevisit;
    }

    public String getUscontact() {
        return uscontact;
    }

    public void setUscontact(String uscontact) {
        this.uscontact = uscontact;
    }

    public String getUsmail() {
        return usmail;
    }

    public void setUsmail(String usmail) {
        this.usmail = usmail;
    }
}
