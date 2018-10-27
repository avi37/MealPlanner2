package com.example.admin.mealplanner2new.Views;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.admin.mealplanner2new.R;

public class ReportHistoryDetailActivity extends AppCompatActivity {

    TextView tv_date, tv_reportName, tv_doctorName, tv_labName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_history_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        tv_date = findViewById(R.id.reportHistoryDetail_tv_date);
        tv_reportName = findViewById(R.id.reportHistoryDetail_tv_reportName);
        tv_doctorName = findViewById(R.id.reportHistoryDetail_tv_doctorName);
        tv_labName = findViewById(R.id.reportHistoryDetail_tv_labName);

        tv_date.setText(getIntent().getStringExtra("date"));
        tv_reportName.setText(getIntent().getStringExtra("report_name"));
        tv_doctorName.setText(getIntent().getStringExtra("doctor_name"));
        tv_labName.setText(getIntent().getStringExtra("lab_name"));

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
