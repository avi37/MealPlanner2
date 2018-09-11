package com.example.admin.mealplanner2new.Views;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.admin.mealplanner2new.R;

import java.util.Arrays;

public class MealTimesActivity extends AppCompatActivity {

    ProgressBar progressBar;
    ListView listView_mealTimes;
    TextView textView_noTimes;
    FloatingActionButton fab_addMEalTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_times);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.mealTimes_progressbar);
        listView_mealTimes = findViewById(R.id.mealTimes_listview);
        textView_noTimes = findViewById(R.id.mealTimes_tv_noTimes);
        fab_addMEalTimes = findViewById(R.id.mealTimes_btn_addMealTimes);


        fab_addMEalTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MealTimesActivity.this, AddNewMealTimeActivity.class);
                startActivity(i);
            }
        });


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
