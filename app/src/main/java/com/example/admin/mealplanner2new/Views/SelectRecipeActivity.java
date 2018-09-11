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

import com.example.admin.mealplanner2new.Adapters.AdapterSelectRecipe;
import com.example.admin.mealplanner2new.Common.RetrofitClient;
import com.example.admin.mealplanner2new.Models.ResSelectItems;
import com.example.admin.mealplanner2new.Models.ResSelectRecipe;
import com.example.admin.mealplanner2new.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class SelectRecipeActivity extends AppCompatActivity {

    GetRecipesAPI getRecipesAPI;
    private final String BASE_URL = "http://www.code-fuel.in/mealplanner/";

    TextView textView_no_items;
    ProgressBar progressBar;
    ListView listView_recipes;

    AdapterSelectRecipe adapterSelectRecipe;

    String ingred_id, ingred_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_recipe);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        ingred_id = getIntent().getExtras().getString("ingred_id");
        ingred_item = getIntent().getExtras().getString("ingred_item");

        getRecipesAPI = getRecipesAPIService(BASE_URL);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        textView_no_items = findViewById(R.id.selectRecipe_tv_noItems);
        progressBar = findViewById(R.id.selectRecipe_progressbar);
        listView_recipes = findViewById(R.id.listView_selectRecipe);

        textView_no_items.setVisibility(View.GONE);
        get_All_recipes();

    }

    private void get_All_recipes() {
        progressBar.setVisibility(View.VISIBLE);

        getRecipesAPI.getRecipes(ingred_id).enqueue(new Callback<List<ResSelectRecipe>>() {
            @Override
            public void onResponse(Call<List<ResSelectRecipe>> call, Response<List<ResSelectRecipe>> response) {

                if (response.isSuccessful()) {

                    if (response.body() != null) {

                        if (response.body().size() == 0) {
                            textView_no_items.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);

                        } else {

                            String[] recipe_name = new String[response.body().size()];
                            String[] recipe_thumbImg = new String[response.body().size()];
                            String[] recipe_realImg = new String[response.body().size()];

                            String[] recipe_proteins = new String[response.body().size()];
                            String[] recipe_fats = new String[response.body().size()];
                            String[] recipe_carbs = new String[response.body().size()];
                            String[] recipe_calories = new String[response.body().size()];

                            for (int i = 0; i < response.body().size(); i++) {
                                recipe_name[i] = response.body().get(i).getName();
                                recipe_thumbImg[i] = response.body().get(i).getThumb();
                                recipe_realImg[i] = response.body().get(i).getImg();

                                recipe_proteins[i] = response.body().get(i).getProteins();
                                recipe_fats[i] = response.body().get(i).getFats();
                                recipe_carbs[i] = response.body().get(i).getCarbs();
                                recipe_calories[i] = response.body().get(i).getCalories();
                            }

                            adapterSelectRecipe = new AdapterSelectRecipe(getApplicationContext(), R.layout.row_list_recipes, recipe_name, recipe_thumbImg, recipe_realImg, recipe_proteins, recipe_fats, recipe_carbs, recipe_calories);
                            listView_recipes.setAdapter(adapterSelectRecipe);

                            adapterSelectRecipe.notifyDataSetChanged();

                            progressBar.setVisibility(View.GONE);

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
            public void onFailure(Call<List<ResSelectRecipe>> call, Throwable t) {
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


//-------------------------------------- APIs --------------------------------------------------//


    GetRecipesAPI getRecipesAPIService(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetRecipesAPI.class);
    }

    interface GetRecipesAPI {
        @POST("ingredients/get_subingredients_api2/")
        @FormUrlEncoded
        Call<List<ResSelectRecipe>> getRecipes(@Field("id") String id);
    }


}
