package com.example.admin.mealplanner2new.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.mealplanner2new.Common.PrefRegister;
import com.example.admin.mealplanner2new.R;


public class GSLevelFragment extends Fragment implements View.OnClickListener {

    PrefRegister prefRegister;

    View view_main;
    TextView textView_1, textView_2, textView_3, textView_4, textView_5;
    Button button_next;

    boolean isAnySelected = false;
    String selectedLevel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.fragment_gslevel, container, false);

        prefRegister = new PrefRegister(getContext());

        textView_1 = view_main.findViewById(R.id.gsLevel_tv_1);
        textView_2 = view_main.findViewById(R.id.gsLevel_tv_2);
        textView_3 = view_main.findViewById(R.id.gsLevel_tv_3);
        textView_4 = view_main.findViewById(R.id.gsLevel_tv_4);
        textView_5 = view_main.findViewById(R.id.gsLevel_tv_5);
        button_next = view_main.findViewById(R.id.gsLevel_btn_next);


        textView_1.setOnClickListener(this);
        textView_2.setOnClickListener(this);
        textView_3.setOnClickListener(this);
        textView_4.setOnClickListener(this);
        textView_5.setOnClickListener(this);
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prefRegister.setLevel(selectedLevel);

                Fragment someFragment = new GSPastExFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
                transaction.replace(R.id.content_get_started, someFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        return view_main;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.gsLevel_tv_1:
                selectLevel("1");
                break;

            case R.id.gsLevel_tv_2:
                selectLevel("2");
                break;

            case R.id.gsLevel_tv_3:
                selectLevel("3");
                break;

            case R.id.gsLevel_tv_4:
                selectLevel("4");
                break;

            case R.id.gsLevel_tv_5:
                selectLevel("5");
                break;

        }

    }

    private void selectLevel(String level) {

        selectedLevel = level;

        if (isAnySelected) {
            resetAll();
        } else {
            isAnySelected = true;
        }

        button_next.setVisibility(View.VISIBLE);
        switch (level) {
            case "1":
                textView_1.setBackgroundColor(getResources().getColor(R.color.gs_grey));
                break;

            case "2":
                textView_2.setBackgroundColor(getResources().getColor(R.color.gs_grey));
                break;

            case "3":
                textView_3.setBackgroundColor(getResources().getColor(R.color.gs_grey));
                break;

            case "4":
                textView_4.setBackgroundColor(getResources().getColor(R.color.gs_grey));
                break;

            case "5":
                textView_5.setBackgroundColor(getResources().getColor(R.color.gs_grey));
                break;
        }

    }


    private void resetAll() {
        textView_1.setBackgroundColor(getResources().getColor(R.color.level_red1));
        textView_2.setBackgroundColor(getResources().getColor(R.color.level_red2));
        textView_3.setBackgroundColor(getResources().getColor(R.color.level_red3));
        textView_4.setBackgroundColor(getResources().getColor(R.color.level_red4));
        textView_5.setBackgroundColor(getResources().getColor(R.color.level_red5));
    }


}
