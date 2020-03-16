package com.oyvindmonsen.workout_tracker_api.controller;

import java.util.HashMap;

public class ApiResponse {
    private String status;
    private HashMap<String, String> data = new HashMap<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HashMap<String, String> getData() {
        return data;
    }

    public void addData(String key, String value) {
        this.data.put(key, value);
    }
}
