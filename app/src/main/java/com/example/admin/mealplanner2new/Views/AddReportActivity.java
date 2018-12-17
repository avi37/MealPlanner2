package com.example.admin.mealplanner2new.Views;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.mealplanner2new.Common.RetrofitClient;
import com.example.admin.mealplanner2new.Common.SessionManager;
import com.example.admin.mealplanner2new.Models.BodyCreateReport;
import com.example.admin.mealplanner2new.Models.ResCommon;
import com.example.admin.mealplanner2new.Models.ResReDoLa;
import com.example.admin.mealplanner2new.Models.ResReportFields;
import com.example.admin.mealplanner2new.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class AddReportActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://code-fuel.in/healthbotics/api/auth/";
    GetReportTypesAPI getReportTypesAPI;
    GetReportFieldsAPI getReportFieldsAPI;
    CreateReportAPI createReportAPI;
    SessionManager sessionManager;

    Spinner spinner_doctorName, spinner_labName, spinner_reportType;
    TextView textView_paramName, textView_yourResult;
    LinearLayout ll_fieldsHeader;
    RecyclerView recyclerView_dataEntries;
    Button button_submit;

    ArrayList<String> arrayList_reportId, arrayList_reportName;
    ArrayList<String> arrayList_doctorId, arrayList_doctorName;
    ArrayList<String> arrayList_labId, arrayList_labName;
    ArrayAdapter<String> adapter_doctorName, adapter_labName, adapter_reportName;

    ArrayList<ResReportFields> arrayList_reportFields;
    RecAdapter recAdapter;


    private String token, user_id, doctor_id, lab_id, report_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);

        getReportTypesAPI = getGetReportTypesAPIService(BASE_URL);
        getReportFieldsAPI = getGetReportFieldsAPIService(BASE_URL);
        createReportAPI = getCreateReportAPIService(BASE_URL);
        sessionManager = new SessionManager(this);

        token = sessionManager.getAccessToken();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        spinner_doctorName = findViewById(R.id.addReport_spinner_doctorName);
        spinner_labName = findViewById(R.id.addReport_spinner_labName);
        spinner_reportType = findViewById(R.id.addReport_spinner_reportType);
        ll_fieldsHeader = findViewById(R.id.addReport_constraint_header);
        textView_paramName = findViewById(R.id.addReport_tv_paramName);
        textView_yourResult = findViewById(R.id.addReport_tv_result);
        recyclerView_dataEntries = findViewById(R.id.addReport_recView_dataEntries);
        button_submit = findViewById(R.id.addReport_btn_submit);


        spinner_reportType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getReportFields(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodSubmit();
            }
        });

        getReportTypes();

    }


    private void getReportTypes() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        getReportTypesAPI.get_reportTypes( /*"Bearer " + token*/).enqueue(new Callback<ResReDoLa>() {
            @Override
            public void onResponse(Call<ResReDoLa> call, Response<ResReDoLa> response) {

                progressDialog.dismiss();

                if (response.isSuccessful()) {

                    if (response.body() != null) {

                        ll_fieldsHeader.setVisibility(View.VISIBLE);

                        arrayList_reportId = new ArrayList<>();
                        arrayList_reportName = new ArrayList<>();
                        arrayList_doctorId = new ArrayList<>();
                        arrayList_doctorName = new ArrayList<>();
                        arrayList_labId = new ArrayList<>();
                        arrayList_labName = new ArrayList<>();

                        for (int i = 0; i < response.body().getDoctor().size(); i++) {
                            arrayList_doctorId.add(response.body().getDoctor().get(i).getId());
                            arrayList_doctorName.add(response.body().getDoctor().get(i).getHospitalName());
                        }

                        for (int i = 0; i < response.body().getLab().size(); i++) {
                            arrayList_labId.add(response.body().getLab().get(i).getId());
                            arrayList_labName.add(response.body().getLab().get(i).getLabName());
                        }

                        for (int i = 0; i < response.body().getReport().size(); i++) {
                            arrayList_reportId.add(response.body().getReport().get(i).getId());
                            arrayList_reportName.add(response.body().getReport().get(i).getReportName());
                        }

                        adapter_doctorName = new ArrayAdapter<>(AddReportActivity.this, android.R.layout.simple_spinner_item, arrayList_doctorName);
                        spinner_doctorName.setAdapter(adapter_doctorName);

                        adapter_labName = new ArrayAdapter<>(AddReportActivity.this, android.R.layout.simple_spinner_item, arrayList_labName);
                        spinner_labName.setAdapter(adapter_labName);

                        adapter_reportName = new ArrayAdapter<>(AddReportActivity.this, android.R.layout.simple_spinner_item, arrayList_reportName);
                        spinner_reportType.setAdapter(adapter_reportName);

                    } else {
                        // response body null
                    }

                } else {
                    // response not successful
                }

            }

            @Override
            public void onFailure(Call<ResReDoLa> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }

    private void getReportFields(int position) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Getting fields...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        getReportFieldsAPI.get_reportFiedls("Bearer " + token, arrayList_reportId.get(position)).enqueue(new Callback<List<ResReportFields>>() {
            @Override
            public void onResponse(Call<List<ResReportFields>> call, Response<List<ResReportFields>> response) {

                progressDialog.dismiss();

                if (response.isSuccessful()) {

                    if (response.body() != null) {

                        arrayList_reportFields = new ArrayList<>();

                        arrayList_reportFields = (ArrayList<ResReportFields>) response.body();

                        recAdapter = new RecAdapter(arrayList_reportFields);

                        recyclerView_dataEntries.setLayoutManager(new LinearLayoutManager(AddReportActivity.this, LinearLayoutManager.VERTICAL, false));
                        recyclerView_dataEntries.setAdapter(recAdapter);

                        recAdapter.notifyDataSetChanged();

                    } else {
                        // response body is null
                    }


                } else {
                    // response not successful
                }

            }

            @Override
            public void onFailure(Call<List<ResReportFields>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }

    private void methodSubmit() {

        boolean allAdded = true;

        for (int i = 0; i < arrayList_reportFields.size(); i++) {

            if (arrayList_reportFields.get(i).getResult().isEmpty() || arrayList_reportFields.get(i).getResult().equals("")) {
                allAdded = false;
                break;
            }

        }


        if (allAdded) {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Saving your report...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();


            user_id = sessionManager.getKeyUId();
            doctor_id = arrayList_doctorId.get(spinner_doctorName.getSelectedItemPosition());
            lab_id = arrayList_labId.get(spinner_labName.getSelectedItemPosition());
            report_id = arrayList_reportId.get(spinner_reportType.getSelectedItemPosition());

            String token = sessionManager.getAccessToken();

            BodyCreateReport bodyCreateReport = new BodyCreateReport(user_id, doctor_id, lab_id, report_id, arrayList_reportFields);

            Log.d("LOG_BodyCreateReport", new Gson().toJson(bodyCreateReport));

            createReportAPI.create_report("Bearer " + token, bodyCreateReport).enqueue(new Callback<ResCommon>() {
                @Override
                public void onResponse(Call<ResCommon> call, Response<ResCommon> response) {

                    progressDialog.dismiss();

                    if (response.isSuccessful()) {

                        if (response.body() != null) {

                            if (response.body().getMsg().equals("true")) {

                                finish();

                                Toast.makeText(AddReportActivity.this, "Report added successfully", Toast.LENGTH_LONG).show();

                            }

                        } else {
                            // response body null
                        }

                    } else {
                        // response not successful
                    }


                }

                @Override
                public void onFailure(Call<ResCommon> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });



        } else {
            Toast.makeText(this, "Please fill all the values", Toast.LENGTH_LONG).show();

        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }


//---------------------------------------- APIs -----------------------------------------------//

    GetReportTypesAPI getGetReportTypesAPIService(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetReportTypesAPI.class);
    }

    interface GetReportTypesAPI {
        @Headers("X-Requested-With:XMLHttpRequest")
        @GET("getReDoLa")
        Call<ResReDoLa> get_reportTypes();
    }


    GetReportFieldsAPI getGetReportFieldsAPIService(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetReportFieldsAPI.class);
    }

    interface GetReportFieldsAPI {
        @Headers("X-Requested-With:XMLHttpRequest")
        @POST("getReportData")
        @FormUrlEncoded
        Call<List<ResReportFields>> get_reportFiedls(@Header("Authorization") String token,
                                                     @Field("id") String id
        );
    }

    CreateReportAPI getCreateReportAPIService(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(CreateReportAPI.class);
    }

    interface CreateReportAPI {
        @Headers("X-Requested-With:XMLHttpRequest")
        @POST("createUserReport")
        Call<ResCommon> create_report(@Header("Authorization") String token,
                                      @Body BodyCreateReport bodyCreateReport
        );
    }


//----------------------------------- Adapter Class -------------------------------------------//

    public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {

        private ArrayList<ResReportFields> mDataSet;


        RecAdapter(ArrayList<ResReportFields> mDataSet) {
            this.mDataSet = mDataSet;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_list_report_fields, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            viewHolder.getTextView_name().setText(mDataSet.get(position).getLabel_name());

            viewHolder.getEditText_result().setText(mDataSet.get(position).getResult());

        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private final TextView textView_name;
            private final EditText editText_result;


            //  ("id") ("label_name") ("min") ("max")

            ViewHolder(View v) {
                super(v);

                textView_name = (TextView) v.findViewById(R.id.row_reportFields_tv_name);
                editText_result = (EditText) v.findViewById(R.id.row_reportFields_et_result);

                getEditText_result().addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        String str = s.toString().trim();

                        try {
                            if (!str.isEmpty()) {

                                double value = Double.parseDouble(getEditText_result().getText().toString());
                                double max = Double.parseDouble(mDataSet.get(getAdapterPosition()).getMax());
                                double min = Double.parseDouble(mDataSet.get(getAdapterPosition()).getMin());

                                if (value < min || value > max) {
                                    getEditText_result().setTextColor(getResources().getColor(R.color.level_red4));
                                } else {
                                    getEditText_result().setTextColor(getResources().getColor(R.color.mainColorPrimaryDark));
                                }
                            }
                        } catch (Exception e) {
                            Toast.makeText(AddReportActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }

                        //listFieldData.add(getAdapterPosition(), new BodyCreateReport.Datum(mDataSet.get(getAdapterPosition()).getId(), str));

                        mDataSet.get(getAdapterPosition()).setResult(str);

                    }
                });

            }

            TextView getTextView_name() {
                return textView_name;
            }

            public EditText getEditText_result() {
                return editText_result;
            }
        }

    }

}
