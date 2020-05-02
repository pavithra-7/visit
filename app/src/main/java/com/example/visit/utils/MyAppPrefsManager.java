package com.example.visit.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * MyAppPrefsManager handles some Prefs of AndroidShopApp Application
 **/


public class MyAppPrefsManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor prefsEditor;

    private static final String PREF_NAME = "VISITOR_MANAGEMENT";

    private static final String DEPARTMENT_NAME = "dept_name";
    private static final String IS_USER_LOGGED_IN_ADMIN = "isLogged_in_Admin";
    private static final String IS_USER_LOGGED_IN_DEPARTMENT = "isLogged_in_Department";



     public MyAppPrefsManager(Context context) {
        int PRIVATE_MODE = 0;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        prefsEditor = sharedPreferences.edit();
        prefsEditor.apply();
    }





    public void setAdminLoggedIn(boolean isAdminLoggedIn) {
        prefsEditor.putBoolean(IS_USER_LOGGED_IN_ADMIN, isAdminLoggedIn);
        prefsEditor.commit();
    }

    public boolean isAdminLoggedIn() {
        return sharedPreferences.getBoolean(IS_USER_LOGGED_IN_ADMIN, false);
    }


    public void setDepartmentLoggedIn(boolean isDepartmentLoggedIn) {
        prefsEditor.putBoolean(IS_USER_LOGGED_IN_DEPARTMENT, isDepartmentLoggedIn);
        prefsEditor.commit();
    }

    public boolean isDepartmentLoggedIn() {
        return sharedPreferences.getBoolean(IS_USER_LOGGED_IN_DEPARTMENT, false);
    }


    public void setDepartmentName(String departmentName) {
        prefsEditor.putString(DEPARTMENT_NAME, departmentName);
        prefsEditor.commit();
    }

    public String getDepartmentName() {
        return sharedPreferences.getString(DEPARTMENT_NAME, null);
    }


}
