package com.example.admin.mealplanner2new.Views;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.admin.mealplanner2new.Common.SessionManager;
import com.example.admin.mealplanner2new.Fragments.GSMainFragment;
import com.example.admin.mealplanner2new.R;

public class GetStartedActivity extends AppCompatActivity {

    SessionManager sessionManager;

    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        sessionManager = new SessionManager(this);

        if (sessionManager.checkLogin()) {
            Intent i = new Intent(this, ChooseDashActivity.class);

            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(i);
            finish();
        }

        ft = getSupportFragmentManager().beginTransaction();

        ft.add(R.id.content_get_started, new GSMainFragment());
        ft.commit();
    }

}
