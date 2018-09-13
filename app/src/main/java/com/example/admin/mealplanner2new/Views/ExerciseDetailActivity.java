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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.mealplanner2new.Models.Exercise;
import com.example.admin.mealplanner2new.R;

import java.util.ArrayList;

public class ExerciseDetailActivity extends AppCompatActivity {

    RecyclerView recyclerView_exercises;
    CustomAdapter customAdapter;
    private Button btnStart;
    ArrayList<Exercise> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        recyclerView_exercises = findViewById(R.id.rvList);
        btnStart = findViewById(R.id.btnStart);

        exercises = new ArrayList<>();

        Exercise exercise1 = new Exercise();
        exercise1.setName("Push ups");          // 1
        exercise1.setReps("30 Reps");
        exercises.add(exercise1);

        Exercise exercise2 = new Exercise();
        exercise2.setName("Flat bench barbell");         // 2
        exercise2.setReps("3*12 Reps");
        exercises.add(exercise2);

        Exercise exercise3 = new Exercise();
        exercise3.setName("Inclined bench barbell");         // 3
        exercise3.setReps("3*12 Reps");
        exercises.add(exercise3);

        Exercise exercise4 = new Exercise();
        exercise4.setName("Cable fly");          // 4
        exercise4.setReps("2*12 Reps");
        exercises.add(exercise4);

        Exercise exercise5 = new Exercise();
        exercise5.setName("Dec fly machine");          // 5
        exercise5.setReps("4*12");
        exercises.add(exercise5);

        Exercise exercise6 = new Exercise();
        exercise6.setName("Hip twister");          // 6
        exercise6.setReps("50 Reps");
        exercises.add(exercise6);

        Exercise exercise7 = new Exercise();
        exercise7.setName("dumbbell side bend");           // 7
        exercise7.setReps("3*10");
        exercises.add(exercise7);

        Exercise exercise8 = new Exercise();
        exercise8.setName("dumbbell fly floor");           // 8
        exercise8.setReps("3*10");
        exercises.add(exercise8);

        recyclerView_exercises.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        customAdapter = new CustomAdapter(exercises);

        recyclerView_exercises.setAdapter(customAdapter);


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(ExerciseDetailActivity.this,StartExerciseActivity.class);
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

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView textView_name, textView_reps;

            public ViewHolder(View v) {
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

        public CustomAdapter(ArrayList<Exercise> dataSet) {
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

    }

}
