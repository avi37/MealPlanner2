package com.example.admin.mealplanner2new.Views;

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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.admin.mealplanner2new.Common.RetrofitClient;
import com.example.admin.mealplanner2new.Common.SessionManager;
import com.example.admin.mealplanner2new.Models.ResTodayMeals;
import com.example.admin.mealplanner2new.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class TodayMenuActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://code-fuel.in/healthbotics/api/auth/";
    GetTodayMealAPI getTodayMealAPI;
    SessionManager sessionManager;


    TextView textView_noMeals;
    ProgressBar progressBar;
    RecyclerView recyclerView_mealList;

    RecAdapter recAdapter;
    ArrayList<ResTodayMeals> apiResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_menu);

        getTodayMealAPI = getGetTodayMealAPIService(BASE_URL);
        sessionManager = new SessionManager(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        textView_noMeals = findViewById(R.id.todayMenu_tv_noMeals);
        progressBar = findViewById(R.id.todayMenu_progressbar);
        recyclerView_mealList = findViewById(R.id.todayMenu_revView);

        getTodayMeals();

    }

    private void getTodayMeals() {
        progressBar.setVisibility(View.VISIBLE);

        String token = sessionManager.getAccessToken();
        String today_date = getTodayDate();

        getTodayMealAPI.get_TodayMeals("Bearer " + token, today_date).enqueue(new Callback<List<ResTodayMeals>>() {
            @Override
            public void onResponse(Call<List<ResTodayMeals>> call, Response<List<ResTodayMeals>> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    if (response.body() != null) {

                        apiResponse = (ArrayList<ResTodayMeals>) response.body();

                        recAdapter = new RecAdapter(apiResponse);

                        recyclerView_mealList.setLayoutManager(new LinearLayoutManager(TodayMenuActivity.this, LinearLayoutManager.VERTICAL, false));

                        recyclerView_mealList.setAdapter(recAdapter);

                        recAdapter.notifyDataSetChanged();

                        if (response.body().size() == 0) {
                            textView_noMeals.setVisibility(View.VISIBLE);
                        }

                    } else {
                        //response body is null
                    }

                } else {
                    //response not successful
                }

            }

            @Override
            public void onFailure(Call<List<ResTodayMeals>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private String getTodayDate() {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("dd-MM-yyyy");
        Date today_date = new Date();

        return simpledateformat.format(today_date);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }


//--------------------------------------------- APIs --------------------------------------------//

    GetTodayMealAPI getGetTodayMealAPIService(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetTodayMealAPI.class);
    }

    interface GetTodayMealAPI {
        @Headers("X-Requested-With:XMLHttpRequest")
        @POST("getUserMeal")
        @FormUrlEncoded
        Call<List<ResTodayMeals>> get_TodayMeals(@Header("Authorization") String token,
                                                 @Field("meal_date") String meal_date
        );
    }

//----------------------------------------- Adapter Class ---------------------------------------//

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
            private final TextView textView_name, textView_meal_category, textView_meal_time;
            private final TextView tv_protein, tv_fats, tv_carbs, tv_caloreis;

            ViewHolder(View v) {
                super(v);

                cardView_main = (CardView) v.findViewById(R.id.row_todayMenu_list_card);
                textView_name = (TextView) v.findViewById(R.id.row_todayMenu_tv_name);
                textView_meal_category = (TextView) v.findViewById(R.id.row_todayMenu_tv_category);
                textView_meal_time = (TextView) v.findViewById(R.id.row_todayMenu_tv_time);
                tv_protein = (TextView) v.findViewById(R.id.row_todayMenu_tv_proteins);
                tv_fats = (TextView) v.findViewById(R.id.row_todayMenu_tv_fats);
                tv_carbs = (TextView) v.findViewById(R.id.row_todayMenu_tv_carbs);
                tv_caloreis = (TextView) v.findViewById(R.id.row_todayMenu_tv_calories);

            }

            TextView getTextView_name() {
                return textView_name;
            }


        }

    }

}
