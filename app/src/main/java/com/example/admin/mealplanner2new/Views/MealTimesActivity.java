package com.example.admin.mealplanner2new.Views;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.admin.mealplanner2new.Common.RetrofitClient;
import com.example.admin.mealplanner2new.Common.SessionManager;
import com.example.admin.mealplanner2new.Models.ResCommon;
import com.example.admin.mealplanner2new.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;


public class MealTimesActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://code-fuel.in/healthbotics/api/auth/";
    GetCategoriesAPI getCategoriesAPI;

    SessionManager sessionManager;

    ProgressBar progressBar;
    RecyclerView recyclerView_mealTimes;
    TextView textView_noTimes;
    FloatingActionButton fab_addMEalTimes;

    RecAdapter recAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_times);

        getCategoriesAPI = getGetCategoriesAPIService(BASE_URL);

        sessionManager = new SessionManager(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.mealTimes_progressbar);
        recyclerView_mealTimes = findViewById(R.id.mealTimes_recView);
        textView_noTimes = findViewById(R.id.mealTimes_tv_noTimes);
        fab_addMEalTimes = findViewById(R.id.mealTimes_btn_addMealTimes);


        fab_addMEalTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MealTimesActivity.this, AddNewMealTimeActivity.class);
                startActivity(i);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        getSavedCategories();
    }

    private void getSavedCategories() {
        progressBar.setVisibility(View.VISIBLE);

        String token = sessionManager.getAccessToken();

        getCategoriesAPI.get_Categories("Bearer " + token).enqueue(new Callback<List<ResCommon>>() {
            @Override
            public void onResponse(Call<List<ResCommon>> call, Response<List<ResCommon>> response) {

                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    if (response.body() != null) {

                        if (response.body().size() > 0) {

                            recAdapter = new RecAdapter((ArrayList<ResCommon>) response.body());

                            recyclerView_mealTimes.setLayoutManager(new LinearLayoutManager(MealTimesActivity.this, LinearLayoutManager.VERTICAL, false));

                            recyclerView_mealTimes.setAdapter(recAdapter);

                            recAdapter.notifyDataSetChanged();

                        } else {
                            textView_noTimes.setVisibility(View.VISIBLE);
                        }


                    } else {
                        //response body is null

                    }

                } else {
                    // response not successful
                }

            }

            @Override
            public void onFailure(Call<List<ResCommon>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
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


//------------------------------------------ APIs ----------------------------------------//

    GetCategoriesAPI getGetCategoriesAPIService(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetCategoriesAPI.class);
    }

    interface GetCategoriesAPI {
        @Headers("X-Requested-With:XMLHttpRequest")
        @GET("getUserMealCat")
        Call<List<ResCommon>> get_Categories(@Header("Authorization") String token);
    }


//----------------------------------- Adapter Class ----------------------------------------//

    public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {

        private ArrayList<ResCommon> mDataSet;


        RecAdapter(ArrayList<ResCommon> mDataSet) {
            this.mDataSet = mDataSet;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_list_meal_times, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            viewHolder.textView_name.setText(mDataSet.get(position).getCat_name());
            viewHolder.textView_time.setText(mDataSet.get(position).getCat_time());
        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {

            private final TextView textView_name, textView_time;

            ViewHolder(View v) {
                super(v);

                textView_name = (TextView) v.findViewById(R.id.row_mealTimes_tv_name);
                textView_time = (TextView) v.findViewById(R.id.row_mealTimes_tv_time);
            }

        }

    }

}
