package com.example.admin.mealplanner2new.Views;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.mealplanner2new.Adapters.AdapterPagerQuestions;
import com.example.admin.mealplanner2new.R;

public class ExQueItemActivity extends AppCompatActivity {

    TextView textView_index;
    ViewPager viewPager_question;

    AdapterPagerQuestions adapterPagerQuestions;

    private int index;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_que_item);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        textView_index = findViewById(R.id.que_tv_index);
        viewPager_question = findViewById(R.id.queItem_viewPager);
        adapterPagerQuestions = new AdapterPagerQuestions(getSupportFragmentManager());

        viewPager_question.setAdapter(adapterPagerQuestions);

        Toast.makeText(this, "Swipe to view other answers", Toast.LENGTH_LONG).show();

        viewPager_question.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                index = position + 1;
                textView_index.setText("Question  " + index);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
