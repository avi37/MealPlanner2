package com.example.admin.mealplanner2new.Views;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.admin.mealplanner2new.Common.RetrofitClient;
import com.example.admin.mealplanner2new.Models.Exercise;
import com.example.admin.mealplanner2new.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public class ExerciseDetailActivity extends AppCompatActivity {

    private final String BASE_URL = "http://code-fuel.in/healthbotics/api/auth/";
    RecyclerView recyclerView_exercises;
    CustomAdapter customAdapter;
    ArrayList<Exercise> exercises;
    private Button btnStart;
    private boolean flag = false;
    private String ex_category;
    private String cat_id;
    private String dateOf;
    private GetExerciseDetail exerciseDetail;
    private ProgressBar progressBar;
    private TextView tvDate;
    private TextView tvCatName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);

        exerciseDetail = RetrofitClient.getClient(BASE_URL).create(GetExerciseDetail.class);

        if (getIntent() != null) {

             flag = getIntent().getBooleanExtra("flag", false);
            // exercises = getIntent().getParcelableArrayListExtra("data");
            ex_category = getIntent().getStringExtra("ex_title");
            cat_id = getIntent().getStringExtra("cat_id");
            dateOf = getIntent().getStringExtra("date");

        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView_exercises = findViewById(R.id.rvList);
        progressBar = findViewById(R.id.progress_bar);
        tvCatName = findViewById(R.id.tvExerciseType);
        tvDate = findViewById(R.id.tvDate);

        tvCatName.setText("Day - "+ex_category);
        tvDate.setText(dateOf);


        btnStart = findViewById(R.id.btnStart);
        btnStart.setEnabled(false);

        if (flag) {
            btnStart.setVisibility(View.VISIBLE);
        } else {
            btnStart.setVisibility(View.GONE);
        }


        recyclerView_exercises.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        //customAdapter = new CustomAdapter(exercises);

        // recyclerView_exercises.setAdapter(customAdapter);

        exerciseDetail.exerciseDetail(cat_id).enqueue(new Callback<ArrayList<Exercise>>() {
            @Override
            public void onResponse(Call<ArrayList<Exercise>> call, Response<ArrayList<Exercise>> response) {

                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    exercises = response.body();
                    if (exercises != null && exercises.size() > 0) {
                        btnStart.setEnabled(true);

                        recyclerView_exercises.setAdapter(new CustomAdapter(exercises));

                    }


                } else {

                    btnStart.setEnabled(false);
                }


            }

            @Override
            public void onFailure(Call<ArrayList<Exercise>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                btnStart.setEnabled(false);

            }
        });


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(ExerciseDetailActivity.this, StartExerciseActivity.class);
                intent.putParcelableArrayListExtra("data", exercises);
                intent.putExtra("day",dateOf);
                intent.putExtra("work_id",ex_category);
                startActivity(intent);


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


    interface GetExerciseDetail {

        @POST("getworkout_detail")
        @FormUrlEncoded
        Call<ArrayList<Exercise>> exerciseDetail(@Field("cat_id") String cat_id);

    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        private ArrayList<Exercise> mDataSet;

        CustomAdapter(ArrayList<Exercise> dataSet) {
            mDataSet = dataSet;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_exercise_detail, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            viewHolder.textView_name.setText(mDataSet.get(position).getName());
            viewHolder.textView_reps.setText(mDataSet.get(position).getReps());
        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView textView_name, textView_reps;

            ViewHolder(View v) {
                super(v);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                    }
                });
                textView_name = (TextView) v.findViewById(R.id.tvExerciseName);
                textView_reps = v.findViewById(R.id.tvRep);
            }

        }

    }

}
