package com.example.admin.mealplanner2new.Views;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.mealplanner2new.Common.RetrofitClient;
import com.example.admin.mealplanner2new.Common.SessionManager;
import com.example.admin.mealplanner2new.Models.ModelExList;
import com.example.admin.mealplanner2new.Models.ModelYogaList;
import com.example.admin.mealplanner2new.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class ExListOnCatActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://code-fuel.in/healthbotics/api/auth/";
    GetExListAPI getExListAPI;

    SessionManager sessionManager;

    RecyclerView recyclerView_exList;
    ProgressBar progressBar;
    TextView textView_noEx;

    private String cat_id;

    AdapterExList adapterExList;
    private ArrayList<ModelYogaList> resExArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_list_on_cat);

        cat_id = getIntent().getStringExtra("cat_id");      // coming from ExCatListActivity
        getExListAPI = getGetExListAPIService(BASE_URL);

        sessionManager = new SessionManager(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView_exList = findViewById(R.id.exListOnCat_revView);
        progressBar = findViewById(R.id.exListOnCat_progressBar);
        textView_noEx = findViewById(R.id.exListOnCat_tv_noEx);

        getAllExercises(cat_id);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getAllExercises(String cat_id) {
        progressBar.setVisibility(View.VISIBLE);

        String token = sessionManager.getAccessToken();


        getExListAPI.get_exerciseList("Bearer " + token, cat_id).enqueue(new Callback<List<ModelExList>>() {
            @Override
            public void onResponse(Call<List<ModelExList>> call, Response<List<ModelExList>> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    if (response.body() != null) {

                        if (response.body().size() > 0) {

                            adapterExList = new AdapterExList(ExListOnCatActivity.this, (ArrayList<ModelExList>) response.body());

                            recyclerView_exList.setLayoutManager(new LinearLayoutManager(ExListOnCatActivity.this, LinearLayoutManager.VERTICAL, false));
                            recyclerView_exList.addItemDecoration(new DividerItemDecoration(ExListOnCatActivity.this, DividerItemDecoration.VERTICAL));


                            recyclerView_exList.setAdapter(adapterExList);

                            adapterExList.notifyDataSetChanged();

                        } else {
                            textView_noEx.setVisibility(View.VISIBLE);
                        }


                    } else {
                        // response body is null
                    }

                } else {
                    // response not successful
                }

            }

            @Override
            public void onFailure(Call<List<ModelExList>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }


//---------------------------------------- APIs ----------------------------------------------//

    GetExListAPI getGetExListAPIService(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetExListAPI.class);
    }

    interface GetExListAPI {
        @Headers("X-Requested-With:XMLHttpRequest")
        @POST("getExercise")
        @FormUrlEncoded
        Call<List<ModelExList>> get_exerciseList(@Header("Authorization") String token,
                                                 @Field("id") String id
        );
    }

//----------------------------------- Adapter Class -----------------------------------------//

    public class AdapterExList extends RecyclerView.Adapter<AdapterExList.MyViewHolder> {

        String BASE_THUMB_URL = "http://code-fuel.in/healthbotics/storage/app/public/thumb/";
        String BASE_PHOTO_URL = "http://code-fuel.in/healthbotics/storage/app/public/exercise/";

        Context context;
        private ArrayList<ModelExList> exArrayList;


        public AdapterExList(Context context, ArrayList<ModelExList> exArrayList) {
            this.exArrayList = exArrayList;
            this.context = context;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_yoga, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

            String thumb_url = BASE_THUMB_URL + (exArrayList.get(position).getThumb());
            Glide.with(ExListOnCatActivity.this).load(thumb_url).into(holder.iv_thumb);

            holder.tv_name.setText(exArrayList.get(position).getName());

            String photo_url = BASE_PHOTO_URL + (exArrayList.get(position).getPhoto());

            holder.linearLayout_rowItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ExListOnCatActivity.this, ExDetailsActivity.class);
                    i.putExtra("id", exArrayList.get(position).getId());
                    i.putExtra("name", exArrayList.get(position).getName());
                    i.putExtra("thumb", thumb_url);
                    i.putExtra("photo", photo_url);
                    i.putExtra("description", exArrayList.get(position).getDescription());
                    i.putExtra("video_link", exArrayList.get(position).getVideo_link());

                    startActivity(i);
                }
            });

        }

        @Override
        public int getItemCount() {
            return exArrayList.size();
        }


        //----- Holder Class -----//

        class MyViewHolder extends RecyclerView.ViewHolder {

            LinearLayout linearLayout_rowItem;
            ImageView iv_thumb;
            TextView tv_name;

            MyViewHolder(View view) {
                super(view);
                linearLayout_rowItem = view.findViewById(R.id.row_list_yoga);
                iv_thumb = view.findViewById(R.id.row_yogaList_iv_thumb);
                tv_name = view.findViewById(R.id.row_yogaList_tv_name);
            }

        }

    }


}
