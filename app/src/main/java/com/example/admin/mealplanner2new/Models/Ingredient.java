package com.example.admin.mealplanner2new.Models;

import java.util.ArrayList;

public class Ingredient {

    public ArrayList<ResRecipeItem> getProteinList() {
        return proteinList;
    }

    public void setProteinList(ArrayList<ResRecipeItem> proteinList) {
        this.proteinList = proteinList;
    }

    public ArrayList<ResRecipeItem> getCarbList() {
        return carbList;
    }

    public void setCarbList(ArrayList<ResRecipeItem> carbList) {
        this.carbList = carbList;
    }

    public ArrayList<ResRecipeItem> getVeggiList() {
        return veggiList;
    }

    public void setVeggiList(ArrayList<ResRecipeItem> veggiList) {
        this.veggiList = veggiList;
    }

    private ArrayList<ResRecipeItem> proteinList;
     private ArrayList<ResRecipeItem> carbList;
     private ArrayList<ResRecipeItem> veggiList;
}
