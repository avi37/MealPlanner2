package com.example.admin.mealplanner2new.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResReDoLa {

    @SerializedName("report")
    @Expose
    private List<Report> report = null;

    @SerializedName("doctor")
    @Expose
    private List<Doctor> doctor = null;

    @SerializedName("lab")
    @Expose
    private List<Lab> lab = null;

    public List<Report> getReport() {
        return report;
    }

    public void setReport(List<Report> report) {
        this.report = report;
    }

    public List<Doctor> getDoctor() {
        return doctor;
    }

    public void setDoctor(List<Doctor> doctor) {
        this.doctor = doctor;
    }

    public List<Lab> getLab() {
        return lab;
    }

    public void setLab(List<Lab> lab) {
        this.lab = lab;
    }


    //------------------------- Doctor Class --------------------------//
//----------------------------------------------------------------//
    public class Doctor {

        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("hospital_name")
        @Expose
        private String hospitalName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHospitalName() {
            return hospitalName;
        }

        public void setHospitalName(String hospitalName) {
            this.hospitalName = hospitalName;
        }

    }

    //------------------------- Lab Class -----------------------------//
//----------------------------------------------------------------//
    public class Lab {

        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("lab_name")
        @Expose
        private String labName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLabName() {
            return labName;
        }

        public void setLabName(String labName) {
            this.labName = labName;
        }

    }


    //------------------------- Report Class --------------------------//
//----------------------------------------------------------------//
    public class Report {

        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("report_name")
        @Expose
        private String reportName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getReportName() {
            return reportName;
        }

        public void setReportName(String reportName) {
            this.reportName = reportName;
        }

    }


}
