package com.example.admin.mealplanner2new.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.admin.mealplanner2new.Common.PrefRegister;
import com.example.admin.mealplanner2new.R;


public class GSGenderFragment extends Fragment {

    PrefRegister prefRegister;

    View view_main;
    Button button_next;
    RadioGroup rgMaleFemale;
    RadioButton rbMale;
    RadioButton rbFemale;

    boolean isChecked;
    String selectedGender;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.fragment_gsgender, container, false);

        prefRegister = new PrefRegister(getContext());

        button_next = view_main.findViewById(R.id.gsGender_btn_next);
        rgMaleFemale = view_main.findViewById(R.id.rgMaleFemale);
        rbMale = view_main.findViewById(R.id.gsGender_tv_male);
        rbFemale = view_main.findViewById(R.id.gsGender_tv_female);


        rgMaleFemale.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                button_next.setVisibility(View.VISIBLE);
                isChecked = true;

                if (checkedId == R.id.gsGender_tv_male) {
                    selectedGender = "0";
                } else {
                    selectedGender = "1";
                }

            }
        });


        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prefRegister.setGender(selectedGender);

                Fragment someFragment = new GSAimFragment();
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
