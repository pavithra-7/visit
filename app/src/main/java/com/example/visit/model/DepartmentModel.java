package com.example.visit.model;

import java.util.concurrent.atomic.AtomicInteger;

public class DepartmentModel {

    private static final AtomicInteger count = new AtomicInteger(0);
    private int depID;
    private String depName;
    private String depMail;
    private String depPassword;
    private String depPhone;
    private String depCode;
    private String headofdep;
    private String headEmail;
    private String headPhone;
    private String userCount;


    public DepartmentModel() {
    }
    public DepartmentModel(int deptID,String depName, String depMail, String depPassword, String depPhone, String depCode, String headofdep, String headEmail, String headPhone, String userCount) {
        this.depID=deptID;
        this.depName = depName;
        this.depMail = depMail;
        this.depPassword = depPassword;
        this.depPhone = depPhone;
        this.depCode = depCode;
        this.headofdep = headofdep;
        this.headEmail = headEmail;
        this.headPhone = headPhone;
        this.userCount = userCount;

    }


    public DepartmentModel( String depName, String depMail,String depPassword, String depPhone, String depCode, String headofdep, String headEmail, String headPhone,String userCount) {
        depID = count.incrementAndGet();
        this.depName = depName;
        this.depMail = depMail;
        this.depPassword = depPassword;
        this.depPhone = depPhone;
        this.depCode = depCode;
        this.headofdep = headofdep;
        this.headEmail = headEmail;
        this.headPhone = headPhone;
        this.userCount = userCount;

    }

    public DepartmentModel(String depPhone, String headofdep, String headEmail, String headPhone) {
        this.depPhone = depPhone;
        this.headofdep = headofdep;
        this.headEmail = headEmail;
        this.headPhone = headPhone;
    }


    public int getDepID() {
        return depID;
    }

    public void setDepID(int depID) {
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

    public String getDepPassword() {
        return depPassword;
    }

    public void setDepPassword(String depPassword) {
        this.depPassword = depPassword;
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

    public String getUserCount() {
        return userCount;
    }

    public void setUserCount(String userCount) {
        this.userCount = userCount;
    }
}
