package com.example.admin.mealplanner2new.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.mealplanner2new.R;


public class AdapterSelectRecipe extends ArrayAdapter<String> {

    private Context context;

    private String[] nameArray;
    private String[] thumbImageArray;
    private String[] realImageArray;

    private String[] proteinArray;
    private String[] fatsArray;
    private String[] carbsArray;
    private String[] caloriesArray;

    private String BASE_REAL_IMG_URL = "http://www.code-fuel.in/mealplanner/menu/";
    private String BASE_THUMB_IMG_URL = "http://www.code-fuel.in/mealplanner/menu/thumb/";

    public AdapterSelectRecipe(@NonNull Context context, int resource, String[] nameArray, String[] thumbImageArray, String[] realImageArray, String[] proteinArray, String[] fatsArray, String[] carbsArray, String[] caloriesArray) {
        super(context, resource);

        this.context = context;
        this.nameArray = nameArray;
        this.thumbImageArray = thumbImageArray;
        this.realImageArray = realImageArray;
        this.proteinArray = proteinArray;
        this.fatsArray = fatsArray;
        this.carbsArray = carbsArray;
        this.caloriesArray = caloriesArray;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(final int position, final View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row_list_recipes, parent, false);

        TextView textView_name = row.findViewById(R.id.row_recipes_tv_name);
        textView_name.setText(nameArray[position]);

        ImageView imageView_img = row.findViewById(R.id.row_recipes_iv_thumbImg);
        String img_uri = BASE_THUMB_IMG_URL + (thumbImageArray[position]);
        Glide.with(context).load(img_uri).into(imageView_img);

        TextView textView_proteins = row.findViewById(R.id.row_recipes_tv_proteins);
        textView_proteins.setText(proteinArray[position]);

        TextView textView_fats = row.findViewById(R.id.row_recipes_tv_fats);
        textView_fats.setText(fatsArray[position]);

        TextView textView_carbs = row.findViewById(R.id.row_recipes_tv_carbs);
        textView_carbs.setText(carbsArray[position]);

        TextView textView_calories = row.findViewById(R.id.row_recipes_tv_calories);
        textView_calories.setText(caloriesArray[position]);

        ImageView imageView_add = row.findViewById(R.id.row_recipes_iv_add);

        imageView_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecipe(nameArray[position]);

            }
        });

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectRecipe(nameArray[position]);
            }
        });

        return row;
    }

    private void openSelectRecipe(String recipe) {
        Toast.makeText(context, "Selected: " + recipe, Toast.LENGTH_SHORT).show();
    }

    private void addRecipe(String recipe) {
        Toast.makeText(context, "Selected: " + recipe, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getCount() {
        return nameArray.length;
    }

}
