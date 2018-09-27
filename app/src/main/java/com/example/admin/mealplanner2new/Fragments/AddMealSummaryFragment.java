package com.example.admin.mealplanner2new.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.mealplanner2new.Models.Ingredient;
import com.example.admin.mealplanner2new.Models.ResRecipeItem;
import com.example.admin.mealplanner2new.R;
import com.example.admin.mealplanner2new.Views.AddTodayMealActivity;

import java.util.ArrayList;


public class AddMealSummaryFragment extends Fragment {

    View view_main;

    private Context context;
    private Ingredient ingredient;
    private ArrayList<ResRecipeItem> allItemList;

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


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.fragment_add_meal_summary, container, false);
        allItemList = new ArrayList<>();

        if (ingredient != null) {

            allItemList.addAll(ingredient.getProteinList());
            allItemList.addAll(ingredient.getCarbList());
            allItemList.addAll(ingredient.getfNtList());
            allItemList.addAll(ingredient.getVeggiList());

            Log.e("all item", String.valueOf(allItemList.size()));


        }


        return view_main;
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
                imageView_add.setVisibility(View.GONE);


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
