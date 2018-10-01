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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.mealplanner2new.Common.RetrofitClient;
import com.example.admin.mealplanner2new.Models.ModelYogaList;
import com.example.admin.mealplanner2new.Models.ResCommon;
import com.example.admin.mealplanner2new.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class YogaListActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://code-fuel.in/healthbotics/api/auth/";
    GetYogaListAPI getYogaListAPI;

    RecyclerView recyclerView_yogaList;
    ProgressBar progressBar;

    AdapterYogaList adapterYogaList;
    private ArrayList<ModelYogaList> resYogaArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga_list);

        getYogaListAPI = getGetYogaListAPIService(BASE_URL);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView_yogaList = findViewById(R.id.yogaList_revView);
        progressBar = findViewById(R.id.yogaList_progressBar);

        getYogaList();
    }

    private void getYogaList() {
        progressBar.setVisibility(View.VISIBLE);

        resYogaArrayList = new ArrayList<>();

        resYogaArrayList.add(new ModelYogaList("Crescent Lunge", R.drawable.yoga_crescent_lunge));
        resYogaArrayList.add(new ModelYogaList("Warrior Pose", R.drawable.yoga_warrior_pose));
        resYogaArrayList.add(new ModelYogaList("Cobra Pose", R.drawable.yoga_cobra_pose));
        resYogaArrayList.add(new ModelYogaList("Camel Pose", R.drawable.yoga_camel_pose));
        resYogaArrayList.add(new ModelYogaList("Pigeon Pose", R.drawable.yoga_pigeon_pose));
        resYogaArrayList.add(new ModelYogaList("One Legged-King Pigeon", R.drawable.yoga_pigeon_king));
        resYogaArrayList.add(new ModelYogaList("Low Lunge", R.drawable.yoga_low_lunge));
        resYogaArrayList.add(new ModelYogaList("Bow Pose", R.drawable.yoga_bow_pose));
        resYogaArrayList.add(new ModelYogaList("Downward Facing Dog Pose", R.drawable.yoga_downward_dog));
        resYogaArrayList.add(new ModelYogaList("Warrior Pose on One Foot", R.drawable.yoga_warrior_on_one_leg));

        adapterYogaList = new AdapterYogaList(this, resYogaArrayList);

        recyclerView_yogaList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView_yogaList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        recyclerView_yogaList.setAdapter(adapterYogaList);
        adapterYogaList.notifyDataSetChanged();

        progressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }


//---------------------------------------- APIs ----------------------------------------------//

    GetYogaListAPI getGetYogaListAPIService(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetYogaListAPI.class);
    }

    interface GetYogaListAPI {
        @Headers("X-Requested-With:XMLHttpRequest")
        @POST("getYogaList")
        @FormUrlEncoded
        Call<List<ResCommon>> get_yogaList(@Header("Authorization") String token);
    }

//----------------------------------- Adapter Class -----------------------------------------//

    public class AdapterYogaList extends RecyclerView.Adapter<AdapterYogaList.MyViewHolder> {

        String BASE_IMG_URL = "http://code-fuel.in/healthbotics/storage/app/public/thumb/";

        Context context;
        private ArrayList<ModelYogaList> yogaArrayList;


        public AdapterYogaList(Context context, ArrayList<ModelYogaList> yogaArrayList) {
            this.yogaArrayList = yogaArrayList;
            this.context = context;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_yoga, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {

            /*String img_uri = BASE_IMG_URL + (yogaArrayList.get(position).getyThumb());
            Glide.with(getApplicationContext()).load(img_uri).into(holder.iv_thumb);*/
            holder.iv_thumb.setImageResource(yogaArrayList.get(position).getDrwable_image());
            holder.tv_name.setText(yogaArrayList.get(position).getName());

            holder.linearLayout_rowItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(YogaListActivity.this, YogaItemDetailActivity.class);
                    i.putExtra("name", yogaArrayList.get(position).getName());
                    i.putExtra("imageUrl", yogaArrayList.get(position).getDrwable_image());

                    startActivity(i);
                }
            });

        }

        @Override
        public int getItemCount() {
            return yogaArrayList.size();
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
