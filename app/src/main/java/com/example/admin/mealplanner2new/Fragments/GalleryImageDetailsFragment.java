package com.example.admin.mealplanner2new.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.mealplanner2new.R;


public class GalleryImageDetailsFragment extends Fragment {

    View view_main;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.fragment_gallery_image_details, container, false);

        return view_main;
    }


}
