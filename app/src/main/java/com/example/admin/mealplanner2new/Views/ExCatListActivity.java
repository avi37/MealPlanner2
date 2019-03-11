package com.example.admin.mealplanner2new.Views;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;

import com.example.admin.mealplanner2new.R;

public class ExCatListActivity extends AppCompatActivity implements View.OnClickListener {

    CardView cardView_cardio, cardView_chest, cardView_shoulders, cardView_biceps;
    CardView cardView_triceps, cardView_back, cardView_abs, cardView_legs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_ex_cat_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        cardView_cardio = findViewById(R.id.allEx_card_cardio);
        cardView_chest = findViewById(R.id.allEx_card_chest);
        cardView_shoulders = findViewById(R.id.allEx_card_shoulders);
        cardView_biceps = findViewById(R.id.allEx_card_biceps);
        cardView_triceps = findViewById(R.id.allEx_card_triceps);
        cardView_back = findViewById(R.id.allEx_card_back);
        cardView_abs = findViewById(R.id.allEx_card_abs);
        cardView_legs = findViewById(R.id.allEx_card_legs);


        cardView_cardio.setOnClickListener(this);
        cardView_chest.setOnClickListener(this);
        cardView_shoulders.setOnClickListener(this);
        cardView_biceps.setOnClickListener(this);
        cardView_triceps.setOnClickListener(this);
        cardView_back.setOnClickListener(this);
        cardView_abs.setOnClickListener(this);
        cardView_legs.setOnClickListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.allEx_card_cardio:
                goToExList("1");
                break;

            case R.id.allEx_card_chest:
                goToExList("2");
                break;

            case R.id.allEx_card_shoulders:
                goToExList("3");
                break;

            case R.id.allEx_card_biceps:
                goToExList("4");
                break;

            case R.id.allEx_card_triceps:
                goToExList("5");
                break;

            case R.id.allEx_card_back:
                goToExList("6");
                break;

            case R.id.allEx_card_abs:
                goToExList("7");
                break;

            case R.id.allEx_card_legs:
                goToExList("8");
                break;

        }

    }

    private void goToExList(String s) {
        Intent i = new Intent(ExCatListActivity.this, ExListOnCatActivity.class);
        i.putExtra("cat_id", s);
        startActivity(i);
    }

}
