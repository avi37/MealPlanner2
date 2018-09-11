package com.example.admin.mealplanner2new.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.admin.mealplanner2new.R;


public class GSExFragment extends Fragment {

    View view_main;
    RadioGroup radioGroup_workout;
    RadioButton rbGYM;
    RadioButton rbHOME;
    boolean isChecked;
    Button button_next;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.fragment_gsex, container, false);

        radioGroup_workout = view_main.findViewById(R.id.gsEx_RG_workout);
        rbGYM = view_main.findViewById(R.id.gsEx_radio_gym);
        rbHOME = view_main.findViewById(R.id.gsEx_radio_home);
        button_next = view_main.findViewById(R.id.gsEx_btn_next);


        radioGroup_workout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                button_next.setVisibility(View.VISIBLE);
                isChecked =true;
            }
        });

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment someFragment = new GSGenderFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
                transaction.replace(R.id.content_get_started, someFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view_main;
    }


}
