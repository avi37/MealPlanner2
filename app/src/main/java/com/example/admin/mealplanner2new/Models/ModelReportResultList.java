package com.example.admin.mealplanner2new.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelReportResultList {

    @SerializedName("label_name")
    @Expose
    private String label_name;


    @SerializedName("result")
    @Expose
    private String result;


    public String getLabel_name() {
        return label_name;
    }

    public void setLabel_name(String label_name) {
        this.label_name = label_name;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
