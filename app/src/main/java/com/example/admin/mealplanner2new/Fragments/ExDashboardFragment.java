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
import com.example.admin.mealplanner2new.Views.ExNavigationActivity;
import com.example.admin.mealplanner2new.Views.ShowExercisesActivity;


public class ExDashboardFragment extends Fragment implements View.OnClickListener {

    View view_main;

    CardView cardView_showExercise;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view_main = inflater.inflate(R.layout.fragment_ex_dashboard, container, false);

        cardView_showExercise = view_main.findViewById(R.id.dash_card_show_exercise);


        cardView_showExercise.setOnClickListener(this);

        return view_main;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.dash_card_show_exercise:
                methodShowExercises();
                break;
        }

    }

    private void methodShowExercises() {
        Intent i = new Intent(getContext(), ShowExercisesActivity.class);
        startActivity(i);
    }

}
