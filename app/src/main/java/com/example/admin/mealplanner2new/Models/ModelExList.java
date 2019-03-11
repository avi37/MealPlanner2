package com.example.admin.mealplanner2new.Models;

public class ModelExList {

    private String id, name, photo, thumb, description, video_link;


    public ModelExList(String id, String name, String photo, String thumb, String description, String video_link) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.thumb = thumb;
        this.description = description;
        this.video_link = video_link;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideo_link() {
        return video_link;
    }

    public void setVideo_link(String video_link) {
        this.video_link = video_link;
    }
}
