package com.example.admin.mealplanner2new.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.admin.mealplanner2new.Common.SessionManager;
import com.example.admin.mealplanner2new.R;


public class ChooseDashActivity extends AppCompatActivity {

    SessionManager sessionManager;

    LinearLayout linearLayout_opt_diet, linearLayout_opt_ex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_dash);

        sessionManager = new SessionManager(this);

        linearLayout_opt_diet = findViewById(R.id.ll_opt_dash1);
        linearLayout_opt_ex = findViewById(R.id.ll_opt_dash2);


        linearLayout_opt_diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                startActivity(new Intent(ChooseDashActivity.this, DietMainNavigationActivity.class));
            }
        });

        linearLayout_opt_ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                startActivity(new Intent(ChooseDashActivity.this, ExNavigationActivity.class));
            }
        });

    }


}
