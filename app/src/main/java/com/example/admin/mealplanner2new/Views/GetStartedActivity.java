package com.example.admin.mealplanner2new.Views;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.admin.mealplanner2new.Fragments.GSMainFragment;
import com.example.admin.mealplanner2new.R;

public class GetStartedActivity extends AppCompatActivity {

    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        ft = getSupportFragmentManager().beginTransaction();

        ft.add(R.id.content_get_started, new GSMainFragment());
        ft.commit();
    }

}
