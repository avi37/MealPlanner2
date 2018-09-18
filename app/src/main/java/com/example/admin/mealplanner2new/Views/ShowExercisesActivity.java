package com.example.admin.mealplanner2new.Views;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;

import com.example.admin.mealplanner2new.R;

public class ShowExercisesActivity extends AppCompatActivity {

    CardView cardView_monday;
    CardView cardView_tuesDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_exercises);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        cardView_monday = findViewById(R.id.cvMonday);
        cardView_tuesDay = findViewById(R.id.cvTuesday);


        cardView_tuesDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowExercisesActivity.this, ExerciseDetailActivity.class));
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
