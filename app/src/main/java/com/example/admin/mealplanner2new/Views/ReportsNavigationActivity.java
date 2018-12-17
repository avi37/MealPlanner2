package com.example.admin.mealplanner2new.Views;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.mealplanner2new.Common.RetrofitClient;
import com.example.admin.mealplanner2new.Common.SessionManager;
import com.example.admin.mealplanner2new.Fragments.ReportsDashboardFragment;
import com.example.admin.mealplanner2new.Models.ResCommon;
import com.example.admin.mealplanner2new.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;

public class ReportsNavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    SessionManager sessionManager;

    LogoutAPI logoutAPI;
    private static final String BASE_URL = "http://code-fuel.in/healthbotics/api/auth/";

    NavigationView navigationView;
    View header_view;
    TextView textView_userName;

    FragmentTransaction ft;

    private String auth_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_navigation);

        sessionManager = new SessionManager(this);
        auth_token = sessionManager.getAccessToken();

        logoutAPI = getLogoutAPIService(BASE_URL);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.report_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        header_view = navigationView.getHeaderView(0);

        textView_userName = header_view.findViewById(R.id.exHeader_tv_userName);
        textView_userName.setText(sessionManager.getUserName());

        ft = getSupportFragmentManager().beginTransaction();

        ft.add(R.id.content_reports_navigation, new ReportsDashboardFragment());
        setTitle("Reports Manager");
        ft.commit();


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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.reports_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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

        if (id == R.id.nav3_dashboard) {
            setTitle("Diet Manager");
            navigationView.getMenu().getItem(0).setChecked(true);

        } else if (id == R.id.nav3_my_profile) {
            startActivity(new Intent(this, MyProfileActivity.class));
            navigationView.getMenu().getItem(0).setChecked(true);

        } else if (id == R.id.nav3_switchDash) {
            showSwitchDashAlert();
            navigationView.getMenu().getItem(0).setChecked(true);

        } else if (id == R.id.nav3_logout) {
            showLogoutAlert();
            navigationView.getMenu().getItem(0).setChecked(true);

        } else if (id == R.id.nav3_quit) {
            System.exit(0);

        } else if (id == R.id.nav3_about_dev) {
            startActivity(new Intent(this, AboutDevActivity.class));
            navigationView.getMenu().getItem(0).setChecked(true);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void showSwitchDashAlert() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Switch Dashboard");
        alertDialog.setMessage("To which Dashboard you want to go?");

        alertDialog.setPositiveButton("Exercise Manager", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();

                Intent i = new Intent(ReportsNavigationActivity.this, ExNavigationActivity.class);
                startActivity(i);
                finish();

            }
        });

        alertDialog.setNegativeButton("Diet Manager", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

                Intent i = new Intent(ReportsNavigationActivity.this, DietMainNavigationActivity.class);
                startActivity(i);
                finish();

            }
        });

        alertDialog.show();

    }

    private void showLogoutAlert() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Log out alert");
        alertDialog.setMessage("Are you sure you want to from your account?");

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                final ProgressDialog progressDialog = new ProgressDialog(ReportsNavigationActivity.this);
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


                                                                                          Intent i = new Intent(ReportsNavigationActivity.this, GetStartedActivity.class);
                                                                                          i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                                          i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                                                                          Toast.makeText(ReportsNavigationActivity.this, "User Logged out", Toast.LENGTH_SHORT).show();

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


//--------------------------------- APIs ---------------------------------//

    LogoutAPI getLogoutAPIService(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(LogoutAPI.class);
    }

    interface LogoutAPI {
        @GET("logout")
        Call<ResCommon> logout_user(@Header("Authorization") String auth_token);
    }

}
