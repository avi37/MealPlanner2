package com.example.admin.mealplanner2new.Views;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.mealplanner2new.Adapters.AdapterSelectIngredItem;
import com.example.admin.mealplanner2new.Common.RetrofitClient;
import com.example.admin.mealplanner2new.Models.ResSelectItems;
import com.example.admin.mealplanner2new.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class SelectIngredItemActivity extends AppCompatActivity {

    GetItemsAPI getItemsAPI;
    private final String BASE_URL = "http://www.code-fuel.in/mealplanner/";

    TextView textView_no_items;
    ProgressBar progressBar;
    ListView listView_ingredItems;

    AdapterSelectIngredItem adapterSelectIngredItem;

    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ingred_item);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        category = getIntent().getExtras().getString("category");
        getItemsAPI = getItemsAPIService(BASE_URL);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        textView_no_items = findViewById(R.id.selectIngredItems_progressbar_tv_noItems);
        progressBar = findViewById(R.id.selectIngredItems_progressbar);
        listView_ingredItems = findViewById(R.id.listView_ingredItems);

        textView_no_items.setVisibility(View.GONE);
        get_All_ingredItems(category);

    }


    private void get_All_ingredItems(String category) {
        progressBar.setVisibility(View.VISIBLE);

        getItemsAPI.getItems(category).enqueue(new Callback<List<ResSelectItems>>() {
            @Override
            public void onResponse(Call<List<ResSelectItems>> call, Response<List<ResSelectItems>> response) {

                if (response.isSuccessful()) {

                    if (response.body() != null) {

                        if (response.body().size() == 0) {
                            textView_no_items.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        } else {

                            String[] item_id = new String[response.body().size()];
                            String[] item_name = new String[response.body().size()];
                            String[] item_thumbImg = new String[response.body().size()];
                            String[] item_realImg = new String[response.body().size()];

                            for (int i = 0; i < response.body().size(); i++) {
                                item_id[i] = response.body().get(i).getId();
                                item_name[i] = response.body().get(i).getName();
                                item_thumbImg[i] = response.body().get(i).getThumb();
                                item_realImg[i] = response.body().get(i).getImg();
                            }

                            adapterSelectIngredItem = new AdapterSelectIngredItem(getApplicationContext(), R.layout.row_list_ingred_items, item_id, item_name, item_thumbImg, item_realImg);
                            listView_ingredItems.setAdapter(adapterSelectIngredItem);

                            adapterSelectIngredItem.notifyDataSetChanged();

                            progressBar.setVisibility(View.INVISIBLE);

                        }

                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Response body is null", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Response not successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ResSelectItems>> call, Throwable t) {
                textView_no_items.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "onFailure", Toast.LENGTH_SHORT).show();
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


//---------------------------------- APIs ---------------------------------------------//


    GetItemsAPI getItemsAPIService(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetItemsAPI.class);
    }

    interface GetItemsAPI {
        @POST("ingredients/get_ingredients_api/")
        @FormUrlEncoded
        Call<List<ResSelectItems>> getItems(@Field("category") String category);
    }

}
