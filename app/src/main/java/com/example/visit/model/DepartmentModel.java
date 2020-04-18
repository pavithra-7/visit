package com.example.visit.model;

public class DepartmentModel {

    private String depID;
    private String depName;
    private String depMail;
    private String depPhone;
    private String depCode;
    private String headofdep;
    private String headEmail;
    private String headPhone;

    public DepartmentModel() {
    }

    public DepartmentModel(String depID, String depName, String depMail, String depPhone, String depCode, String headofdep, String headEmail, String headPhone) {
        this.depID = depID;
        this.depName = depName;
        this.depMail = depMail;
        this.depPhone = depPhone;
        this.depCode = depCode;
        this.headofdep = headofdep;
        this.headEmail = headEmail;
        this.headPhone = headPhone;
    }

    public String getDepID() {
        return depID;
    }

    public void setDepID(String depID) {
        this.depID = depID;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getDepMail() {
        return depMail;
    }

    public void setDepMail(String depMail) {
        this.depMail = depMail;
    }

    public String getDepPhone() {
        return depPhone;
    }

    public void setDepPhone(String depPhone) {
        this.depPhone = depPhone;
    }

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    public String getHeadofdep() {
        return headofdep;
    }

    public void setHeadofdep(String headofdep) {
        this.headofdep = headofdep;
    }

    public String getHeadEmail() {
        return headEmail;
    }

    public void setHeadEmail(String headEmail) {
        this.headEmail = headEmail;
    }

    public String getHeadPhone() {
        return headPhone;
    }

    public void setHeadPhone(String headPhone) {
        this.headPhone = headPhone;
    }
}
