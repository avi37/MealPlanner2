package com.example.admin.mealplanner2new.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.mealplanner2new.R;
import com.example.admin.mealplanner2new.Views.GetStartedActivity;
import com.example.admin.mealplanner2new.Views.LoginActivity;

public class GSMainFragment extends Fragment {

    View view_main;
    TextView textView_loginHere;
    TextView tv_start;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.fragment_gsmain, container, false);

        tv_start = view_main.findViewById(R.id.getStarted_tv_getStarted);
        textView_loginHere = view_main.findViewById(R.id.getStarted_tv_login_here);


        tv_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment someFragment = new GSExFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
                transaction.replace(R.id.content_get_started, someFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        textView_loginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        return view_main;

    }


}
