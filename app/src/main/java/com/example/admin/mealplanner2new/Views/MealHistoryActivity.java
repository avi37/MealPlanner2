package com.example.admin.mealplanner2new.Views;

import android.app.DatePickerDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
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
import com.example.admin.mealplanner2new.Models.ResTodayMeals;
import com.example.admin.mealplanner2new.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class MealHistoryActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String BASE_URL = "http://code-fuel.in/healthbotics/api/auth/";
    GetMealHistory getMealHistory;
    SessionManager sessionManager;


    TextView textView_date, textView_noHistory;
    ImageView imageView_refresh;
    RecyclerView recyclerView_history;
    ProgressBar progressBar;

    RecAdapter recAdapter;
    ArrayList<ResTodayMeals> apiResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_history);

        getMealHistory = getGetMealHistoryService(BASE_URL);
        sessionManager = new SessionManager(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        textView_date = findViewById(R.id.mealHistory_tv_date);
        textView_noHistory = findViewById(R.id.mealHistory_tv_noHistory);
        imageView_refresh = findViewById(R.id.mealHistory_iv_refresh);
        recyclerView_history = findViewById(R.id.mealHistory_recView);
        progressBar = findViewById(R.id.mealHistory_progressbar);


        textView_date.setOnClickListener(this);
        imageView_refresh.setOnClickListener(this);

        getMealHistory();

    }

    private void getMealHistory() {
        progressBar.setVisibility(View.VISIBLE);

        String token = sessionManager.getAccessToken();

        getMealHistory.get_mealHistory("Bearer " + token).enqueue(new Callback<ArrayList<ResTodayMeals>>() {
            @Override
            public void onResponse(Call<ArrayList<ResTodayMeals>> call, Response<ArrayList<ResTodayMeals>> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    if (response.body() != null) {

                        apiResponse = (ArrayList<ResTodayMeals>) response.body();

                        recAdapter = new RecAdapter(apiResponse);

                        recyclerView_history.setLayoutManager(new LinearLayoutManager(MealHistoryActivity.this, LinearLayoutManager.VERTICAL, false));

                        recyclerView_history.setAdapter(recAdapter);

                        recAdapter.notifyDataSetChanged();

                        if (response.body().size() == 0) {
                            textView_noHistory.setVisibility(View.VISIBLE);
                        }

                    } else {
                        // response body is null
                    }

                } else {
                    // response not successful
                }

            }

            @Override
            public void onFailure(Call<ArrayList<ResTodayMeals>> call, Throwable t) {
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

            case R.id.mealHistory_tv_date:
                showDatePicker();
                break;

            case R.id.mealHistory_iv_refresh:
                methodRefresh();
                break;

        }

    }

    private void methodRefresh() {
        progressBar.setVisibility(View.VISIBLE);

        textView_date.setText("All History");
        Toast.makeText(this, "Refreshing your history list...", Toast.LENGTH_LONG).show();

        recyclerView_history.setAdapter(new RecAdapter(apiResponse));

        progressBar.setVisibility(View.GONE);
    }

    private void showDatePicker() {
/*
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                dateExResult.clear();
                recyclerView_exList.getAdapter().notifyDataSetChanged();

                String _month = String.valueOf(monthOfYear + 1);

                textView_date.setText(dayOfMonth + "/" + _month + "/" + year);

                if (monthOfYear + 1 < 10) {
                    _month = "0" + _month;
                }

                String _date = dayOfMonth + "/" + _month + "/" + year;


                for (int i = 0; i < exerciseArrayList.size(); i++) {

                    if (getYMDDate(exerciseArrayList.get(i).getDay()).equals(_date)) {
                        dateExResult.add(exerciseArrayList.get(i));

                    }

                }

                recyclerView_exList.setAdapter(new CustomAdapter(dateExResult));
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show(); */

    }

    String getYMDDate(String dateString) {

        Date dateTime = null;

        try {
            dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(dateTime);
    }


//---------------------------------------- APIs ----------------------------------------------//

    GetMealHistory getGetMealHistoryService(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetMealHistory.class);
    }

    interface GetMealHistory {
        @Headers("X-Requested-With:XMLHttpRequest")
        @GET("getUserMealHistory")
        Call<ArrayList<ResTodayMeals>> get_mealHistory(@Header("Authorization") String token);
    }


//----------------------------------- Adapter Class ---------------------------------------------//

    public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {

        private ArrayList<ResTodayMeals> mDataSet;


        RecAdapter(ArrayList<ResTodayMeals> mDataSet) {
            this.mDataSet = mDataSet;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_list_today_menu, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            viewHolder.getTextView_name().setText(mDataSet.get(position).getMeal_name());
            viewHolder.textView_meal_category.setText(mDataSet.get(position).getMeal_category());
            viewHolder.textView_meal_date.setText(mDataSet.get(position).getMeal_date());
            viewHolder.textView_meal_time.setText(mDataSet.get(position).getMeal_time());

            Double protein = 0.0, fats = 0.0, carbs = 0.0, calories = 0.0;

            for (int i = 0; i < mDataSet.get(position).getRecipe().size(); i++) {
                protein += Double.parseDouble(mDataSet.get(position).getRecipe().get(i).getProteins());
                fats += Double.parseDouble(mDataSet.get(position).getRecipe().get(i).getFats());
                carbs += Double.parseDouble(mDataSet.get(position).getRecipe().get(i).getCarbs());
                calories += Double.parseDouble(mDataSet.get(position).getRecipe().get(i).getCalorie());
            }

            viewHolder.tv_protein.setText(String.valueOf(protein));
            viewHolder.tv_fats.setText(String.valueOf(fats));
            viewHolder.tv_carbs.setText(String.valueOf(carbs));
            viewHolder.tv_caloreis.setText(String.valueOf(calories));

            viewHolder.cardView_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* Intent i = new Intent(TodayMenuActivity.this, TodayMenuDetailsActivity.class);
                    i.putExtras("meal_details", mDataSet.get(position).getRecipe());
                    startActivity(i);*/
                }
            });

        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private final CardView cardView_main;
            private final TextView textView_name, textView_meal_category, textView_meal_date, textView_meal_time;
            private final TextView tv_protein, tv_fats, tv_carbs, tv_caloreis;

            ViewHolder(View v) {
                super(v);

                cardView_main = (CardView) v.findViewById(R.id.row_todayMenu_list_card);
                textView_name = (TextView) v.findViewById(R.id.row_todayMenu_tv_name);
                textView_meal_category = (TextView) v.findViewById(R.id.row_todayMenu_tv_category);
                textView_meal_date = (TextView) v.findViewById(R.id.row_todayMenu_tv_date);
                textView_meal_time = (TextView) v.findViewById(R.id.row_todayMenu_tv_time);
                tv_protein = (TextView) v.findViewById(R.id.row_todayMenu_tv_proteins);
                tv_fats = (TextView) v.findViewById(R.id.row_todayMenu_tv_fats);
                tv_carbs = (TextView) v.findViewById(R.id.row_todayMenu_tv_carbs);
                tv_caloreis = (TextView) v.findViewById(R.id.row_todayMenu_tv_calories);

                textView_meal_date.setVisibility(View.VISIBLE);

            }

            TextView getTextView_name() {
                return textView_name;
            }

        }

    }


}
