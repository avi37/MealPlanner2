package com.example.admin.mealplanner2new.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.mealplanner2new.R;


public class YogaExerciseFragment extends Fragment {

    View view_main;
    TextView textView_name;
    ImageView imageView_image;
    Button button_done;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.fragment_yoga_exercise, container, false);

        textView_name = view_main.findViewById(R.id.yogaEx_tv_exName);
        imageView_image = view_main.findViewById(R.id.yogaEx_iv_image);
        button_done = view_main.findViewById(R.id.yogaEx_btn_done);

        return view_main;
    }


}
