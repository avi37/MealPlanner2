package com.example.admin.mealplanner2new.Views;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.admin.mealplanner2new.Common.RetrofitClient;
import com.example.admin.mealplanner2new.Common.SessionManager;
import com.example.admin.mealplanner2new.Fragments.ExDashboardFragment;
import com.example.admin.mealplanner2new.Models.ResCommon;
import com.example.admin.mealplanner2new.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;

public class ExNavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    SessionManager sessionManager;

    LogoutAPI logoutAPI;
    private static final String BASE_URL = "http://code-fuel.in/healthbotics/api/auth/";

    NavigationView navigationView;
    View header_view;
    SwitchCompat switchCompat_dash;

    FragmentTransaction ft;

    String auth_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_navigation);

        sessionManager = new SessionManager(this);
        auth_token = sessionManager.getAccessToken();

        logoutAPI = getLogoutAPIService(BASE_URL);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        header_view = navigationView.getHeaderView(0);

        switchCompat_dash = header_view.findViewById(R.id.switch_ex);
        switchCompat_dash.setText("Switch Dashboard");
        switchCompat_dash.setChecked(true);


        ft = getSupportFragmentManager().beginTransaction();

        ft.add(R.id.content_ex_navigation, new ExDashboardFragment());
        ft.commit();
        navigationView.getMenu().getItem(0).setChecked(true);


        switchCompat_dash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent i = new Intent(ExNavigationActivity.this, DietMainNavigationActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ex_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav2_dashboard) {
            setTitle("Diet Manager");
            navigationView.getMenu().getItem(0).setChecked(true);

        } /*else if (id == R.id.nav2_my_profile) {
            startActivity(new Intent(this, MyProfileActivity.class));
            navigationView.getMenu().getItem(0).setChecked(true);

        }*/ else if (id == R.id.nav2_help) {
            startActivity(new Intent(this, ExHelpActivity.class));
            navigationView.getMenu().getItem(0).setChecked(true);

        } else if (id == R.id.nav2_logout) {
            showLogoutAlert();
            navigationView.getMenu().getItem(0).setChecked(true);

        } else if (id == R.id.nav2_quit) {
            System.exit(0);

        } else if (id == R.id.nav2_about_dev) {
            startActivity(new Intent(this, AboutDevActivity.class));
            navigationView.getMenu().getItem(0).setChecked(true);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void showLogoutAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Log out alert");
        alertDialog.setMessage("Are you sure you want to from your account?");

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                final ProgressDialog progressDialog = new ProgressDialog(ExNavigationActivity.this);
                progressDialog.setMessage("Loading");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                logoutAPI.logout_user("Bearer " + auth_token).enqueue(new Callback<ResCommon>() {
                                                                          @Override
                                                                          public void onResponse(Call<ResCommon> call, Response<ResCommon> response) {


                                                                              if (response.isSuccessful()) {

                                                                                  if (response.body() != null) {

                                                                                      if (response.body().getMsg().equals("true")) {

                                                                                          sessionManager.deleteSession();

                                                                                          finishAffinity();


                                                                                          Intent i = new Intent(ExNavigationActivity.this, GetStartedActivity.class);
                                                                                          i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                                          i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                                                                          Toast.makeText(ExNavigationActivity.this, "User Logged out", Toast.LENGTH_SHORT).show();

                                                                                          startActivity(i);

                                                                                      }

                                                                                  } else {
                                                                                      progressDialog.dismiss();
                                                                                      Toast.makeText(getApplicationContext(), "Some error occurred while logging you out \nPlease try after sometime", Toast.LENGTH_SHORT).show();
                                                                                  }

                                                                              } else {
                                                                                  progressDialog.dismiss();
                                                                                  Toast.makeText(getApplicationContext(), "Error in getting response", Toast.LENGTH_SHORT).show();
                                                                              }


                                                                          }

                                                                          @Override
                                                                          public void onFailure(Call<ResCommon> call, Throwable t) {
                                                                              progressDialog.dismiss();
                                                                              Toast.makeText(getApplicationContext(), "Error in getting response2", Toast.LENGTH_SHORT).show();
                                                                          }
                                                                      }

                );
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                navigationView.getMenu().getItem(0).setChecked(true);
            }
        });
        alertDialog.show();

    }


//------------------------------------------ APIs ------------------------------------------------//

    LogoutAPI getLogoutAPIService(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(LogoutAPI.class);
    }

    interface LogoutAPI {
        @GET("logout")
        Call<ResCommon> logout_user(@Header("Authorization") String auth_token);
    }

}
