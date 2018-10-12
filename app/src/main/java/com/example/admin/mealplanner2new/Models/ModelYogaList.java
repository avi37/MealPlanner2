package com.example.admin.mealplanner2new.Models;

public class ModelYogaList {

    private String id, name;
    private Integer drwable_image;

    public ModelYogaList(String id, String name, Integer drwable_image) {
        this.id = id;
        this.name = name;
        this.drwable_image = drwable_image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDrwable_image() {
        return drwable_image;
    }

    public void setDrwable_image(Integer drwable_image) {
        this.drwable_image = drwable_image;
    }
}
