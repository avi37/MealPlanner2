package com.example.admin.mealplanner2new.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BodyCreateReport {

    @SerializedName("user_id")
    @Expose
    private String userId;

    @SerializedName("doctor_id")
    @Expose
    private String doctorId;

    @SerializedName("lab_id")
    @Expose
    private String labId;

    @SerializedName("report_id")
    @Expose
    private String reportId;

    @SerializedName("data")
    @Expose
    private List<ResReportFields> data = null;


    public BodyCreateReport(String userId, String doctorId, String labId, String reportId, List<ResReportFields> data) {
        this.userId = userId;
        this.doctorId = doctorId;
        this.labId = labId;
        this.reportId = reportId;
        this.data = data;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public List<ResReportFields> getData() {
        return data;
    }

    public void setData(List<ResReportFields> data) {
        this.data = data;
    }


}
