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
import android.widget.TextView;

import com.example.admin.mealplanner2new.Common.PrefRegister;
import com.example.admin.mealplanner2new.R;


public class GSScheduleFragment extends Fragment {

    PrefRegister prefRegister;

    View view_main;
    RadioGroup radioGroup_schedule;
    RadioButton rbAuto, rbManual;
    Button button_next;

    boolean isAnySelected = false;
    String selectedOpt;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.fragment_gsschedule, container, false);

        prefRegister = new PrefRegister(getContext());

        radioGroup_schedule = view_main.findViewById(R.id.rgSchedule);
        rbAuto = view_main.findViewById(R.id.gsSchedule_RB_auto);
        rbManual = view_main.findViewById(R.id.gsSchedule_RB_manual);
        button_next = view_main.findViewById(R.id.gsSchedule_btn_next);


        radioGroup_schedule.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                button_next.setVisibility(View.VISIBLE);
                isAnySelected = true;

                if (checkedId == R.id.gsSchedule_RB_auto) {
                    selectedOpt = "0";
                } else {
                    selectedOpt = "1";
                }
            }
        });

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prefRegister.setSchedule(selectedOpt);

                if (selectedOpt.equals("0")) {
                    Fragment someFragment = new GSAddCoachFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
                    transaction.replace(R.id.content_get_started, someFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    Fragment someFragment = new GSExPlanFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
                    transaction.replace(R.id.content_get_started, someFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }

            }
        });


        return view_main;
    }


}
