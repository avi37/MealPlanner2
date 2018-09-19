package com.example.admin.mealplanner2new.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityList {

@SerializedName("city")
@Expose
private String city;

public String getCity() {
return city;
}

public void setCity(String city) {
this.city = city;
}

}