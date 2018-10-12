package com.example.admin.mealplanner2new.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.admin.mealplanner2new.R;


public class QueAnsFragment extends Fragment {

    View view_main;
    WebView webView;


    private String page_title;
    private int page_index;

    String link = "http://code-fuel.in/healthbotics/ex_que/";

    public static QueAnsFragment newInstance(int index, String title) {
        QueAnsFragment fragment = new QueAnsFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    // https://www.youtube.com/watch?v=Xg37L5z5r_M

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        page_index = getArguments().getInt("index", 0);
        page_title = getArguments().getString("title");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.fragment_que_ans, container, false);

        webView = view_main.findViewById(R.id.queAns_webView);

        webView.loadUrl(link);


        return view_main;
    }


}
