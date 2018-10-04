package com.example.admin.mealplanner2new.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.mealplanner2new.Adapters.AdapterTipsVP;
import com.example.admin.mealplanner2new.Common.OnImagePressedListener;
import com.example.admin.mealplanner2new.R;
import com.example.admin.mealplanner2new.Views.ExHistoryActivity;
import com.example.admin.mealplanner2new.Views.ExSectionActivity;
import com.example.admin.mealplanner2new.Views.ImageGalleryActivity;
import com.example.admin.mealplanner2new.Views.ShowExercisesActivity;
import com.example.admin.mealplanner2new.Views.YogaSectionActivity;

import java.util.ArrayList;


public class ExDashboardFragment extends Fragment implements View.OnClickListener {

    View view_main;
    CardView cardView_showExercise, cardView_showHistory, cardView_imageGallery, cardView_yoga;
    ViewPager viewPager_tips;

    private ArrayList<Integer> tipsArray = new ArrayList<Integer>();
    private Handler handler;
    private int eventAction = -1;
    private int currentPage = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.fragment_ex_dashboard, container, false);

        cardView_showExercise = view_main.findViewById(R.id.dash_card_show_exercise);
        cardView_showHistory = view_main.findViewById(R.id.dash_card_ex_history);
        cardView_imageGallery = view_main.findViewById(R.id.dash_card_image_gallery);
        cardView_yoga = view_main.findViewById(R.id.dash_card_yoga);

        viewPager_tips = view_main.findViewById(R.id.exDash_viewPager_tips);

        cardView_showExercise.setOnClickListener(this);
        cardView_showHistory.setOnClickListener(this);
        cardView_imageGallery.setOnClickListener(this);
        cardView_yoga.setOnClickListener(this);

        tipsArray.add(R.drawable.tips1);
        tipsArray.add(R.drawable.tips2);
        tipsArray.add(R.drawable.tips3);

        init(view_main);

        return view_main;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.dash_card_show_exercise:
                methodShowExercises();
                break;

            case R.id.dash_card_ex_history:
                methodShowHistory();
                break;

            case R.id.dash_card_image_gallery:
                methodImageGallery();
                break;

            case R.id.dash_card_yoga:
                methodYogaSection();
                break;
        }

    }


    private void methodShowExercises() {
        Intent i = new Intent(getContext(), ExSectionActivity.class);
        startActivity(i);
    }

    private void methodShowHistory() {
        Intent i = new Intent(getContext(), ExHistoryActivity.class);
        startActivity(i);
    }

    private void methodImageGallery() {
        Intent i = new Intent(getContext(), ImageGalleryActivity.class);
        startActivity(i);
    }

    private void methodYogaSection() {
        Intent i = new Intent(getContext(), YogaSectionActivity.class);
        startActivity(i);
    }


    private void init(View view) {

        // Auto start of viewpager
        handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == tipsArray.size()) {
                    currentPage = 0;
                }
                viewPager_tips.setCurrentItem(currentPage++, true);
                handler.postDelayed(this, 2000);
            }
        };

        handler.post(Update);


        viewPager_tips.setAdapter(new AdapterTipsVP(getActivity(), tipsArray, new OnImagePressedListener() {
            @Override
            public void onImagePressed(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    eventAction = event.getAction();
                    handler.removeCallbacksAndMessages(null);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {

                    eventAction = event.getAction();
                    handler.post(Update);


                }

            }
        }));

        /*CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        indicator.setViewPager(mPager);*/


        viewPager_tips.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (handler != null) {
            handler.removeCallbacks(null);
        }


    }


}
