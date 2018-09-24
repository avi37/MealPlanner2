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


public class AddProteinFoodFragment extends Fragment {

    View view_main;
    TextView textView_noRecipes;
    RecyclerView recyclerView_proteinRecipes;
    ProgressBar progressBar;
    Button button_next;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.fragment_add_protein_food, container, false);

        textView_noRecipes = view_main.findViewById(R.id.addProtein_tv_noRecipes);
        progressBar = view_main.findViewById(R.id.addProtein_progressBar);
        recyclerView_proteinRecipes = view_main.findViewById(R.id.addProtein_recView_recipes);
        button_next = view_main.findViewById(R.id.addProteinFood_btn_next);


        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AddTodayMealActivity) getActivity()).setCurrentItem(2, true);
            }
        });

        return view_main;

    }

    public static AddProteinFoodFragment newInstance(int page, String title) {
        AddProteinFoodFragment fragmentAddProteinFood = new AddProteinFoodFragment();
        Bundle args = new Bundle();
        args.putInt("2", page);
        args.putString("Add Protein Food", title);
        fragmentAddProteinFood.setArguments(args);
        return fragmentAddProteinFood;
    }


}
