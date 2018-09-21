package com.example.admin.mealplanner2new.Views;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.admin.mealplanner2new.Adapters.GalleryAdapter;
import com.example.admin.mealplanner2new.Models.ModelGallaryImage;
import com.example.admin.mealplanner2new.R;

import java.util.ArrayList;


public class ImageGalleryActivity extends AppCompatActivity {

    RecyclerView recyclerView_gallery;
    ProgressBar progressBar;
    TextView textView_noImages;

    ArrayList<ModelGallaryImage> imageData = new ArrayList<>();
    public static String arrImages[] = {
            "https://images.unsplash.com/photo-1444090542259-0af8fa96557e?q=80&fm=jpg&w=1080&fit=max&s=4b703b77b42e067f949d14581f35019b",
            "https://images.unsplash.com/photo-1439546743462-802cabef8e97?dpr=2&fit=crop&fm=jpg&h=725&q=50&w=1300",
            "https://images.unsplash.com/photo-1441155472722-d17942a2b76a?q=80&fm=jpg&w=1080&fit=max&s=80cb5dbcf01265bb81c5e8380e4f5cc1",
            "https://images.unsplash.com/photo-1437651025703-2858c944e3eb?dpr=2&fit=crop&fm=jpg&h=725&q=50&w=1300",
            "https://images.unsplash.com/photo-1431538510849-b719825bf08b?dpr=2&fit=crop&fm=jpg&h=725&q=50&w=1300",
            "https://images.unsplash.com/photo-1434873740857-1bc5653afda8?dpr=2&fit=crop&fm=jpg&h=725&q=50&w=1300",
            "https://images.unsplash.com/photo-1439396087961-98bc12c21176?dpr=2&fit=crop&fm=jpg&h=725&q=50&w=1300",
            "https://images.unsplash.com/photo-1433616174899-f847df236857?dpr=2&fit=crop&fm=jpg&h=725&q=50&w=1300",
            "https://images.unsplash.com/photo-1438480478735-3234e63615bb?dpr=2&fit=crop&fm=jpg&h=725&q=50&w=1300",
            "https://images.unsplash.com/photo-1438027316524-6078d503224b?dpr=2&fit=crop&fm=jpg&h=725&q=50&w=1300"
    };

    GalleryAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallery);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        progressBar = findViewById(R.id.exGallery_progressbar);
        textView_noImages = findViewById(R.id.exGallery_tv_noImages);
        recyclerView_gallery = findViewById(R.id.exGallery_recView);
        recyclerView_gallery.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView_gallery.setHasFixedSize(true); // Helps improve performance

        for (int i = 0; i < arrImages.length; i++) {
            ModelGallaryImage imageModel = new ModelGallaryImage();
            imageModel.setName("Image " + i);
            imageModel.setUrl(arrImages[i]);
            imageData.add(imageModel);
        }


        mAdapter = new GalleryAdapter(ImageGalleryActivity.this, imageData) ;
        recyclerView_gallery.setAdapter(mAdapter);

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
