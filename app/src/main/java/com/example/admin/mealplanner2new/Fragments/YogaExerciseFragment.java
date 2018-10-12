package com.example.admin.mealplanner2new.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.mealplanner2new.Models.ModelYogaList;
import com.example.admin.mealplanner2new.R;
import com.example.admin.mealplanner2new.Views.StartYogaActivity;

import java.util.ArrayList;


public class YogaExerciseFragment extends Fragment {

    View view_main;
    TextView textView_name, textView_restTime;
    ImageView imageView_image;
    Button button_done, button_skip;

    ArrayList<ModelYogaList> resYogaArrayList;

    int yogaId;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        resYogaArrayList = ((StartYogaActivity) context).resYogaArrayList;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        resYogaArrayList = ((StartYogaActivity) activity).resYogaArrayList;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        yogaId = getArguments().getInt("yoga_id");              // coming from StartYogaActivity

        Toast.makeText(getContext(), "Yoga_id: " + yogaId, Toast.LENGTH_LONG).show();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.fragment_yoga_exercise, container, false);

        textView_name = view_main.findViewById(R.id.yogaEx_tv_exName);
        imageView_image = view_main.findViewById(R.id.yogaEx_iv_image);
        textView_restTime = view_main.findViewById(R.id.yogaEx_iv_restTime);
        button_done = view_main.findViewById(R.id.yogaEx_btn_done);
        button_skip = view_main.findViewById(R.id.yogaEx_btn_skip);


        if (yogaId < resYogaArrayList.size()) {
            setFragmentData(yogaId);
        }

        button_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodButtonDone();
            }
        });

        button_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodButtonSkip();
            }
        });


        return view_main;
    }

    private void setFragmentData(int yogaId) {

        textView_name.setText(resYogaArrayList.get(yogaId).getName());
        imageView_image.setImageResource(resYogaArrayList.get(yogaId).getDrwable_image());

    }


    private void methodButtonSkip() {

        if (yogaId < resYogaArrayList.size()) {

            textView_name.setVisibility(View.VISIBLE);

            button_skip.setVisibility(View.GONE);
            textView_restTime.setVisibility(View.GONE);

            imageView_image.setVisibility(View.VISIBLE);
            button_done.setVisibility(View.VISIBLE);

            yogaId = yogaId + 1;

            YogaExerciseFragment yogaExerciseFragment = new YogaExerciseFragment();

            Bundle bundle = new Bundle();
            bundle.putInt("yoga_id", yogaId);

            yogaExerciseFragment.setArguments(bundle);

            getFragmentManager().beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.activity_start_yoga, yogaExerciseFragment, String.valueOf(yogaId))
                    .commit();


        } else {
            Toast.makeText(getContext(), "Finished", Toast.LENGTH_LONG).show();
            getActivity().onBackPressed();
        }


    }

    private void methodButtonDone() {
        imageView_image.setVisibility(View.GONE);
        button_done.setVisibility(View.GONE);

        button_skip.setVisibility(View.VISIBLE);
        textView_restTime.setVisibility(View.VISIBLE);

        textView_name.setVisibility(View.INVISIBLE);

        new CountDownTimer(30500, 1000) {

            public void onTick(long millisUntilFinished) {
                textView_restTime.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                methodButtonSkip();
            }
        }.start();

    }


}
