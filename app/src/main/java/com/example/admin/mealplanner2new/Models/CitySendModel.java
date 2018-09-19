package com.example.admin.mealplanner2new.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CitySendModel {

    @SerializedName("state")
    @Expose
    private String state;


    public CitySendModel(String state) {
        this.state = state;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
