package com.example.admin.mealplanner2new.Views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.mealplanner2new.Common.RetrofitClient;
import com.example.admin.mealplanner2new.Models.BodySignUp;
import com.example.admin.mealplanner2new.Models.ResCommon;
import com.example.admin.mealplanner2new.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class SignUpActivity extends AppCompatActivity {

    //http://127.0.0.1:8000/api/auth/
    SignUpAPI signUpAPI;
    private static final String BASE_URL = "http://192.168.0.106/meal/public/api/auth/";


    EditText editText_name, editText_email, editText_number, editText_password1, editText_password2;
    Spinner spinner_date, spinner_month, spinner_year;
    Button button_signup;
    TextView textView_login_here;

    String name, email, number, password1, password2, dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpAPI = getSignUpAPIService(BASE_URL);

        editText_name = findViewById(R.id.signUp_et_name);
        editText_email = findViewById(R.id.signUp_et_email);
        editText_number = findViewById(R.id.signUp_et_number);
        editText_password1 = findViewById(R.id.signUp_et_password1);
        editText_password2 = findViewById(R.id.signUp_et_password2);
        spinner_date = findViewById(R.id.signUp_spinnerDOB_date);
        spinner_month = findViewById(R.id.signUp_spinnerDOB_month);
        spinner_year = findViewById(R.id.signUp_spinnerDOB_year);
        button_signup = findViewById(R.id.signUp_btn_signUp);
        textView_login_here = findViewById(R.id.signUp_tv_login_here);

        initDateSpinner();
        initMonthSpinner();
        initYearSpinner();

        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodSignUp();
            }
        });

        textView_login_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });


    }


    private void initDateSpinner() {
        List<String> date = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            date.add(String.valueOf(i));
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, date);

        // Drop down layout style - list view with radio button
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_date.setAdapter(dateAdapter);
    }

    private void initMonthSpinner() {
        List<String> month = new ArrayList<>();
        month.add("January");
        month.add("February");
        month.add("March");
        month.add("April");
        month.add("May");
        month.add("June");
        month.add("July");
        month.add("August");
        month.add("September");
        month.add("October");
        month.add("November");
        month.add("December");

        // Creating adapter for spinner
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, month);

        // Drop down layout style - list view with radio button
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_month.setAdapter(monthAdapter);
    }

    private void initYearSpinner() {
        List<String> year = new ArrayList<>();
        for (int i = 1985; i <= 2016; i++) {
            year.add(String.valueOf(i));
        }

        // Creating adapter for spinner
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, year);

        // Drop down layout style - list view with radio button
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_year.setAdapter(yearAdapter);
    }


    private void methodSignUp() {

        name = editText_name.getText().toString();
        email = editText_email.getText().toString();
        number = editText_number.getText().toString();
        password1 = editText_password1.getText().toString();
        password2 = editText_password2.getText().toString();

        if (name.equals("")) {
            editText_name.setError("Name is required");
            editText_name.requestFocus();
        } else if (name.length() < 3) {
            editText_name.setError("Enter a valid name");
            editText_name.requestFocus();
        } else if (email.equals("")) {
            editText_email.setError("E-mail is required");
            editText_email.requestFocus();
        } else if (!isValidEmail(email)) {
            editText_email.setError("Enter a valid e-mail address");
            editText_email.requestFocus();
        } else if (number.equals("")) {
            editText_number.setError("Number is required");
            editText_number.requestFocus();
        } else if (!isValidNumber(number)) {
            editText_number.setError("Enter a valid number");
            editText_number.requestFocus();
        } else if (password1.equals("")) {
            editText_password1.setError("Password is required");
            editText_password1.requestFocus();
        } else if (password1.length() < 8) {
            editText_password1.setError("Length of password must be >=8 characters");
            editText_password1.requestFocus();
        } else if (password2.equals("")) {
            editText_password2.setError("Confirmation of password is required");
            editText_password2.requestFocus();
        } else if (!(password1.equals(password2))) {
            editText_password2.setError("Passwords must be same");
            editText_password2.requestFocus();
        } else {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            String int_month = getMonthNumber();
            dob = spinner_year.getSelectedItem().toString() + "-" + int_month + "-" + spinner_date.getSelectedItem().toString();

            BodySignUp bodySignUp = new BodySignUp(name, email, password1, password2, number, dob, "");

            signUpAPI.signUp_user(bodySignUp).enqueue(new Callback<ResCommon>() {
                @Override
                public void onResponse(Call<ResCommon> call, Response<ResCommon> response) {

                    if (response.isSuccessful()) {
                        if (response.body() != null) {

                            if (response.body().getMsg().equals("true")) {
                                progressDialog.dismiss();
                                finish();
                                startActivity(new Intent(SignUpActivity.this, DietMainNavigationActivity.class));

                            } else if (response.body().getMsg().equals("The given data was invalid.")) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "You are already registered. \nPlease Log in.", Toast.LENGTH_SHORT).show();

                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Some error occurred while signing you up \nPlease try after sometime", Toast.LENGTH_SHORT).show();
                            }

                        }

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Error in getting response", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ResCommon> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Connection error", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    private boolean isValidEmail(String email) {
        Pattern p;
        Matcher m;

        String EMAIL_STRING = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        p = Pattern.compile(EMAIL_STRING);
        m = p.matcher(email);

        return m.matches();
    }

    private boolean isValidNumber(String number) {
        Pattern p;
        Matcher m;
        String NUMBER_STRING = "^[6-9]\\d{9}$";
        p = Pattern.compile(NUMBER_STRING);
        m = p.matcher(number);
        return m.matches();
    }

    private String getMonthNumber() {
        String d = null;
        switch (spinner_month.getSelectedItem().toString()) {
            case "January":
                d = "01";
                break;

            case "February":
                d = "02";
                break;

            case "March":
                d = "03";
                break;

            case "April":
                d = "04";
                break;

            case "May":
                d = "05";
                break;

            case "June":
                d = "06";
                break;

            case "July":
                d = "07";
                break;

            case "August":
                d = "08";
                break;

            case "September":
                d = "09";
                break;

            case "October":
                d = "10";
                break;

            case "November":
                d = "11";
                break;

            case "December":
                d = "12";
                break;
        }
        return d;
    }

//---------------------------------------- APIs -------------------------------------------------//


    SignUpAPI getSignUpAPIService(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(SignUpAPI.class);
    }

    interface SignUpAPI {
        @POST("signup")
        Call<ResCommon> signUp_user(@Body BodySignUp bodySignUp);
    }


}
