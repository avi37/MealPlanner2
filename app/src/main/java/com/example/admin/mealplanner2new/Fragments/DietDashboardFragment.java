package com.example.admin.mealplanner2new.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.mealplanner2new.R;
import com.example.admin.mealplanner2new.Views.AddIngredientActivity;
import com.example.admin.mealplanner2new.Views.AddRecipeActivity;
import com.example.admin.mealplanner2new.Views.AddTodayMealActivity;
import com.example.admin.mealplanner2new.Views.MealHistoryActivity;
import com.example.admin.mealplanner2new.Views.TodayMenuActivity;


public class DietDashboardFragment extends Fragment implements View.OnClickListener {

    View view_main;

    CardView cardView_add_today_meal, cardView_check_today_menu, cardView_meal_history, cardView_add_recipe, cardView_add_ingredient;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view_main = inflater.inflate(R.layout.fragment_dashboard, container, false);

        cardView_add_today_meal = view_main.findViewById(R.id.dash_card_add_today_meal);
        cardView_check_today_menu = view_main.findViewById(R.id.dash_card_today_menu);
        cardView_meal_history = view_main.findViewById(R.id.dash_card_meal_history);
        cardView_add_recipe = view_main.findViewById(R.id.dash_card_add_recipe);
        cardView_add_ingredient = view_main.findViewById(R.id.dash_card_add_ingredient);


        cardView_add_today_meal.setOnClickListener(this);
        cardView_check_today_menu.setOnClickListener(this);
        cardView_meal_history.setOnClickListener(this);
        cardView_add_recipe.setOnClickListener(this);
        cardView_add_ingredient.setOnClickListener(this);

        return view_main;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.dash_card_add_today_meal:
                method_add_today_meal();
                break;

            case R.id.dash_card_today_menu:
                method_check_today_menu();
                break;

            case R.id.dash_card_meal_history:
                method_check_meal_history();
                break;

            case R.id.dash_card_add_recipe:
                method_add_recipe();
                break;

            case R.id.dash_card_add_ingredient:
                method_add_ingredient();
                break;

        }
    }

    private void method_add_ingredient() {
        Intent i = new Intent(getContext(), AddIngredientActivity.class);
        startActivity(i);
    }

    private void method_add_recipe() {
        Intent i = new Intent(getContext(), AddRecipeActivity.class);
        startActivity(i);
    }

    private void method_check_meal_history() {
        Intent i = new Intent(getContext(), MealHistoryActivity.class);
        startActivity(i);
    }

    private void method_check_today_menu() {
        Intent i = new Intent(getContext(), TodayMenuActivity.class);
        startActivity(i);
    }

    private void method_add_today_meal() {
        Intent i = new Intent(getContext(), AddTodayMealActivity.class);
        startActivity(i);
    }

}
