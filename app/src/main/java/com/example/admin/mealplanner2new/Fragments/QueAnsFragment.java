package com.example.admin.mealplanner2new.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.mealplanner2new.R;


public class QueAnsFragment extends Fragment {

    View view_main;
    TextView textView_index;


    private String page_title;
    private int page_index;


    public static QueAnsFragment newInstance(int index, String title) {
        QueAnsFragment fragment = new QueAnsFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        page_index = getArguments().getInt("someInt", 0);
        page_title = getArguments().getString("someTitle");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.fragment_que_ans, container, false);

        textView_index = view_main.findViewById(R.id.queAns_tv_index);

        textView_index.setText("Question" + page_index);

        return view_main;
    }


}
