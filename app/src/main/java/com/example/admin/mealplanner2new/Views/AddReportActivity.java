package com.example.admin.mealplanner2new.Views;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.admin.mealplanner2new.R;

public class AddReportActivity extends AppCompatActivity {

    Spinner spinner_reportType;
    TextView textView_paramName, textView_yourResult;
    RecyclerView recyclerView_dataEntries;
    Button button_submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        spinner_reportType = findViewById(R.id.addReport_spinner_reportType);
        textView_paramName = findViewById(R.id.addReport_tv_paramName);
        textView_yourResult = findViewById(R.id.addReport_tv_result);
        recyclerView_dataEntries = findViewById(R.id.addReport_recView_dataEntries);
        button_submit = findViewById(R.id.addReport_btn_submit);


        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodSubmit();
            }
        });

        getReportTypes();

    }

    private void methodSubmit() {
        // Submit method
    }

    private void getReportTypes() {
        // API Call

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
