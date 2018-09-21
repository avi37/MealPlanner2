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
import android.widget.TextView;

import com.example.admin.mealplanner2new.Models.Exercise;
import com.example.admin.mealplanner2new.R;

import java.util.ArrayList;


public class ExerciseDetailActivity extends AppCompatActivity {

    RecyclerView recyclerView_exercises;
    CustomAdapter customAdapter;
    ArrayList<Exercise> exercises;
    private Button btnStart;
    private boolean flag = false;
    private String ex_category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);

        if (getIntent() != null) {

            flag = getIntent().getBooleanExtra("flag", false);
            exercises = getIntent().getParcelableArrayListExtra("data");
            ex_category = getIntent().getStringExtra("ex_title");

        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        recyclerView_exercises = findViewById(R.id.rvList);
        btnStart = findViewById(R.id.btnStart);

        if (flag) {
            btnStart.setVisibility(View.VISIBLE);
        } else {
            btnStart.setVisibility(View.GONE);
        }


        recyclerView_exercises.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        customAdapter = new CustomAdapter(exercises);

        recyclerView_exercises.setAdapter(customAdapter);


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(ExerciseDetailActivity.this, StartExerciseActivity.class);
                intent.putParcelableArrayListExtra("data", exercises);
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
