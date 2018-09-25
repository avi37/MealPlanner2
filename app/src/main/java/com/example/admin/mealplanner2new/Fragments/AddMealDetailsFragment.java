package com.example.admin.mealplanner2new.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.admin.mealplanner2new.Common.PrefMeal;
import com.example.admin.mealplanner2new.Common.PrefRegister;
import com.example.admin.mealplanner2new.Common.SessionManager;
import com.example.admin.mealplanner2new.R;
import com.example.admin.mealplanner2new.Views.AddTodayMealActivity;


public class AddMealDetailsFragment extends Fragment {

    SessionManager sessionManager;
    PrefMeal prefMeal;

    View view_main;
    Spinner spinner_category, spinner_mealType;
    TextView textView_mealTime;
    Button button_next;

    private String mealCategory, mealTime, mealType;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.fragment_add_meal_details, container, false);

        sessionManager = new SessionManager(getContext());
        prefMeal = new PrefMeal(getContext());

        spinner_category = view_main.findViewById(R.id.addTodayMeal_spinnerMealCat);
        textView_mealTime = view_main.findViewById(R.id.addTodayMeal_tv_selectedTime);
        spinner_mealType = view_main.findViewById(R.id.addTodayMeal_spinnerMealType);
        button_next = view_main.findViewById(R.id.addMealDetails_btn_next);


        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mealCategory = spinner_category.getSelectedItem().toString();
                mealTime = textView_mealTime.getText().toString();
                String _mealType = spinner_mealType.getSelectedItem().toString();

                if (_mealType.equals("Veg.")) {
                    mealType = "1";
                } else {
                    mealType = "0";
                }

                prefMeal.setMealDetails(mealType, mealTime, mealCategory);
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
