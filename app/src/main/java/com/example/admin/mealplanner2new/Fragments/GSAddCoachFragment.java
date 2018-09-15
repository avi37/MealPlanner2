package com.example.admin.mealplanner2new.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.mealplanner2new.Common.PrefRegister;
import com.example.admin.mealplanner2new.Models.ModelCoachList;
import com.example.admin.mealplanner2new.R;

import java.util.ArrayList;


public class GSAddCoachFragment extends Fragment {

    PrefRegister prefRegister;

    View view_main;
    TextView textView_noFound;
    RecyclerView recyclerView_coachList;
    Button button_next;

    private ArrayList<ModelCoachList> coachArrayList = new ArrayList<>();

    String selected_coachId;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.fragment_gsadd_coach, container, false);

        prefRegister = new PrefRegister(getContext());

        textView_noFound = view_main.findViewById(R.id.gs_coach_tv_noCoach);
        recyclerView_coachList = view_main.findViewById(R.id.gs_coach_recView);
        button_next = view_main.findViewById(R.id.gs_coach_btn_next);

        // Reset list
        if (coachArrayList != null) {
            coachArrayList.clear();
        }


        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isChecked = false;

                for (int i = 0; i < coachArrayList.size(); i++) {

                    if (coachArrayList.get(i).isChecked()) {

                        selected_coachId = coachArrayList.get(i).getcId();

                        isChecked = true;
                        break;
                    }
                }


                if (isChecked) {

                } else {
                    Toast.makeText(getActivity(), "Please select one coach", Toast.LENGTH_LONG).show();

                }

                prefRegister.setCoachId(selected_coachId);

                Fragment someFragment = new SignUpFragment();
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
