package com.example.admin.mealplanner2new.Views;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.mealplanner2new.Common.RetrofitClient;
import com.example.admin.mealplanner2new.Common.SessionManager;
import com.example.admin.mealplanner2new.Fragments.FirstPhotoUploadFragment;
import com.example.admin.mealplanner2new.Models.ResCommon;
import com.example.admin.mealplanner2new.R;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class MyProfileActivity extends AppCompatActivity implements View.OnClickListener {

    CheckPhotoUploadedAPI checkPhotoUploadedAPI;
    SessionManager sessionManager;

    private final String BASE_URL = "http://code-fuel.in/healthbotics/api/auth/";
    /*private ChangePwdAPI changePwdAPI;
    private ChangeProfileAPI changeProfileAPI;*/

    CircleImageView profilePic;
    TextView textView_number, textView_userName, textView_editPwd;
    ImageView imageView_editName;
    TextView textView_uploadInfo;
    Button button_uploadPhoto;
    LinearLayout linearLayout_photoUpload;

    String number, username;

    FragmentTransaction ft;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        /*changePwdAPI = getPwdAPIService(BASE_URL);
        changeProfileAPI = getProfileAPIService(BASE_URL);*/
        checkPhotoUploadedAPI = getCheckPhotoUploadedAPIService(BASE_URL);

        sessionManager = new SessionManager(this);
        if (!sessionManager.getUserName().isEmpty()) {
            username = sessionManager.getUserName();
        } else {
            username = "";
        }


        profilePic = findViewById(R.id.myProfile_iv_profilePic);
        //textView_number = findViewById(R.id.myProfile_tv_number);
        textView_userName = findViewById(R.id.myProfile_tv_userName);
        imageView_editName = findViewById(R.id.myProfile_iv_updateUserName);
        textView_editPwd = findViewById(R.id.myProfile_tv_changePwd);
        textView_uploadInfo = findViewById(R.id.myProfile_tv_info1);
        button_uploadPhoto = findViewById(R.id.myProfile_btn_uploadPhoto);
        linearLayout_photoUpload = findViewById(R.id.myProfile_ll_photoUpload);

        setUserData();

        imageView_editName.setOnClickListener(this);
        textView_editPwd.setOnClickListener(this);
        button_uploadPhoto.setOnClickListener(this);

    }


    private void checkForFirstPhoto() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Getting your profile details...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String token = sessionManager.getAccessToken();

        checkPhotoUploadedAPI.checkPhotoUploaded("Bearer " + token, sessionManager.getKeyUId()).enqueue(new Callback<ResCommon>() {
            @Override
            public void onResponse(Call<ResCommon> call, Response<ResCommon> response) {

                progressDialog.dismiss();

                if (response.isSuccessful()) {

                    if (response.body() != null) {

                        if (response.body().getMsg().equals("true")) {
                            linearLayout_photoUpload.setClickable(false);
                            linearLayout_photoUpload.setVisibility(View.GONE);
                        }

                    }

                }

            }

            @Override
            public void onFailure(Call<ResCommon> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }

    private void setUserData() {
        //textView_number.setText("+91 " + number);
        textView_userName.setText(username);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.myProfile_iv_updateUserName:
                //updateUserName();
                break;

            case R.id.myProfile_tv_changePwd:
                //changePassword();
                break;

            case R.id.myProfile_btn_uploadPhoto:
                methodUploadPhoto();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkForFirstPhoto();
    }


    private void methodUploadPhoto() {

        ft = getFragmentManager().beginTransaction();

        ft.add(R.id.activity_my_profile, new FirstPhotoUploadFragment(), "PhotoUploadFragment");
        ft.addToBackStack(null);
        ft.commit();
    }

    /*private void updateUserName() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Are you sure?");
        alertDialog.setMessage("Are you sure you want to change your Username?");

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                changeProfileApiMethod();
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    private void changeProfileApiMethod() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.change_profile_dialog_layout, null);

        final EditText et_newUserName = alertLayout.findViewById(R.id.change_pro_et_new_userName);

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(alertLayout)
                .setTitle("Change Username")
                .setPositiveButton("Submit", null)   //Set to null. Will be overridden while the onclick
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {

                Button button_Submit = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                Button button_Cancel = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);

                button_Submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final boolean[] wantToCloseDialog = {false};
                        String new_user_name = et_newUserName.getText().toString();

                        if (!(new_user_name.equals(""))) {
                            changeProfileAPI.changeProfile(sessionManager.getUserid(), sessionManager.getUserRole(), new_user_name).enqueue(new Callback<MyRes>() {
                                @Override
                                public void onResponse(@NonNull Call<MyRes> call, @NonNull Response<MyRes> response) {
                                    if (response.isSuccessful()) {
                                        if (response.body() != null && response.body().getMsg().equalsIgnoreCase("true")) {
                                            wantToCloseDialog[0] = true;
                                            Toast.makeText(getApplicationContext(), "Your Username changed successfully", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Response body is null \nError in getting message", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Response not successful", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<MyRes> call, @NonNull Throwable t) {
                                    Toast.makeText(getApplicationContext(), "Error in getting response \n Internet Error", Toast.LENGTH_LONG).show();
                                }
                            });

                        } else {
                            et_newUserName.setError("Please fill this field");
                            et_newUserName.requestFocus();
                        }

                        if (wantToCloseDialog[0]) {
                            dialog.dismiss();
                        }

                    }
                });

                button_Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }


    private void changePassword() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Are you sure?");
        alertDialog.setMessage("Are you sure you want to change your password?");

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                changePasswordApiMethod();
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();

    }

    private void changePasswordApiMethod() {

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.change_pwd_dialog_layout, null);

        final EditText et_newPwd1 = alertLayout.findViewById(R.id.change_pwd_et_new_pwd1);
        final EditText et_newPwd2 = alertLayout.findViewById(R.id.change_pwd_et_new_pwd2);

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Change Password");
        alert.setView(alertLayout);
        alert.setCancelable(false);

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(alertLayout)
                .setTitle("Change Password")
                .setPositiveButton("Submit", null)   //Set to null. Will be overridden while the onclick
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {

                Button button_Submit = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                Button button_Cancel = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);

                button_Submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final boolean[] wantToCloseDialog = {false};

                        String new_pwd1, new_pwd2;
                        new_pwd1 = et_newPwd1.getText().toString();
                        new_pwd2 = et_newPwd2.getText().toString();

                        if (!(new_pwd1.equals("") || new_pwd2.equals(""))) {
                            if (new_pwd2.equals(new_pwd1)) {
                                changePwdAPI.changePwd(sessionManager.getUserid(), sessionManager.getUserRole(), new_pwd1).enqueue(new Callback<MyRes>() {
                                    @Override
                                    public void onResponse(@NonNull Call<MyRes> call, @NonNull Response<MyRes> response) {
                                        if (response.isSuccessful()) {
                                            if (response.body() != null && response.body().getMsg().equalsIgnoreCase("true")) {
                                                wantToCloseDialog[0] = false;
                                                Toast.makeText(getApplicationContext(), "Your password changed successfully", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Response body is null \nError in getting message", Toast.LENGTH_LONG).show();
                                            }
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Response not successful", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<MyRes> call, @NonNull Throwable t) {
                                        Toast.makeText(getApplicationContext(), "Error in getting response \n Internet Error", Toast.LENGTH_LONG).show();
                                    }
                                });

                            } else {
                                et_newPwd2.setError("Both password must be same");
                                et_newPwd2.requestFocus();
                            }
                        } else {
                            if (et_newPwd1.getText().toString().equals("")) {
                                et_newPwd1.setError("Required");
                                et_newPwd1.requestFocus();
                            } else {
                                et_newPwd2.setError("Required");
                                et_newPwd2.requestFocus();
                            }
                        }


                        if (wantToCloseDialog[0]) {
                            dialog.dismiss();
                        }

                    }
                });

                button_Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        dialog.setCancelable(false);
        dialog.show();

    }
*/

//---------------------------------- APIs ------------------------------------------------------//


    /*ChangePwdAPI getPwdAPIService(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(ChangePwdAPI.class);
    }

    interface ChangePwdAPI {
        @POST("home/change_password_api/")
        @FormUrlEncoded
        Call<MyRes> changePwd(@Field("u_id") String u_id,
                              @Field("role") String role,
                              @Field("new_pass") String new_pwd
        );
    }


    ChangeProfileAPI getProfileAPIService(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(ChangeProfileAPI.class);
    }

    interface ChangeProfileAPI {
        @POST("home/edit_profile_api/")
        @FormUrlEncoded
        Call<MyRes> changeProfile(@Field("u_id") String u_id,
                                  @Field("role") String role,
                                  @Field("new_name") String new_user_name
        );
    }*/


    CheckPhotoUploadedAPI getCheckPhotoUploadedAPIService(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(CheckPhotoUploadedAPI.class);
    }

    interface CheckPhotoUploadedAPI {
        @Headers("X-Requested-With:XMLHttpRequest")
        @GET("getFirstUploadedPhoto")
        Call<ResCommon> checkPhotoUploaded(@Header("Authorization") String token,
                                           @Query("u_id") String u_id
        );
    }

}
