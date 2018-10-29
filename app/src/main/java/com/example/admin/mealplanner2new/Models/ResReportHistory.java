package com.example.admin.mealplanner2new.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResReportHistory {

    @SerializedName("doctorName")
    @Expose
    private String doctorName;

    @SerializedName("labName")
    @Expose
    private String labName;

    @SerializedName("report_name")
    @Expose
    private String reportName;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("report_id")
    @Expose
    private Integer reportId;

    @SerializedName("data")
    @Expose
    private List<ValueData> data = null;


    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public List<ValueData> getData() {
        return data;
    }

    public void setData(List<ValueData> data) {
        this.data = data;
    }


//--------------------------- Field Data Class ----------------------------------//

    public class ValueData implements Serializable {

        @SerializedName("label_name")
        @Expose
        private String labelName;

        @SerializedName("value")
        @Expose
        private String value;

        public String getLabelName() {
            return labelName;
        }

        public void setLabelName(String labelName) {
            this.labelName = labelName;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }


}
