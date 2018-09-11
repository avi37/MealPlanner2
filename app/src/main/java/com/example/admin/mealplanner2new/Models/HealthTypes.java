package com.example.admin.mealplanner2new.Models;

public class HealthTypes {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getHealthName() {
        return healthName;
    }

    public void setHealthName(String healthName) {
        this.healthName = healthName;
    }

    private String categoryName;

    private String healthName;


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private boolean isChecked;
}
