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
import com.example.admin.mealplanner2new.Views.ExHistoryActivity;
import com.example.admin.mealplanner2new.Views.ExNavigationActivity;
import com.example.admin.mealplanner2new.Views.ImageGalleryActivity;
import com.example.admin.mealplanner2new.Views.ShowExercisesActivity;


public class ExDashboardFragment extends Fragment implements View.OnClickListener {

    View view_main;
    CardView cardView_showExercise, cardView_showHistory, cardView_imageGallery;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view_main = inflater.inflate(R.layout.fragment_ex_dashboard, container, false);

        cardView_showExercise = view_main.findViewById(R.id.dash_card_show_exercise);
        cardView_showHistory = view_main.findViewById(R.id.dash_card_ex_history);
        cardView_imageGallery = view_main.findViewById(R.id.dash_card_image_gallery);


        cardView_showExercise.setOnClickListener(this);
        cardView_showHistory.setOnClickListener(this);
        cardView_imageGallery.setOnClickListener(this);


        return view_main;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.dash_card_show_exercise:
                methodShowExercises();
                break;

            case R.id.dash_card_ex_history:
                methodShowHistory();
                break;

            case R.id.dash_card_image_gallery:
                methodImageGallery();
                break;
        }

    }


    private void methodShowExercises() {
        Intent i = new Intent(getContext(), ShowExercisesActivity.class);
        startActivity(i);
    }

    private void methodShowHistory() {
        Intent i = new Intent(getContext(), ExHistoryActivity.class);
        startActivity(i);
    }

    private void methodImageGallery() {
        Intent i = new Intent(getContext(), ImageGalleryActivity.class);
        startActivity(i);
    }

}
