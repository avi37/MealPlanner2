package com.example.admin.mealplanner2new.Views;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;

import com.example.admin.mealplanner2new.R;

public class ExSectionActivity extends AppCompatActivity {

    CardView cardView_allEx, cardView_myEx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_section);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        cardView_allEx = findViewById(R.id.exSection_card_allEx);
        cardView_myEx = findViewById(R.id.exSection_card_myEx);


        cardView_allEx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ExSectionActivity.this, ExCatListActivity.class);

                startActivity(i);
            }
        });

        cardView_myEx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ExSectionActivity.this, ShowExercisesActivity.class);

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
