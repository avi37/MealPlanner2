package com.example.admin.mealplanner2new.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BodyLogin {

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("remember_me")
    @Expose
    private boolean remember_me;


    public BodyLogin(String email, String password, boolean remember_me) {
        this.email = email;
        this.password = password;
        this.remember_me = remember_me;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRemember_me(boolean remember_me) {
        this.remember_me = remember_me;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean getRemember_me() {
        return remember_me;
    }
}
