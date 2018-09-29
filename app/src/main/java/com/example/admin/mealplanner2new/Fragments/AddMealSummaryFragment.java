package com.example.admin.mealplanner2new.Fragments;

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
import com.example.admin.mealplanner2new.Models.Ingredient;
import com.example.admin.mealplanner2new.Models.ResRecipeItem;
import com.example.admin.mealplanner2new.R;
import com.example.admin.mealplanner2new.Views.AddTodayMealActivity;

import java.util.ArrayList;
import java.util.Arrays;


public class AddMealSummaryFragment extends Fragment {

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

        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("Confirm saving your meal?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        methodSubmitMeal();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();


        dialog.setCancelable(false);
        dialog.show();
    }


    private void methodSubmitMeal() {
        getActivity().finish();

        Toast.makeText(getContext(), "Meal Saved", Toast.LENGTH_SHORT).show();

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
