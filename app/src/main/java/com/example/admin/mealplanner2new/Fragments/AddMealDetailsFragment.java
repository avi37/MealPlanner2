package com.example.admin.mealplanner2new.Fragments;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.mealplanner2new.Common.PrefMeal;
import com.example.admin.mealplanner2new.Common.RetrofitClient;
import com.example.admin.mealplanner2new.Common.SessionManager;
import com.example.admin.mealplanner2new.Models.ResCommon;
import com.example.admin.mealplanner2new.Models.ResRecipeItem;
import com.example.admin.mealplanner2new.R;
import com.example.admin.mealplanner2new.Views.AddTodayMealActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public class AddMealDetailsFragment extends Fragment {

    private static final String BASE_URL = "http://code-fuel.in/healthbotics/api/auth/";
    GetCategoriesAPI getCategoriesAPI;
    GetSavedRecipesAPI getSavedRecipesAPI;

    SessionManager sessionManager;
    PrefMeal prefMeal;

    View view_main;
    Spinner spinner_category, spinner_mealType;
    TextView textView_mealTime, textView_selectInfo;
    Button button_repeat, button_next;
    RecyclerView recyclerView_repeatedRecipes;
    ProgressBar progressBar;
    private Context context;
    RecAdapter recAdapter;
    private ArrayList<ResRecipeItem> resRecipeItemArrayList;

    private String mealCategoryId, mealTime, mealType;

    private ArrayList<String> cat_id, cat_name, cat_time;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

        this.mealCategoryId = ((AddTodayMealActivity) (context)).mealCategory;
        this.mealTime = ((AddTodayMealActivity) (context)).mealTime;
        this.mealType = ((AddTodayMealActivity) (context)).mealType;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.mealCategoryId = ((AddTodayMealActivity) (activity)).mealCategory;
        this.mealTime = ((AddTodayMealActivity) (activity)).mealTime;
        this.mealType = ((AddTodayMealActivity) (activity)).mealType;

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.fragment_add_meal_details, container, false);

        getCategoriesAPI = getGetCategoriesAPIService(BASE_URL);
        getSavedRecipesAPI = getSavedRecipesAPIService(BASE_URL);

        sessionManager = new SessionManager(getContext());
        prefMeal = new PrefMeal(getContext());

        spinner_category = view_main.findViewById(R.id.addTodayMeal_spinnerMealCat);
        textView_mealTime = view_main.findViewById(R.id.addTodayMeal_tv_selectedTime);
        spinner_mealType = view_main.findViewById(R.id.addTodayMeal_spinnerMealType);
        button_repeat = view_main.findViewById(R.id.addMealDetails_btn_repeat);
        button_next = view_main.findViewById(R.id.addMealDetails_btn_next);
        textView_selectInfo = view_main.findViewById(R.id.addMealDetails_tv_selectInfo);
        recyclerView_repeatedRecipes = view_main.findViewById(R.id.addMealDetails_recView_repeatRecipes);
        progressBar = view_main.findViewById(R.id.addMealDetails_progressbar);


        getSavedCategories();

        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (cat_time.size() > 0) {
                    textView_mealTime.setText(cat_time.get(spinner_category.getSelectedItemPosition()));

                } else {
                    Toast.makeText(context, "You have not added any Meal Category\nPlease add one from Dashboard navigation menu", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        textView_mealTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        button_repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                textView_selectInfo.setVisibility(View.VISIBLE);

                String token = sessionManager.getAccessToken();
                String u_id = sessionManager.getKeyUId();
                String type;

                if (spinner_mealType.getSelectedItem().toString().equals("Veg.")) {
                    type = "1";

                } else {
                    type = "0";
                }

                mealType = type;

                getSavedRecipesAPI.get_savedRecipes("Bearer " + token, u_id, type).enqueue(new Callback<List<ResRecipeItem>>() {
                    @Override
                    public void onResponse(Call<List<ResRecipeItem>> call, Response<List<ResRecipeItem>> response) {

                        progressBar.setVisibility(View.GONE);

                        if (response.isSuccessful()) {

                            if (response.body() != null) {

                                resRecipeItemArrayList = (ArrayList<ResRecipeItem>) response.body();


                                recAdapter = new RecAdapter(resRecipeItemArrayList);

                                recyclerView_repeatedRecipes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                                recyclerView_repeatedRecipes.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

                                recyclerView_repeatedRecipes.setAdapter(recAdapter);


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
        });

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mealCategoryId = cat_id.get(spinner_category.getSelectedItemPosition());   //spinner_category.getSelectedItem().toString();
                mealTime = textView_mealTime.getText().toString();
                String _mealType = spinner_mealType.getSelectedItem().toString();

                if (_mealType.equals("Veg.")) {
                    mealType = "1";
                } else {
                    mealType = "0";
                }
                prefMeal.setMealDetails(mealCategoryId, mealTime, mealType);
                ((AddTodayMealActivity) getActivity()).setCurrentItem(1, true);
            }
        });


        return view_main;
    }


    private void getSavedCategories() {
        progressBar.setVisibility(View.VISIBLE);

        String token = sessionManager.getAccessToken();

        getCategoriesAPI.get_Categories("Bearer " + token).enqueue(new Callback<List<ResCommon>>() {
            @Override
            public void onResponse
                    (Call<List<ResCommon>> call, Response<List<ResCommon>> response) {

                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    if (response.body() != null) {

                        if (response.body().size() > 0) {

                            cat_id = new ArrayList<>();
                            cat_name = new ArrayList<>();
                            cat_time = new ArrayList<>();

                            for (int i = 0; i < response.body().size(); i++) {
                                cat_id.add(response.body().get(i).getCat_id());
                                cat_name.add(response.body().get(i).getCat_name());
                                cat_time.add(response.body().get(i).getCat_time());
                            }

                            final ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, cat_name);
                            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            spinner_category.setAdapter(categoryAdapter);

                            categoryAdapter.notifyDataSetChanged();

                            textView_mealTime.setText(cat_time.get(spinner_category.getSelectedItemPosition()));


                        } else {
                            Toast.makeText(context, "You have not added any Meal Category\nPlease add one from Dashboard navigation menu", Toast.LENGTH_LONG).show();
                            spinner_category.setClickable(false);
                            button_next.setVisibility(View.INVISIBLE);
                            button_next.setClickable(false);
                        }


                    } else {
                        //response body is null

                    }

                } else {
                    // response not successful
                }

            }

            @Override
            public void onFailure(Call<List<ResCommon>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });


    }

    public static AddMealDetailsFragment newInstance(int page, String title) {
        AddMealDetailsFragment fragmentAddMealDetails = new AddMealDetailsFragment();
        Bundle args = new Bundle();
        args.putInt("1", page);
        args.putString("Add Meal Details", title);
        fragmentAddMealDetails.setArguments(args);
        return fragmentAddMealDetails;
    }

    private void showTimePicker() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                textView_mealTime.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }


//---------------------------------------- APIs --------------------------------------------------//

    GetCategoriesAPI getGetCategoriesAPIService(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetCategoriesAPI.class);
    }

    interface GetCategoriesAPI {
        @Headers("X-Requested-With:XMLHttpRequest")
        @GET("getUserMealCat")
        Call<List<ResCommon>> get_Categories(@Header("Authorization") String token);
    }


    GetSavedRecipesAPI getSavedRecipesAPIService(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(GetSavedRecipesAPI.class);
    }

    interface GetSavedRecipesAPI {
        @Headers("X-Requested-With:XMLHttpRequest")
        @POST("getSavedRecipes")
        @FormUrlEncoded
        Call<List<ResRecipeItem>> get_savedRecipes(@Header("Authorization") String token,
                                                   @Field("u_id") String u_id,
                                                   @Field("type") String type
        );
    }


//------------------------------------ Adapter Class --------------------------------------------//

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

            viewHolder.getTextView_name().setText(mDataSet.get(position).getName());

            viewHolder.getImageView_recipeImage().setBackgroundColor(getResources().getColor(R.color.font_grey));

            String img_uri = BASE_IMG_URL + (mDataSet.get(position).getPhoto());
            Glide.with(getContext()).load(img_uri).into(viewHolder.imageView_recipeImage);

            viewHolder.tv_protein.setText(mDataSet.get(position).getProteins());
            viewHolder.tv_fats.setText(mDataSet.get(position).getFats());
            viewHolder.tv_carbs.setText(mDataSet.get(position).getCarbs());
            viewHolder.tv_caloreis.setText(mDataSet.get(position).getCalories());

        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private final TextView textView_name;
            private final ImageView imageView_recipeImage, imageView_add;
            private final TextView tv_protein, tv_fats, tv_carbs, tv_caloreis;

            ViewHolder(View v) {
                super(v);

                textView_name = (TextView) v.findViewById(R.id.row_addRecipe_tv_name);
                imageView_recipeImage = (ImageView) v.findViewById(R.id.row_addRecipe_iv_image);
                imageView_add = (ImageView) v.findViewById(R.id.row_addRecipe_iv_addBtn);
                tv_protein = (TextView) v.findViewById(R.id.row_addRecipe_tv_proteins);
                tv_fats = (TextView) v.findViewById(R.id.row_addRecipe_tv_fats);
                tv_carbs = (TextView) v.findViewById(R.id.row_addRecipe_tv_carbs);
                tv_caloreis = (TextView) v.findViewById(R.id.row_addRecipe_tv_calories);

                imageView_add.setClickable(false);
                imageView_add.setVisibility(View.INVISIBLE);

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Clicked: " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            TextView getTextView_name() {
                return textView_name;
            }

            ImageView getImageView_recipeImage() {
                return imageView_recipeImage;
            }

        }

    }

}
