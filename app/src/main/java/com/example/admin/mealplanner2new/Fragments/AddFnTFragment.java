package com.example.admin.mealplanner2new.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class AddFnTFragment extends Fragment {

    private static final String BASE_URL = "http://code-fuel.in/healthbotics/api/auth/";
    GetRecipesAPI getRecipesAPI;

    SessionManager sessionManager;
    PrefMeal prefMeal;

    View view_main;
    TextView textView_noFnT;
    RecyclerView recyclerView_FnT;
    ProgressBar progressBar;
    Button button_next;

    RecAdapter recAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.fragment_add_fnt, container, false);

        getRecipesAPI = getGetRecipesAPIService(BASE_URL);
        sessionManager = new SessionManager(getContext());
        prefMeal = new PrefMeal(getContext());

        textView_noFnT = view_main.findViewById(R.id.addFnT_tv_noRecipes);
        progressBar = view_main.findViewById(R.id.addFnT_progressBar);
        recyclerView_FnT = view_main.findViewById(R.id.addFnT_recView_recipes);
        button_next = view_main.findViewById(R.id.addFnT_btn_next);

        //setAllRecipes();

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AddTodayMealActivity) getActivity()).setCurrentItem(5, true);
            }
        });

        return view_main;
    }

    public static AddFnTFragment newInstance(int page, String title) {
        AddFnTFragment fragmentAddFnT = new AddFnTFragment();
        Bundle args = new Bundle();
        args.putInt("5", page);
        args.putString("Add Fruit and Nuts", title);
        fragmentAddFnT.setArguments(args);
        return fragmentAddFnT;
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
        String category = "4";
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

                        recyclerView_FnT.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                        recyclerView_FnT.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

                        recyclerView_FnT.setAdapter(recAdapter);

                        if (recAdapter.getItemCount() > 0) {
                            textView_noFnT.setVisibility(View.GONE);
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

    public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {

        private String[] nameArray;
        private String[] imageArray;


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

    }

//---------------------------------------- APIs --------------------------------------------------//

    GetRecipesAPI getGetRecipesAPIService(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetRecipesAPI.class);
    }

    interface GetRecipesAPI {
        @Headers("X-Requested-With:XMLHttpRequest")
        @POST("getRecipesByCategory")
        @FormUrlEncoded
        Call<List<ResRecipeItem>> get_recipes(@Header("Authorization") String token,
                                              @Field("category") String category,
                                              @Field("type") String type
        );
    }

}
