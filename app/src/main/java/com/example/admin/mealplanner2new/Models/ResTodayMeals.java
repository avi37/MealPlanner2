package com.example.admin.mealplanner2new.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResTodayMeals {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("meal_name")
    @Expose
    private String meal_name;

    @SerializedName("meal_type")
    @Expose
    private String meal_type;

    @SerializedName("meal_date")
    @Expose
    private String meal_date;

    @SerializedName("meal_time")
    @Expose
    private String meal_time;

    @SerializedName("meal_cat")
    @Expose
    private String meal_cat;

    @SerializedName("recipe")
    @Expose
    private ArrayList<Recipe> recipe = null;


    public ArrayList<Recipe> getRecipe() {
        return recipe;
    }

    public void setRecipe(ArrayList<Recipe> recipe) {
        this.recipe = recipe;
    }

    public String getMeal_category() {
        return meal_cat;
    }

    public void setMeal_category(String meal_category) {
        this.meal_cat = meal_category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMeal_name() {
        return meal_name;
    }

    public void setMeal_name(String meal_name) {
        this.meal_name = meal_name;
    }

    public String getMeal_type() {
        return meal_type;
    }

    public void setMeal_type(String meal_type) {
        this.meal_type = meal_type;
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


//---------------- Class for Recipe List response ----------------//

    public class Recipe {

        @SerializedName("recipeId")
        @Expose
        private Integer recipeId;

        @SerializedName("photo")
        @Expose
        private String photo;

        @SerializedName("thumb")
        @Expose
        private String thumb;

        @SerializedName("recipe_name")
        @Expose
        private String recipeName;

        @SerializedName("category")
        @Expose
        private Integer category;

        @SerializedName("carbs")
        @Expose
        private String carbs;

        @SerializedName("fats")
        @Expose
        private String fats;

        @SerializedName("calorie")
        @Expose
        private String calorie;

        @SerializedName("proteins")
        @Expose
        private String proteins;


        public Integer getRecipeId() {
            return recipeId;
        }

        public void setRecipeId(Integer recipeId) {
            this.recipeId = recipeId;
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

        public String getRecipeName() {
            return recipeName;
        }

        public void setRecipeName(String recipeName) {
            this.recipeName = recipeName;
        }

        public Integer getCategory() {
            return category;
        }

        public void setCategory(Integer category) {
            this.category = category;
        }

        public String getCarbs() {
            return carbs;
        }

        public void setCarbs(String carbs) {
            this.carbs = carbs;
        }

        public String getFats() {
            return fats;
        }

        public void setFats(String fats) {
            this.fats = fats;
        }

        public String getCalorie() {
            return calorie;
        }

        public void setCalorie(String calorie) {
            this.calorie = calorie;
        }

        public String getProteins() {
            return proteins;
        }

        public void setProteins(String proteins) {
            this.proteins = proteins;
        }

    }

}
