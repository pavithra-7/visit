package com.example.visit;

public class Department {
    private String deptid;
    private String deptname;
    private String deptmailid;
    private String deptphone;
    private String deptcode;
    private String deptpwd;
    private String deptconpwd;
    private String depthod;
    private String depthodmail;
    private String depthodphone;

    public Department(){

    }

    public Department(String deptid, String deptname, String deptmailid, String deptphone,
                      String deptcode, String deptpwd, String deptconpwd, String depthod, String depthodmail,
                      String depthodphone) {
        this.deptid = deptid;
        this.deptname = deptname;
        this.deptmailid = deptmailid;
        this.deptphone = deptphone;
        this.deptcode = deptcode;
        this.deptpwd = deptpwd;
        this.deptconpwd = deptconpwd;
        this.depthod = depthod;
        this.depthodmail = depthodmail;
        this.depthodphone = depthodphone;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getDeptmailid() {
        return deptmailid;
    }

    public void setDeptmailid(String deptmailid) {
        this.deptmailid = deptmailid;
    }

    public String getDeptphone() {
        return deptphone;
    }

    public void setDeptphone(String deptphone) {
        this.deptphone = deptphone;
    }

    public String getDeptcode() {
        return deptcode;
    }

    public void setDeptcode(String deptcode) {
        this.deptcode = deptcode;
    }

    public String getDeptpwd() {
        return deptpwd;
    }

    public void setDeptpwd(String deptpwd) {
        this.deptpwd = deptpwd;
    }

    public String getDeptconpwd() {
        return deptconpwd;
    }

    public void setDeptconpwd(String deptconpwd) {
        this.deptconpwd = deptconpwd;
    }

    public String getDepthod() {
        return depthod;
    }

    public void setDepthod(String depthod) {
        this.depthod = depthod;
    }

    public String getDepthodmail() {
        return depthodmail;
    }

    public void setDepthodmail(String depthodmail) {
        this.depthodmail = depthodmail;
    }

    public String getDepthodphone() {
        return depthodphone;
    }

    public void setDepthodphone(String depthodphone) {
        this.depthodphone = depthodphone;
    }
}
