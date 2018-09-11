package com.example.admin.mealplanner2new.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BodySignUp {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("password_confirmation")
    @Expose
    private String password_confirmation;

    @SerializedName("mobile")
    @Expose
    private String mobile;

    @SerializedName("dob")
    @Expose
    private String dob;

    @SerializedName("photo")
    @Expose
    private String photo;


    public BodySignUp(String name, String email, String password, String password_confirmation, String mobile, String dob, String photo) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.password_confirmation = password_confirmation;
        this.mobile = mobile;
        this.dob = dob;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
