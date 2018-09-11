package com.example.admin.mealplanner2new.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.admin.mealplanner2new.R;
import com.example.admin.mealplanner2new.Views.AddTodayMealActivity;


public class AddMealDetailsFragment extends Fragment {

    View view_main;
    Button button_next;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.fragment_add_meal_details, container, false);

        button_next = view_main.findViewById(R.id.addMealDetails_btn_next);


        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AddTodayMealActivity) getActivity()).setCurrentItem(1, true);
            }
        });

        return view_main;
    }


    public static AddMealDetailsFragment newInstance(int page, String title) {
        AddMealDetailsFragment fragmentAddMealDetails = new AddMealDetailsFragment();
        Bundle args = new Bundle();
        args.putInt("1", page);
        args.putString("Add Meal Details", title);
        fragmentAddMealDetails.setArguments(args);
        return fragmentAddMealDetails;
    }


}
