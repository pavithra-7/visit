package com.example.visit.model;

public class StateData {
    private String stateId;
    private String state;

    public StateData() {
    }

    public StateData(String stateId, String state) {
        this.stateId = stateId;
        this.state = state;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


}
