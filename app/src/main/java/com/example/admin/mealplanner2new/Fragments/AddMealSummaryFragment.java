package com.example.admin.mealplanner2new.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.mealplanner2new.Common.PrefMeal;
import com.example.admin.mealplanner2new.Common.RetrofitClient;
import com.example.admin.mealplanner2new.Common.SessionManager;
import com.example.admin.mealplanner2new.Models.BodyCreateMeal;
import com.example.admin.mealplanner2new.Models.BodyRegister;
import com.example.admin.mealplanner2new.Models.Ingredient;
import com.example.admin.mealplanner2new.Models.ResCommon;
import com.example.admin.mealplanner2new.Models.ResRecipeItem;
import com.example.admin.mealplanner2new.R;
import com.example.admin.mealplanner2new.Views.AddTodayMealActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public class AddMealSummaryFragment extends Fragment {

    private static final String BASE_URL = "http://code-fuel.in/healthbotics/api/auth/";
    SaveMealAPI saveMealAPI;

    SessionManager sessionManager;

    View view_main;
    RecAdapter recAdapter;
    TextView tv_mealCat, tv_mealTime, tv_mealType;
    TextView tv_proteins, tv_fats, tv_carbs, tv_calories;
    RecyclerView recyclerView_recipes;
    Button btn_submit;
    private Context context;
    private Ingredient ingredient;
    private ArrayList<ResRecipeItem> allItemList;
    private double proteins;
    private double carbs;
    private double fats;
    private double calories;
    private String mealType;
    private String mealCategory;
    private String mealTime;
    private PrefMeal prefMeal;

    public static AddMealSummaryFragment newInstance(int page, String title) {
        AddMealSummaryFragment fragmentAddMealSummary = new AddMealSummaryFragment();
        Bundle args = new Bundle();
        args.putInt("6", page);
        args.putString("Meal Summary", title);
        fragmentAddMealSummary.setArguments(args);
        return fragmentAddMealSummary;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

        ingredient = ((AddTodayMealActivity) (context)).ingredient;
        mealTime = ((AddTodayMealActivity) (context)).mealTime;
        mealType = ((AddTodayMealActivity) (context)).mealType;
        mealCategory = ((AddTodayMealActivity) (context)).mealCategory;

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.fragment_add_meal_summary, container, false);

        saveMealAPI = getSaveMealAPIService(BASE_URL);

        sessionManager = new SessionManager(getContext());

        tv_mealCat = view_main.findViewById(R.id.mealSummary_tv_mealCat);
        tv_mealTime = view_main.findViewById(R.id.mealSummary_tv_mealTime);
        tv_mealType = view_main.findViewById(R.id.mealSummary_tv_mealType);
        tv_proteins = view_main.findViewById(R.id.mealSummary_tv_total_proteins);
        tv_fats = view_main.findViewById(R.id.mealSummary_tv_total_fats);
        tv_carbs = view_main.findViewById(R.id.mealSummary_tv_total_carbs);
        tv_calories = view_main.findViewById(R.id.mealSummary_tv_total_calories);
        recyclerView_recipes = view_main.findViewById(R.id.mealSummary_recView_recipes);
        btn_submit = view_main.findViewById(R.id.mealSummary_btn_submit);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDialog();
            }
        });


        return view_main;
    }


    private void showConfirmDialog() {

        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.layout_dialog_save_meal, null);

        final EditText etMealName = alertLayout.findViewById(R.id.saveMealDialog_et_name);

        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(alertLayout)
                .setTitle("Confirm to submit your meal?")
                .setPositiveButton("YES", null)   //Set to null. Will be overridden while the onclick
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {

                Button b1 = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                b1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        boolean wantToCloseDialog = false;

                        String meal_name = etMealName.getText().toString();

                        if (meal_name.equals("")) {
                            etMealName.setError("Recipe name required");
                            etMealName.requestFocus();
                        } else if (meal_name.length() < 3) {
                            etMealName.setError("add more characters to meal name");
                            etMealName.requestFocus();
                        } else {
                            wantToCloseDialog = true;

                            methodSubmitMeal(meal_name);

                        }

                        //if both fields are validate
                        if (wantToCloseDialog) {
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        dialog.setCancelable(false);
        dialog.show();

    }


    private void methodSubmitMeal(String mealName) {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Saving your meal...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String recipe_id = "", user_id, meal_type, meal_name, meal_date, meal_time;

        for (int i = 0; i < allItemList.size(); i++) {
            recipe_id = recipe_id + allItemList.get(i).getId() + ",";
        }

        user_id = sessionManager.getKeyUId();
        meal_type = prefMeal.getMealType();
        meal_name = mealName;
        meal_date = getTodayDate();    // condition for today meal
        meal_time = prefMeal.getMealTime();

        BodyCreateMeal bodyCreateMeal = new BodyCreateMeal(recipe_id, user_id, meal_type, meal_name, meal_date, meal_time);

        String token = sessionManager.getAccessToken();

        saveMealAPI.createMeal("Bearer " + token, bodyCreateMeal).enqueue(new Callback<ResCommon>() {
            @Override
            public void onResponse(Call<ResCommon> call, Response<ResCommon> response) {

                progressDialog.dismiss();

                if (response.isSuccessful()) {

                    if (response.body() != null) {

                        if (response.body().getMsg().equals("true")) {

                            getActivity().finish();

                            Toast.makeText(getContext(), "Meal Saved", Toast.LENGTH_SHORT).show();

                        } else {
                            // msg != true
                        }

                    } else {
                        // response body null
                    }

                } else {
                    //response not successful
                }

            }

            @Override
            public void onFailure(Call<ResCommon> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }


    private String getTodayDate() {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("dd-MM-yyyy");
        Date today_date = new Date();

        return simpledateformat.format(today_date);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);


        if (isVisibleToUser) {
            if (getActivity() != null) {

                prefMeal = new PrefMeal(getActivity());
                tv_mealTime.setText(prefMeal.getMealTime());
                tv_mealCat.setText(prefMeal.getMealCategory());
                if (prefMeal.getMealType().equals("0")) {
                    tv_mealType.setText("Non Veg");
                } else {
                    tv_mealType.setText("Veg");
                }
            }


            allItemList = new ArrayList<>();

            if (ingredient != null && ingredient.getfNtList() != null) {

                allItemList.addAll(ingredient.getProteinList());
                allItemList.addAll(ingredient.getCarbList());
                allItemList.addAll(ingredient.getfNtList());
                allItemList.addAll(ingredient.getVeggiList());

                Log.e("all item", String.valueOf(allItemList.size()));


                for (int i = 0; i < allItemList.size(); i++) {
                    proteins = proteins + Double.valueOf(allItemList.get(i).getProteins());
                    calories = calories + Double.valueOf(allItemList.get(i).getCalories());
                    carbs = carbs + Double.valueOf(allItemList.get(i).getCarbs());
                    fats = proteins + Double.valueOf(allItemList.get(i).getFats());

                }

                tv_proteins.setText(String.valueOf(proteins));
                tv_fats.setText(String.valueOf(fats));
                tv_carbs.setText(String.valueOf(carbs));
                tv_calories.setText(String.valueOf(calories));

                btn_submit.setVisibility(View.VISIBLE);

            }

            recAdapter = new RecAdapter(allItemList);
            recyclerView_recipes.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

            recyclerView_recipes.setAdapter(recAdapter);

            recAdapter.notifyDataSetChanged();

        }

    }


//---------------------------------------- APIs --------------------------------------------------//

    SaveMealAPI getSaveMealAPIService(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(SaveMealAPI.class);
    }

    interface SaveMealAPI {
        @Headers("X-Requested-With:XMLHttpRequest")
        @POST("createMeal")
        Call<ResCommon> createMeal(@Header("Authorization") String token,
                                   @Body BodyCreateMeal bodyCreateMeal
        );
    }

//--------------------------------- Adapter Class -----------------------------------------------//

    public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {

        String BASE_IMG_URL = "http://code-fuel.in/healthbotics/storage/app/public/thumb/";
        private ArrayList<ResRecipeItem> mDataSet;


        RecAdapter(ArrayList<ResRecipeItem> mDataSet) {
            this.mDataSet = mDataSet;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_summary_meal_item, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            viewHolder.getTextView_name().setText(mDataSet.get(position).getName());

            viewHolder.getImageView_recipeImage().setBackgroundColor(getResources().getColor(R.color.font_grey));

            String img_uri = BASE_IMG_URL + (mDataSet.get(position).getThumb());
            Glide.with(getContext()).load(img_uri).into(viewHolder.imageView_recipeImage);

        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private final TextView textView_name;
            private final ImageView imageView_recipeImage;

            ViewHolder(View v) {
                super(v);

                textView_name = (TextView) v.findViewById(R.id.row_mealSummary_tv_name);
                imageView_recipeImage = (ImageView) v.findViewById(R.id.row_mealSummary_iv_image);

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
