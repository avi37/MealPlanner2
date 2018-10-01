package com.example.admin.mealplanner2new.Views;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.mealplanner2new.R;

public class YogaItemDetailActivity extends AppCompatActivity {

    private static String BASE_IMG_URL = "";

    TextView textView_name;
    ImageView imageView_image;

    private String name, imageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga_item_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        textView_name = findViewById(R.id.yogaDetail_tv_name);
        imageView_image = findViewById(R.id.yogaDetail_iv_image);

        name = getIntent().getStringExtra("name");                             //coming from YogaListActivity adapter class
        //imageUrl = BASE_IMG_URL + getIntent().getStringExtra("imageUrl");
        imageUrl = getIntent().getStringExtra("imageUrl");

        //setTitle(name + " details");

        textView_name.setText(name);
        // imageView_image.setImageResource(imageUrl);
        //Glide.with(getApplicationContext()).load(imageUrl).into(imageView_image);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
