package com.example.admin.mealplanner2new.Views;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.admin.mealplanner2new.Common.RetrofitClient;
import com.example.admin.mealplanner2new.Common.SessionManager;
import com.example.admin.mealplanner2new.Models.ResCommon;
import com.example.admin.mealplanner2new.R;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class AddNewMealTimeActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://code-fuel.in/healthbotics/api/auth/";
    SaveMealCategoryAPI saveMealCategoryAPI;

    SessionManager sessionManager;

    ProgressBar progressBar;
    EditText editText_name;
    TextView textView_selectedTime;
    Button button_editTime, button_save;

    String name, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_meal_time);

        saveMealCategoryAPI = getSaveMealCategoryAPIService(BASE_URL);

        sessionManager = new SessionManager(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.addNewMealTime_progressbar);
        editText_name = findViewById(R.id.addNewMealTime_et_name);
        textView_selectedTime = findViewById(R.id.addNewMealTime_tv_time);
        button_editTime = findViewById(R.id.addNewMealTime_btn_editTime);
        button_save = findViewById(R.id.addNewMealTime_btn_save);


        button_editTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });


        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                methodSaveData();

            }
        });

    }

    private void methodSaveData() {
        name = editText_name.getText().toString();
        time = textView_selectedTime.getText().toString();

        if (name.equals("")) {
            editText_name.setError("Name required");
            editText_name.requestFocus();

        } else {
            progressBar.setVisibility(View.VISIBLE);

            String token = sessionManager.getAccessToken();

            saveMealCategoryAPI.save_categoryDetails("Bearer " + token, name, time).enqueue(new Callback<ResCommon>() {
                @Override
                public void onResponse(Call<ResCommon> call, Response<ResCommon> response) {

                    progressBar.setVisibility(View.GONE);

                    if (response.isSuccessful()) {

                        if (response.body() != null) {

                            switch (response.body().getMsg()) {
                                case "true":
                                    clearAllFields();
                                    Toast.makeText(AddNewMealTimeActivity.this, "Your meal category with time is saved.", Toast.LENGTH_LONG).show();
                                    break;

                                case "The given data was invalid":
                                    Toast.makeText(AddNewMealTimeActivity.this, "This meal category name or time is already exist,\nPlease enter unique data", Toast.LENGTH_LONG).show();
                                    break;

                                default:
                                    Toast.makeText(AddNewMealTimeActivity.this, "Error while saving...\nPlease try after sometime", Toast.LENGTH_LONG).show();
                                    break;

                            }

                        } else {
                            // response body is null
                        }

                    } else {
                        // response not successful
                    }

                }

                @Override
                public void onFailure(Call<ResCommon> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                }
            });


        }

    }

    private void clearAllFields() {

        editText_name.setText("");
        editText_name.requestFocus();
        textView_selectedTime.setText("12:12");

    }

    private void showTimePicker() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                textView_selectedTime.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }


//-------------------------------------- APIs -------------------------------------------------//

    SaveMealCategoryAPI getSaveMealCategoryAPIService(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(SaveMealCategoryAPI.class);
    }

    interface SaveMealCategoryAPI {
        @Headers("X-Requested-With:XMLHttpRequest")
        @POST("createMealCat")
        @FormUrlEncoded
        Call<ResCommon> save_categoryDetails(@Header("Authorization") String token,
                                             @Field("cat_name") String cat_name,
                                             @Field("cat_time") String cat_time
        );
    }

}
