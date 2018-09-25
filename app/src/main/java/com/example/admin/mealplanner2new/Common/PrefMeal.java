package com.example.admin.mealplanner2new.Common;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefMeal {

    SharedPreferences mealPref;
    SharedPreferences.Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    private static final String MEAL_PREF_NAME = "MealPref";


    private static final String MEAL_CATEGORY = "meal_category";
    private static final String MEAL_TIME = "meal_time";
    private static final String MEAL_TYPE = "meal_type";


    /*private static final String HEIGHT = "height";
    private static final String AGE = "age";
    private static final String WEIGHT = "weight";*/


    public PrefMeal(Context context) {
        this._context = context;
        mealPref = _context.getSharedPreferences(MEAL_PREF_NAME, PRIVATE_MODE);
        editor = mealPref.edit();
    }

    public void setMealDetails(String mealCategory, String mealTime, String mealType) {
        editor.putString(MEAL_CATEGORY, mealCategory);
        editor.putString(MEAL_TIME, mealTime);
        editor.putString(MEAL_TYPE, mealType);

        editor.commit();
    }

    public String getMealCategory() {
        return mealPref.getString(MEAL_CATEGORY, null);
    }

    public String getMealTime() {
        return mealPref.getString(MEAL_TIME, null);
    }

    public String getMealType() {
        return mealPref.getString(MEAL_TYPE, null);
    }

    public void deleteMealPref() {
        editor.clear();
        editor.commit();
    }

}
