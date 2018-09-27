package com.example.admin.mealplanner2new.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.bumptech.glide.Glide;
import com.example.admin.mealplanner2new.Common.PrefMeal;
import com.example.admin.mealplanner2new.Common.RetrofitClient;
import com.example.admin.mealplanner2new.Common.SessionManager;
import com.example.admin.mealplanner2new.Models.Ingredient;
import com.example.admin.mealplanner2new.Models.ResRecipeItem;
import com.example.admin.mealplanner2new.R;
import com.example.admin.mealplanner2new.Views.AddTodayMealActivity;

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

public class AddVeggiesFragment extends Fragment {

    private static final String BASE_URL = "http://code-fuel.in/healthbotics/api/auth/";
    GetRecipesAPI getRecipesAPI;

    SessionManager sessionManager;
    PrefMeal prefMeal;

    View view_main;
    TextView textView_noVeggies;
    RecyclerView recyclerView_veggies;
    ProgressBar progressBar;
    Button button_next;
    RecAdapter recAdapter;
    private Ingredient ingredient;
    private ArrayList<ResRecipeItem> resRecipeItemArrayList;
    private ArrayList<ResRecipeItem> selectedItemReciepList;
    private Context context;

    public static AddVeggiesFragment newInstance(int page, String title) {
        AddVeggiesFragment fragmentAddVeggies = new AddVeggiesFragment();
        Bundle args = new Bundle();
        args.putInt("4", page);
        args.putString("Add Veggies", title);
        fragmentAddVeggies.setArguments(args);
        return fragmentAddVeggies;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

        ingredient = ((AddTodayMealActivity) (context)).ingredient;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        resRecipeItemArrayList = new ArrayList<>();
        selectedItemReciepList = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.fragment_add_veggies, container, false);

        getRecipesAPI = getGetRecipesAPIService(BASE_URL);
        sessionManager = new SessionManager(getContext());
        prefMeal = new PrefMeal(getContext());

        textView_noVeggies = view_main.findViewById(R.id.addVeggies_tv_noRecipes);
        progressBar = view_main.findViewById(R.id.addVeggies_progressBar);
        recyclerView_veggies = view_main.findViewById(R.id.addVeggies_recView_recipes);
        button_next = view_main.findViewById(R.id.addVeggies_btn_next);

        //setAllVeggies();

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedItemReciepList.size() > 0) {

                    for (int i = 0; i < resRecipeItemArrayList.size(); i++) {

                        if (resRecipeItemArrayList.get(i).isSelected()) {
                            selectedItemReciepList.add(resRecipeItemArrayList.get(i));
                        }

                    }

                    if (selectedItemReciepList.size() > 0) {
                        ingredient.setVeggiList(selectedItemReciepList);
                    }
                    ((AddTodayMealActivity) getActivity()).setCurrentItem(4, true);
                } else {
                    Toast.makeText(getActivity(), "Select any receipe", Toast.LENGTH_LONG).show();
                }


            }
        });

        return view_main;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            setAllVeggies();
        }
    }

    private void setAllVeggies() {

        progressBar.setVisibility(View.VISIBLE);

        String token = sessionManager.getAccessToken();
        String category = "3";
        String mealType = prefMeal.getMealType();

        getRecipesAPI.get_recipes("Bearer " + token, category, mealType).enqueue(new Callback<List<ResRecipeItem>>() {
            @Override
            public void onResponse(Call<List<ResRecipeItem>> call, Response<List<ResRecipeItem>> response) {

                progressBar.setVisibility(View.GONE);


                if (response.isSuccessful()) {

                    if (response.body() != null) {


                        resRecipeItemArrayList = (ArrayList<ResRecipeItem>) response.body();


                        if (selectedItemReciepList.size() > 0 && resRecipeItemArrayList.size() > 0) {

                            for (int i = 0; i < selectedItemReciepList.size(); i++) {

                                for (int j = 0; j < resRecipeItemArrayList.size(); j++) {

                                    if (selectedItemReciepList.get(i).getId().equals(
                                            resRecipeItemArrayList.get(j).getId()
                                    )) {

                                        resRecipeItemArrayList.get(j).setSelected(true);


                                    }


                                }
                            }


                        }


                        recAdapter = new RecAdapter(resRecipeItemArrayList);

                        recyclerView_veggies.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

                        if (getActivity() != null)
                            recyclerView_veggies.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

                        recyclerView_veggies.setAdapter(recAdapter);

                        if (recAdapter.getItemCount() > 0) {
                            recyclerView_veggies.setVisibility(View.GONE);
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

        String BASE_IMG_URL = "http://code-fuel.in/healthbotics/storage/app/public/thumb/";
        private ArrayList<ResRecipeItem> mDataSet;


        RecAdapter(ArrayList<ResRecipeItem> mDataSet) {
            this.mDataSet = mDataSet;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_add_recipe, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            if (mDataSet.get(position).isSelected()) {


                viewHolder.imageView_add.setImageResource(R.drawable.ic_red_remove);


            } else {
                viewHolder.imageView_add.setImageResource(R.drawable.ic_green_add);
            }

            viewHolder.getTextView_name().setText(mDataSet.get(position).getName());

            viewHolder.getImageView_recipeImage().setBackgroundColor(getResources().getColor(R.color.font_grey));

            String img_uri = BASE_IMG_URL + (mDataSet.get(position).getPhoto());
            Glide.with(getContext()).load(img_uri).into(viewHolder.imageView_recipeImage);

        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private final TextView textView_name;
            private final ImageView imageView_recipeImage, imageView_add;

            ViewHolder(View v) {
                super(v);


                textView_name = (TextView) v.findViewById(R.id.row_addRecipe_tv_name);
                imageView_recipeImage = (ImageView) v.findViewById(R.id.row_addRecipe_iv_image);
                imageView_add = (ImageView) v.findViewById(R.id.row_addRecipe_iv_addBtn);


                imageView_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        selectedItemReciepList.clear();

                        if (mDataSet.get(getAdapterPosition()).isSelected()) {
                            mDataSet.get(getAdapterPosition()).setSelected(false);
                            imageView_add.setImageResource(R.drawable.ic_green_add);
                        } else {
                            mDataSet.get(getAdapterPosition()).setSelected(true);
                            imageView_add.setImageResource(R.drawable.ic_red_remove);
                        }

                        for (int i = 0; i < resRecipeItemArrayList.size(); i++) {

                            if (resRecipeItemArrayList.get(i).isSelected()) {
                                selectedItemReciepList.add(resRecipeItemArrayList.get(i));
                            }

                        }

                    }
                });
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
