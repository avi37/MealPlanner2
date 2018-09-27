package com.example.admin.mealplanner2new.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.mealplanner2new.Common.PrefMeal;
import com.example.admin.mealplanner2new.Common.RetrofitClient;
import com.example.admin.mealplanner2new.Common.SessionManager;
import com.example.admin.mealplanner2new.Models.Ingredient;
import com.example.admin.mealplanner2new.Models.ResRecipeItem;
import com.example.admin.mealplanner2new.R;
import com.example.admin.mealplanner2new.Views.AddTodayMealActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class AddCarbFoodFragment extends Fragment {

    private static final String BASE_URL = "http://code-fuel.in/healthbotics/api/auth/";
    GetRecipesAPI getRecipesAPI;

    SessionManager sessionManager;
    PrefMeal prefMeal;
    View view_main;
    TextView textView_noRecipes;
    RecyclerView recyclerView_carbRecipes;
    ProgressBar progressBar;
    Button button_next;
    RecAdapter recAdapter;
    private Ingredient ingredient;
    private Context context;

    public static AddCarbFoodFragment newInstance(int page, String title) {
        AddCarbFoodFragment fragmentAddCarbFood = new AddCarbFoodFragment();
        Bundle args = new Bundle();
        args.putInt("3", page);
        args.putString("Add Carb Food", title);
        fragmentAddCarbFood.setArguments(args);
        return fragmentAddCarbFood;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

        ingredient = ((AddTodayMealActivity) (context)).ingredient;


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.fragment_add_carb_food, container, false);

        getRecipesAPI = getGetRecipesAPIService(BASE_URL);
        sessionManager = new SessionManager(getContext());
        prefMeal = new PrefMeal(getContext());

        textView_noRecipes = view_main.findViewById(R.id.addCarbFood_tv_noRecipes);
        progressBar = view_main.findViewById(R.id.addCarbFood_progressBar);
        recyclerView_carbRecipes = view_main.findViewById(R.id.addCarbFood_recView_recipes);
        button_next = view_main.findViewById(R.id.addCarbFood_btn_next);

        //setAllRecipes();

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AddTodayMealActivity) getActivity()).setCurrentItem(3, true);
            }
        });


        return view_main;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

            setAllRecipes();
        }
    }

    private void setAllRecipes() {

        progressBar.setVisibility(View.VISIBLE);

        String token = sessionManager.getAccessToken();
        String category = "2";
        String type = prefMeal.getMealType();

        getRecipesAPI.get_recipes("Bearer " + token, category, type).enqueue(new Callback<List<ResRecipeItem>>() {
            @Override
            public void onResponse(Call<List<ResRecipeItem>> call, Response<List<ResRecipeItem>> response) {

                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    if (response.body() != null) {

                        String[] names = new String[response.body().size()];
                        String[] thumbs = new String[response.body().size()];

                        for (int i = 0; i < response.body().size(); i++) {
                            names[i] = response.body().get(i).getName();
                            thumbs[i] = response.body().get(i).getThumb();
                        }

                        recAdapter = new RecAdapter(names, thumbs);

                        recyclerView_carbRecipes.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

                        if (getActivity() != null)
                            recyclerView_carbRecipes.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

                        recyclerView_carbRecipes.setAdapter(recAdapter);

                        if (recAdapter.getItemCount() > 0) {
                            textView_noRecipes.setVisibility(View.GONE);
                        }

                    } else {
                        //response body is null

                    }

                } else {
                    //response not successful
                }

            }

            @Override
            public void onFailure(Call<List<ResRecipeItem>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }


//------------------------------------ Adapter Class ---------------------------------------------//

    GetRecipesAPI getGetRecipesAPIService(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetRecipesAPI.class);
    }


//---------------------------------------- APIs --------------------------------------------------//

    interface GetRecipesAPI {
        @Headers("X-Requested-With:XMLHttpRequest")
        @POST("getRecipesByCategory")
        @FormUrlEncoded
        Call<List<ResRecipeItem>> get_recipes(@Header("Authorization") String token,
                                              @Field("category") String category,
                                              @Field("type") String type
        );
    }

    public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {

        private String[] nameArray;
        private String[] imageArray;


        RecAdapter(String[] nameArray, String[] imageArray) {
            this.nameArray = nameArray;
            this.imageArray = imageArray;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_add_recipe, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            int count = position + 1;
            viewHolder.getTextView_name().setText(nameArray[position]);

            viewHolder.getImageView_recipeImage().setBackgroundColor(getResources().getColor(R.color.font_grey));

            viewHolder.getImageView_add().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Clicked: " + count, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return nameArray.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private final TextView textView_name;
            private final ImageView imageView_recipeImage, imageView_add;

            ViewHolder(View v) {
                super(v);

                /*v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //startActivity(new Intent(ExHelpActivity.this, ExQueItemActivity.class));
                    }
                });*/

                textView_name = (TextView) v.findViewById(R.id.row_addRecipe_tv_name);
                imageView_recipeImage = (ImageView) v.findViewById(R.id.row_addRecipe_iv_image);
                imageView_add = (ImageView) v.findViewById(R.id.row_addRecipe_iv_addBtn);
            }

            TextView getTextView_name() {
                return textView_name;
            }

            ImageView getImageView_recipeImage() {
                return imageView_recipeImage;
            }

            ImageView getImageView_add() {
                return imageView_add;
            }

        }

    }

}
