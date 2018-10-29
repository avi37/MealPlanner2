package com.example.admin.mealplanner2new.Views;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.mealplanner2new.Models.ModelReportResultList;
import com.example.admin.mealplanner2new.Models.ResReportFields;
import com.example.admin.mealplanner2new.Models.ResReportHistory;
import com.example.admin.mealplanner2new.R;

import java.util.ArrayList;

public class ReportHistoryDetailActivity extends AppCompatActivity {

    TextView tv_date, tv_reportName, tv_doctorName, tv_labName;
    RecyclerView recyclerView_data;

    ArrayList<ResReportHistory.ValueData> arrayList_dataList = new ArrayList();
    RecAdapter recAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_history_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        tv_date = findViewById(R.id.reportHistoryDetail_tv_date);
        tv_reportName = findViewById(R.id.reportHistoryDetail_tv_reportName);
        tv_doctorName = findViewById(R.id.reportHistoryDetail_tv_doctorName);
        tv_labName = findViewById(R.id.reportHistoryDetail_tv_labName);
        recyclerView_data = findViewById(R.id.reportHistoryDetail_recView_data);

        tv_date.setText(getIntent().getStringExtra("date"));
        tv_reportName.setText(getIntent().getStringExtra("report_name"));
        tv_doctorName.setText(getIntent().getStringExtra("doctor_name"));
        tv_labName.setText(getIntent().getStringExtra("lab_name"));


        Bundle args = getIntent().getBundleExtra("BUNDLE");
        arrayList_dataList = (ArrayList<ResReportHistory.ValueData>) args.getSerializable("DataList");

        recAdapter = new RecAdapter(arrayList_dataList);

        recyclerView_data.setLayoutManager(new LinearLayoutManager(ReportHistoryDetailActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView_data.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView_data.setAdapter(recAdapter);

        recAdapter.notifyDataSetChanged();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }


//--------------------------------------- Adapter Class ---------------------------------------//

    public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {

        private ArrayList<ResReportHistory.ValueData> mDataSet;


        RecAdapter(ArrayList<ResReportHistory.ValueData> mDataSet) {
            this.mDataSet = mDataSet;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_report_history_data, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            viewHolder.getTextView_name().setText(mDataSet.get(position).getLabelName());

            viewHolder.getTextView_result().setText(mDataSet.get(position).getValue());

        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private final TextView textView_name, textView_result;

            ViewHolder(View v) {
                super(v);

                textView_name = (TextView) v.findViewById(R.id.row_reportHistoryData_tv_paramName);
                textView_result = (TextView) v.findViewById(R.id.row_reportHistoryData_tv_result);

            }

            TextView getTextView_name() {
                return textView_name;
            }

            public TextView getTextView_result() {
                return textView_result;
            }
        }

    }

}
