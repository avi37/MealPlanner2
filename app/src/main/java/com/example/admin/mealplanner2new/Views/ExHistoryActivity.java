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

import com.example.admin.mealplanner2new.Common.RetrofitClient;
import com.example.admin.mealplanner2new.Common.SessionManager;
import com.example.admin.mealplanner2new.Models.Exercise;
import com.example.admin.mealplanner2new.Models.WordRoomDatabase;
import com.example.admin.mealplanner2new.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public class ExHistoryActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String BASE_URL = "http://code-fuel.in/healthbotics/api/auth/";
    TextView textView_date, textView_exName, textView_noEx;
    ImageView imageView_refresh;
    RecyclerView recyclerView_exList;
    ProgressBar progressBar;
    WordRoomDatabase wordRoomDatabase;
    private GetExerciseHistory getExerciseHistory;
    private String u_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_history);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        u_id = new SessionManager(getApplicationContext()).getKeyUId();

        getExerciseHistory = RetrofitClient.getClient(BASE_URL).create(GetExerciseHistory.class);

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


        getExerciseHistory.getEXHistory(u_id).enqueue(new Callback<ArrayList<Exercise>>() {
            @Override
            public void onResponse(Call<ArrayList<Exercise>> call, Response<ArrayList<Exercise>> response) {


                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    final ArrayList<Exercise> exerciseArrayList = response.body();
                    if (exerciseArrayList.size() > 0) {
                        recyclerView_exList.setLayoutManager(new LinearLayoutManager(ExHistoryActivity.this, LinearLayoutManager.VERTICAL, false));
//
                        recyclerView_exList.setAdapter(new CustomAdapter(exerciseArrayList));
//

                    } else {
                        exerciseArrayList.clear();
                        recyclerView_exList.getAdapter().notifyDataSetChanged();
                        textView_noEx.setVisibility(View.VISIBLE);
                    }

                } else {


                }


            }

            @Override
            public void onFailure(Call<ArrayList<Exercise>> call, Throwable t) {

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


    interface GetExerciseHistory {

        @POST("day_excersice_hisotry")
        @FormUrlEncoded
        Call<ArrayList<Exercise>> getEXHistory(@Field("u_id") String u_id);


    }

    @SuppressLint("StaticFieldLeak")
    private class GetExData extends AsyncTask<Void, Void, List<Exercise>> {

        @Override
        protected void onPostExecute(List<Exercise> exercises) {
            super.onPostExecute(exercises);

//            if (exercises.size() > 0) {
//                recyclerView_exList.setLayoutManager(new LinearLayoutManager(ExHistoryActivity.this, LinearLayoutManager.VERTICAL, false));
//
//                recyclerView_exList.setAdapter(new CustomAdapter((ArrayList<Exercise>) exercises));
//
//            } else {
//                textView_noEx.setVisibility(View.VISIBLE);
//            }

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

            if (position > 0 && mDataSet.get(position).getDay().equals(mDataSet.get(position - 1).getDay())) {

                viewHolder.tvDate.setVisibility(View.GONE);


            } else {
                viewHolder.tvDate.setVisibility(View.VISIBLE);
            }


            viewHolder.textView_name.setText(mDataSet.get(position).getName());
            viewHolder.textView_reps.setText(mDataSet.get(position).getReps());
            viewHolder.tvDate.setText(mDataSet.get(position).getDay());

        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView textView_name, textView_reps, tvDate;

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
                tvDate = v.findViewById(R.id.tvDate);
            }

        }

    }

}
