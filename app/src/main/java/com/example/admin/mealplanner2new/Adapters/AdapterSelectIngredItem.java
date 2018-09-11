package com.example.admin.mealplanner2new.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.mealplanner2new.R;
import com.example.admin.mealplanner2new.Views.SelectRecipeActivity;

public class AdapterSelectIngredItem extends ArrayAdapter<String> {

    private Context context;

    private String[] idArray;
    private String[] nameArray;
    private String[] thumbImageArray;
    private String[] realImageArray;

    private String BASE_REAL_IMG_URL = "http://www.code-fuel.in/mealplanner/menu/";
    private String BASE_THUMB_IMG_URL = "http://www.code-fuel.in/mealplanner/menu/thumb/";

    public AdapterSelectIngredItem(@NonNull Context context, int resource, String[] idArray, String[] nameArray, String[] thumbImageArray, String[] realImageArray) {
        super(context, resource);

        this.context = context;
        this.idArray = idArray;
        this.nameArray = nameArray;
        this.thumbImageArray = thumbImageArray;
        this.realImageArray = realImageArray;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(final int position, final View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row_list_ingred_items, parent, false);

        TextView textView = row.findViewById(R.id.row_select_ingredItem_tv_name);
        textView.setText(nameArray[position]);

        ImageView imageView = row.findViewById(R.id.row_select_ingredItem_iv_thumbImg);
        String img_uri = BASE_THUMB_IMG_URL + (thumbImageArray[position]);
        Glide.with(context).load(img_uri).into(imageView);

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectRecipe(idArray[position], nameArray[position]);
            }
        });

        return row;
    }

    private void openSelectRecipe(String ingred_id, String ingred_item) {
        Intent i = new Intent(context, SelectRecipeActivity.class);
        i.putExtra("ingred_id", ingred_id);
        i.putExtra("ingred_item", ingred_item);
        context.startActivity(i);
    }

    @Override
    public int getCount() {
        return nameArray.length;
    }
}
