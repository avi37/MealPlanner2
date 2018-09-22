package com.example.admin.mealplanner2new.Adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.admin.mealplanner2new.Fragments.QueAnsFragment;


public class AdapterPagerQuestions extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 50;

    public AdapterPagerQuestions(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                return QueAnsFragment.newInstance(0, "Page # 1");
            case 1:
                return QueAnsFragment.newInstance(1, "Page # 2");
            case 2:
                return QueAnsFragment.newInstance(2, "Page # 3");
            case 3:
                return QueAnsFragment.newInstance(3, "Page # 4");
            case 4:
                return QueAnsFragment.newInstance(4, "Page # 5");
            case 5:
                return QueAnsFragment.newInstance(5, "Page # 6");
            case 6:
                return QueAnsFragment.newInstance(6, "Page # 7");
            case 7:
                return QueAnsFragment.newInstance(7, "Page # 8");
            case 8:
                return QueAnsFragment.newInstance(8, "Page # 9");
            case 9:
                return QueAnsFragment.newInstance(9, "Page # 10");


            case 10:
                return QueAnsFragment.newInstance(10, "Page # 11");
            case 11:
                return QueAnsFragment.newInstance(11, "Page # 12");
            case 12:
                return QueAnsFragment.newInstance(12, "Page # 13");
            case 13:
                return QueAnsFragment.newInstance(13, "Page # 14");
            case 14:
                return QueAnsFragment.newInstance(14, "Page # 15");
            case 15:
                return QueAnsFragment.newInstance(15, "Page # 16");
            case 16:
                return QueAnsFragment.newInstance(16, "Page # 17");
            case 17:
                return QueAnsFragment.newInstance(17, "Page # 18");
            case 18:
                return QueAnsFragment.newInstance(18, "Page # 19");
            case 19:
                return QueAnsFragment.newInstance(19, "Page # 20");


            case 20:
                return QueAnsFragment.newInstance(20, "Page # 21");
            case 21:
                return QueAnsFragment.newInstance(21, "Page # 22");
            case 22:
                return QueAnsFragment.newInstance(22, "Page # 23");
            case 23:
                return QueAnsFragment.newInstance(23, "Page # 24");
            case 24:
                return QueAnsFragment.newInstance(24, "Page # 25");
            case 25:
                return QueAnsFragment.newInstance(25, "Page # 26");
            case 26:
                return QueAnsFragment.newInstance(26, "Page # 27");
            case 27:
                return QueAnsFragment.newInstance(27, "Page # 28");
            case 28:
                return QueAnsFragment.newInstance(28, "Page # 29");
            case 29:
                return QueAnsFragment.newInstance(29, "Page # 30");


            case 30:
                return QueAnsFragment.newInstance(30, "Page # 31");
            case 31:
                return QueAnsFragment.newInstance(31, "Page # 32");
            case 32:
                return QueAnsFragment.newInstance(32, "Page # 33");
            case 33:
                return QueAnsFragment.newInstance(33, "Page # 34");
            case 34:
                return QueAnsFragment.newInstance(34, "Page # 35");
            case 35:
                return QueAnsFragment.newInstance(35, "Page # 36");
            case 36:
                return QueAnsFragment.newInstance(36, "Page # 37");
            case 37:
                return QueAnsFragment.newInstance(37, "Page # 38");
            case 38:
                return QueAnsFragment.newInstance(38, "Page # 39");
            case 39:
                return QueAnsFragment.newInstance(39, "Page # 40");


            case 40:
                return QueAnsFragment.newInstance(40, "Page # 41");
            case 41:
                return QueAnsFragment.newInstance(41, "Page # 42");
            case 42:
                return QueAnsFragment.newInstance(42, "Page # 43");
            case 43:
                return QueAnsFragment.newInstance(43, "Page # 44");
            case 44:
                return QueAnsFragment.newInstance(44, "Page # 45");
            case 45:
                return QueAnsFragment.newInstance(45, "Page # 46");
            case 46:
                return QueAnsFragment.newInstance(46, "Page # 47");
            case 47:
                return QueAnsFragment.newInstance(47, "Page # 48");
            case 48:
                return QueAnsFragment.newInstance(48, "Page # 49");
            case 49:
                return QueAnsFragment.newInstance(49, "Page # 50");


            default:
                return null;
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "Question" + position;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

}
