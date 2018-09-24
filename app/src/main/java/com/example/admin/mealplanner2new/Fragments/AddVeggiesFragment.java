package com.example.admin.mealplanner2new.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.admin.mealplanner2new.R;
import com.example.admin.mealplanner2new.Views.AddTodayMealActivity;

public class AddVeggiesFragment extends Fragment {

    View view_main;
    TextView textView_noVeggies;
    RecyclerView recyclerView_veggies;
    ProgressBar progressBar;
    Button button_next;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.fragment_add_veggies, container, false);

        textView_noVeggies = view_main.findViewById(R.id.addVeggies_tv_noRecipes);
        progressBar = view_main.findViewById(R.id.addVeggies_progressBar);
        recyclerView_veggies = view_main.findViewById(R.id.addVeggies_recView_recipes);
        button_next = view_main.findViewById(R.id.addVeggies_btn_next);


        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AddTodayMealActivity) getActivity()).setCurrentItem(4, true);
            }
        });

        return view_main;
    }


    public static AddVeggiesFragment newInstance(int page, String title) {
        AddVeggiesFragment fragmentAddVeggies = new AddVeggiesFragment();
        Bundle args = new Bundle();
        args.putInt("4", page);
        args.putString("Add Veggies", title);
        fragmentAddVeggies.setArguments(args);
        return fragmentAddVeggies;
    }

}
