package com.example.admin.mealplanner2new.Views;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.admin.mealplanner2new.Adapters.AdapterPagerAddTodayMeal;
import com.example.admin.mealplanner2new.Common.AddMealViewPager;
import com.example.admin.mealplanner2new.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddTodayMealActivity extends AppCompatActivity {

    TextView textView_date, textView_day;
    AddMealViewPager viewPager;

    int currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_today_meal);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.addTodayMeal_viewPager);
        viewPager.setAdapter(new AdapterPagerAddTodayMeal(getSupportFragmentManager()));

        textView_date = findViewById(R.id.addTodayMeal_tv_todayDate);
        textView_day = findViewById(R.id.addTodayMeal_tv_todayDay);

        setTodayData();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                              @Override
                                              public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                              }

                                              @Override
                                              public void onPageSelected(int position) {
                                                  currentItem = viewPager.getCurrentItem();

                                                  setHeaderTitle(currentItem);
                                              }

                                              @Override
                                              public void onPageScrollStateChanged(int state) {

                                              }
                                          }
        );


    }

    private void setHeaderTitle(int currentItem) {

        switch (currentItem) {

            case 0:
                setTitle("Add Meal Details");
                break;

            case 1:
                setTitle("Protein recipes");
                break;

            case 2:
                setTitle("Carb recipes");
                break;

            case 3:
                setTitle("Vegetables");
                break;

            case 4:
                setTitle("Fruit and Nuts");
                break;

            case 5:
                setTitle("Meal Summary");
                break;


        }

    }

    private void setTodayData() {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("dd-MM-yyyy");
        Date today_date = new Date();
        textView_date.setText(simpledateformat.format(today_date));

        simpledateformat = new SimpleDateFormat("EEEE");
        String dayOfWeek = simpledateformat.format(today_date);
        textView_day.setText(dayOfWeek);

        textView_date.setClickable(false);
        textView_day.setClickable(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                finish();

        }

        return super.onOptionsItemSelected(item);
    }


    public void setCurrentItem(int item, boolean smoothScroll) {
        viewPager.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void onBackPressed() {

        if (viewPager.getCurrentItem() == 0 || viewPager.getCurrentItem() == 5) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }

    }

}
