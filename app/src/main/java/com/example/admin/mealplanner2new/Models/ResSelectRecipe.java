package com.example.admin.mealplanner2new.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResSelectRecipe {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("i_id")
    @Expose
    private String iId;

    @SerializedName("create_at")
    @Expose
    private String createAt;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("img")
    @Expose
    private String img;

    @SerializedName("thumb")
    @Expose
    private String thumb;

    @SerializedName("proteins")
    @Expose
    private String proteins;

    @SerializedName("fats")
    @Expose
    private String fats;

    @SerializedName("carbs")
    @Expose
    private String carbs;

    @SerializedName("calories")
    @Expose
    private String calories;

    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getiId() {
        return iId;
    }

    public void setiId(String iId) {
        this.iId = iId;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getProteins() {
        return proteins;
    }

    public void setProteins(String proteins) {
        this.proteins = proteins;
    }

    public String getFats() {
        return fats;
    }

    public void setFats(String fats) {
        this.fats = fats;
    }

    public String getCarbs() {
        return carbs;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
