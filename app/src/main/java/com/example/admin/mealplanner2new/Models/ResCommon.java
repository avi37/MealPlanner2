package com.example.admin.mealplanner2new.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResCommon {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("access_token")
    @Expose
    private String access_token;

    @SerializedName("token_type")
    @Expose
    private String token_type;

    @SerializedName("u_id")
    @Expose
    private String u_id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("expires_at")
    @Expose
    private String expires_at;


    @SerializedName("first_photo")
    @Expose
    private String first_photo;


    @SerializedName("thumb")
    @Expose
    private String thumb;

    @SerializedName("photo")
    @Expose
    private String photo;

    @SerializedName("cat_name")
    @Expose
    private String cat_name;

    @SerializedName("cat_time")
    @Expose
    private String cat_time;



    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_time() {
        return cat_time;
    }

    public void setCat_time(String cat_time) {
        this.cat_time = cat_time;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getFirst_photo() {
        return first_photo;
    }

    public void setFirst_photo(String first_photo) {
        this.first_photo = first_photo;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(String expires_at) {
        this.expires_at = expires_at;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }

}
