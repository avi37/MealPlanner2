package com.example.admin.mealplanner2new.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.admin.mealplanner2new.Fragments.AddCarbFoodFragment;
import com.example.admin.mealplanner2new.Fragments.AddFnTFragment;
import com.example.admin.mealplanner2new.Fragments.AddMealDetailsFragment;
import com.example.admin.mealplanner2new.Fragments.AddMealSummaryFragment;
import com.example.admin.mealplanner2new.Fragments.AddProteinFoodFragment;
import com.example.admin.mealplanner2new.Fragments.AddVeggiesFragment;

public class AdapterPagerAddTodayMeal extends FragmentStatePagerAdapter {

    private static int NUM_ITEMS = 6;

    public AdapterPagerAddTodayMeal(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                return AddMealDetailsFragment.newInstance(0, "Page # 1");

            case 1:
                return AddProteinFoodFragment.newInstance(1, "Page # 2");

            case 2:
                return AddCarbFoodFragment.newInstance(2, "Page # 3");

            case 3:
                return AddVeggiesFragment.newInstance(3, "Page # 3");

            case 4:
                return AddFnTFragment.newInstance(4, "Page # 5");

            case 5:
                return AddMealSummaryFragment.newInstance(5, "Page # 6");

            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

}
