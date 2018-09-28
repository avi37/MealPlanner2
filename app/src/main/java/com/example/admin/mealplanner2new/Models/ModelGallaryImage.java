package com.example.admin.mealplanner2new.Models;

public class ModelGallaryImage {

    String id, thumb, photo;


    public ModelGallaryImage() {

    }

    public ModelGallaryImage(String id, String thumb, String photo) {
        this.id = id;
        this.thumb = thumb;
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
