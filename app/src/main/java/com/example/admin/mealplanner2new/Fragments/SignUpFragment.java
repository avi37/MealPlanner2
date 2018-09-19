package com.example.admin.mealplanner2new.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.mealplanner2new.Common.PrefRegister;
import com.example.admin.mealplanner2new.Common.RetrofitClient;
import com.example.admin.mealplanner2new.Models.BodyRegister;
import com.example.admin.mealplanner2new.Models.ResCommon;
import com.example.admin.mealplanner2new.R;
import com.example.admin.mealplanner2new.Views.DietMainNavigationActivity;
import com.example.admin.mealplanner2new.Views.LoginActivity;
import com.google.gson.Gson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class SignUpFragment extends Fragment {

    PrefRegister prefRegister;

    View view_main;

    //http://127.0.0.1:8000/api/auth/
    SignUpAPI signUpAPI;
    private static final String BASE_URL = "http://code-fuel.in/healthbotics/api/auth/";


    EditText editText_name, editText_email, editText_number, editText_password1, editText_password2;
    Button button_finish;

    String name, email, number, password1, password2;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.fragment_sign_up, container, false);

        prefRegister = new PrefRegister(getContext());

        signUpAPI = getSignUpAPIService(BASE_URL);

        editText_name = view_main.findViewById(R.id.gs_signUp_et_name);
        editText_email = view_main.findViewById(R.id.gs_signUp_et_email);
        editText_number = view_main.findViewById(R.id.gs_signUp_et_number);
        editText_password1 = view_main.findViewById(R.id.gs_signUp_et_password1);
        editText_password2 = view_main.findViewById(R.id.gs_signUp_et_password2);
        button_finish = view_main.findViewById(R.id.gs_signUp_btn_finish);

        button_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodSignUp();
            }
        });

        return view_main;
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

            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Wait while we register you...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();


            String place, gender, aim, height, age, weight, tr_level, ex_level, schedule, week_minutes, ex_days;
            String coach_id, country, state, city;

            place = prefRegister.getWorkoutPlace();
            gender = prefRegister.getGENDER();
            aim = prefRegister.getAIM();
            height = prefRegister.getHEIGHT();
            age = prefRegister.getAGE();
            weight = prefRegister.getWEIGHT();
            tr_level = prefRegister.getTrainingLevel();
            ex_level = prefRegister.getPastExEx();
            schedule = prefRegister.getSCHEDULE();
            week_minutes = prefRegister.getWeekMinutes();
            ex_days = prefRegister.getExDays();

            coach_id = prefRegister.getCoachId();
            country = prefRegister.getCountry();
            state = prefRegister.getState();
            city = prefRegister.getCity();

            BodyRegister bodyRegister = new BodyRegister(place, gender, aim, height, age, weight, tr_level, ex_level, schedule, week_minutes, ex_days, coach_id, country, state, city, name, email, number, password1, password2);

            Log.d("LOG_BodyRegister", new Gson().toJson(bodyRegister));

            signUpAPI.register_user(bodyRegister).enqueue(new Callback<ResCommon>() {
                @Override
                public void onResponse(Call<ResCommon> call, Response<ResCommon> response) {

                    if (response.isSuccessful()) {

                        if (response.body() != null) {

                            if (response.body().getMsg().equals("true")) {
                                progressDialog.dismiss();
                                getActivity().finish();
                                startActivity(new Intent(getContext(), LoginActivity.class));

                            } else if (response.body().getMsg().equals("The given data was invalid.")) {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "You are already registered. \nPlease Log in.", Toast.LENGTH_SHORT).show();

                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Some error occurred while signing you up \nPlease try after sometime", Toast.LENGTH_SHORT).show();
                            }

                        }

                    } else if (response.code() == 422) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Error in getting response", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ResCommon> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Connection error", Toast.LENGTH_SHORT).show();
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


//------------------------------------- APIs ----------------------------------------------------//

    SignUpAPI getSignUpAPIService(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(SignUpAPI.class);
    }

    interface SignUpAPI {
        @Headers("X-Requested-With:XMLHttpRequest")
        @POST("signup")
        Call<ResCommon> register_user(@Body BodyRegister bodyRegister);
    }

}
