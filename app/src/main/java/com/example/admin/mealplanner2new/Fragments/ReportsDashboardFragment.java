package com.example.admin.mealplanner2new.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.mealplanner2new.R;
import com.example.admin.mealplanner2new.Views.AddReportActivity;
import com.example.admin.mealplanner2new.Views.ReportHistoryActivity;


public class ReportsDashboardFragment extends Fragment implements View.OnClickListener {

    private Context context;
    View view_main;

    CardView cardView_addReport, cardView_reportHistory;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.context = activity;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.fragment_reports_dashboard, container, false);

        cardView_addReport = view_main.findViewById(R.id.dash_card_add_report);
        cardView_reportHistory = view_main.findViewById(R.id.dash_card_report_history);

        cardView_addReport.setOnClickListener(this);
        cardView_reportHistory.setOnClickListener(this);

        return view_main;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.dash_card_add_report:
                methodAddReport();
                break;

            case R.id.dash_card_report_history:
                methodReportHistory();
                break;
        }


    }


    private void methodAddReport() {
        Intent i = new Intent(context, AddReportActivity.class);
        startActivity(i);
    }

    private void methodReportHistory() {
        Intent i = new Intent(context, ReportHistoryActivity.class);
        startActivity(i);
    }

}
