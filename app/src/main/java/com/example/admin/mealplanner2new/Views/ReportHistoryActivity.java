package com.example.admin.mealplanner2new.Views;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.admin.mealplanner2new.Common.RetrofitClient;
import com.example.admin.mealplanner2new.Common.SessionManager;
import com.example.admin.mealplanner2new.Models.ResReportHistory;
import com.example.admin.mealplanner2new.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class ReportHistoryActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://code-fuel.in/healthbotics/api/auth/";
    GetReportHistoryAPI getReportHistoryAPI;
    SessionManager sessionManager;


    RecyclerView recyclerView_reportHistory;
    TextView textView_noHistory;
    ProgressBar progressBar;

    ArrayList<ResReportHistory> apiResponse;
    RecAdapter recAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_history);

        getReportHistoryAPI = getGetReportHistoryAPIService(BASE_URL);
        sessionManager = new SessionManager(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView_reportHistory = findViewById(R.id.reportHistory_recView);
        textView_noHistory = findViewById(R.id.reportHistory_tv_noHistory);
        progressBar = findViewById(R.id.reportHistory_progressbar);

        getReportHistory();

    }

    private void getReportHistory() {
        progressBar.setVisibility(View.VISIBLE);

        String token = sessionManager.getAccessToken();

        getReportHistoryAPI.get_reportHistory("Bearer " + token).enqueue(new Callback<ArrayList<ResReportHistory>>() {
            @Override
            public void onResponse(Call<ArrayList<ResReportHistory>> call, Response<ArrayList<ResReportHistory>> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    if (response.body() != null) {

                        apiResponse = (ArrayList<ResReportHistory>) response.body();

                        recAdapter = new RecAdapter(apiResponse);

                        recyclerView_reportHistory.setLayoutManager(new LinearLayoutManager(ReportHistoryActivity.this, LinearLayoutManager.VERTICAL, false));
                        recyclerView_reportHistory.addItemDecoration(new DividerItemDecoration(ReportHistoryActivity.this, DividerItemDecoration.VERTICAL));
                        recyclerView_reportHistory.setAdapter(recAdapter);

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
            public void onFailure(Call<ArrayList<ResReportHistory>> call, Throwable t) {
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


//----------------------------------------- APIs --------------------------------------------//

    GetReportHistoryAPI getGetReportHistoryAPIService(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetReportHistoryAPI.class);
    }

    interface GetReportHistoryAPI {
        @Headers("X-Requested-With:XMLHttpRequest")
        @POST("getUserReport")
        Call<ArrayList<ResReportHistory>> get_reportHistory(@Header("Authorization") String token);
    }


//------------------------------------- Adapter Class --------------------------------------//

    public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {

        private ArrayList<ResReportHistory> mDataSet;


        RecAdapter(ArrayList<ResReportHistory> mDataSet) {
            this.mDataSet = mDataSet;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_list_report_history, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            viewHolder.getTextView_date().setText(mDataSet.get(position).getCreatedAt());
            viewHolder.getTextView_name().setText(mDataSet.get(position).getReportName());
            viewHolder.getTextView_doctorName().setText(mDataSet.get(position).getDoctorName());
            viewHolder.getTextView_labName().setText(mDataSet.get(position).getLabName());


            viewHolder.getLinearLayout_row().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ReportHistoryActivity.this, ReportHistoryDetailActivity.class);

                    i.putExtra("date", mDataSet.get(position).getCreatedAt());
                    i.putExtra("report_name", mDataSet.get(position).getReportName());
                    i.putExtra("doctor_name", mDataSet.get(position).getDoctorName());
                    i.putExtra("lab_name", mDataSet.get(position).getLabName());

                    startActivity(i);
                }
            });

        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private final LinearLayout linearLayout_row;
            private final TextView textView_date, textView_name, textView_doctorName, textView_labName;

            public LinearLayout getLinearLayout_row() {
                return linearLayout_row;
            }

            public TextView getTextView_date() {
                return textView_date;
            }

            public TextView getTextView_name() {
                return textView_name;
            }

            public TextView getTextView_doctorName() {
                return textView_doctorName;
            }

            public TextView getTextView_labName() {
                return textView_labName;
            }

            ViewHolder(View v) {
                super(v);

                linearLayout_row = (LinearLayout) v.findViewById(R.id.row_list_report_history);
                textView_date = (TextView) v.findViewById(R.id.row_reportHistory_tv_date);
                textView_name = (TextView) v.findViewById(R.id.row_reportHistory_tv_reportName);
                textView_doctorName = (TextView) v.findViewById(R.id.row_reportHistory_tv_doctorName);
                textView_labName = (TextView) v.findViewById(R.id.row_reportHistory_tv_labName);


            }


        }

    }


}
