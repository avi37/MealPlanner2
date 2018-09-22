package com.example.admin.mealplanner2new.Views;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.mealplanner2new.Models.Exercise;
import com.example.admin.mealplanner2new.Models.WordRoomDatabase;
import com.example.admin.mealplanner2new.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ExHistoryActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textView_date, textView_exName, textView_noEx;
    ImageView imageView_refresh;
    RecyclerView recyclerView_exList;
    ProgressBar progressBar;


    WordRoomDatabase wordRoomDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_history);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        wordRoomDatabase = WordRoomDatabase.getDatabase(getApplicationContext());

        new GetExData().execute();

        textView_date = findViewById(R.id.exHistory_tv_date);
        imageView_refresh = findViewById(R.id.exHistory_iv_refresh);
        textView_exName = findViewById(R.id.exHistory_tv_exTitle);
        recyclerView_exList = findViewById(R.id.exHistory_recView);
        textView_noEx = findViewById(R.id.exHistory_tv_noEx);
        progressBar = findViewById(R.id.exHistory_progressbar);

        textView_date.setOnClickListener(this);
        imageView_refresh.setOnClickListener(this);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.exHistory_tv_date:
                showDatePicker();
                break;

            case R.id.exHistory_iv_refresh:
                methodRefresh();
                break;

        }
    }

    private void showDatePicker() {

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                textView_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }


    private void methodRefresh() {
        Toast.makeText(this, "Refreshing your history list...", Toast.LENGTH_LONG).show();
    }


    @SuppressLint("StaticFieldLeak")
    private class GetExData extends AsyncTask<Void, Void, List<Exercise>> {

        @Override
        protected void onPostExecute(List<Exercise> exercises) {
            super.onPostExecute(exercises);

            if (exercises.size() > 0) {
                recyclerView_exList.setLayoutManager(new LinearLayoutManager(ExHistoryActivity.this, LinearLayoutManager.VERTICAL, false));

                recyclerView_exList.setAdapter(new CustomAdapter((ArrayList<Exercise>) exercises));

            } else {
                textView_noEx.setVisibility(View.VISIBLE);
            }

        }

        @Override
        protected List<Exercise> doInBackground(Void... voids) {

            return wordRoomDatabase.wordDao().getAllData("");
        }
    }


    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        private ArrayList<Exercise> mDataSet;

         CustomAdapter(ArrayList<Exercise> dataSet) {
            mDataSet = dataSet;
        }

        @NonNull
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
