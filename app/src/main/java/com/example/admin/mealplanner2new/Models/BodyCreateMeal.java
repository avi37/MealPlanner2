package com.example.admin.mealplanner2new.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BodyCreateMeal {

    @SerializedName("recipe_id")
    @Expose
    private String recipe_id;

    @SerializedName("user_id")
    @Expose
    private String user_id;

    @SerializedName("cat_id")
    @Expose
    private String cat_id;

    @SerializedName("meal_type")
    @Expose
    private String meal_type;

    @SerializedName("meal_name")
    @Expose
    private String meal_name;

    @SerializedName("meal_date")
    @Expose
    private String meal_date;

    @SerializedName("meal_time")
    @Expose
    private String meal_time;


    public BodyCreateMeal(String recipe_id, String user_id, String cat_id, String meal_type, String meal_name, String meal_date, String meal_time) {
        this.recipe_id = recipe_id;
        this.user_id = user_id;
        this.cat_id = cat_id;
        this.meal_type = meal_type;
        this.meal_name = meal_name;
        this.meal_date = meal_date;
        this.meal_time = meal_time;
    }

    public String getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(String recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getMeal_type() {
        return meal_type;
    }

    public void setMeal_type(String meal_type) {
        this.meal_type = meal_type;
    }

    public String getMeal_name() {
        return meal_name;
    }

    public void setMeal_name(String meal_name) {
        this.meal_name = meal_name;
    }

    public String getMeal_date() {
        return meal_date;
    }

    public void setMeal_date(String meal_date) {
        this.meal_date = meal_date;
    }

    public String getMeal_time() {
        return meal_time;
    }

    public void setMeal_time(String meal_time) {
        this.meal_time = meal_time;
    }
}
