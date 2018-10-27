package com.example.admin.mealplanner2new.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResReportHistory {

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("report_name")
    @Expose
    private String report_name;

    @SerializedName("doctor_name")
    @Expose
    private String doctor_name;

    @SerializedName("lab_name")
    @Expose
    private String lab_name;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReport_name() {
        return report_name;
    }

    public void setReport_name(String report_name) {
        this.report_name = report_name;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getLab_name() {
        return lab_name;
    }

    public void setLab_name(String lab_name) {
        this.lab_name = lab_name;
    }
}
