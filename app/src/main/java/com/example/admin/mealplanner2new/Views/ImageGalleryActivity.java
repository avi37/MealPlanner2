package com.example.admin.mealplanner2new.Views;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.mealplanner2new.Adapters.GalleryAdapter;
import com.example.admin.mealplanner2new.Common.RetrofitClient;
import com.example.admin.mealplanner2new.Common.SessionManager;
import com.example.admin.mealplanner2new.Models.ModelGallaryImage;
import com.example.admin.mealplanner2new.Models.ResCommon;
import com.example.admin.mealplanner2new.Models.ResUploadedPhotos;
import com.example.admin.mealplanner2new.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public class ImageGalleryActivity extends AppCompatActivity {

    private String BASE_URL = "http://code-fuel.in/healthbotics/api/auth/";
    UploadedPhotosAPI uploadedPhotosAPI;

    SessionManager sessionManager;

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

        uploadedPhotosAPI = getUploadedPhotosAPIService(BASE_URL);
        sessionManager = new SessionManager(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        progressBar = findViewById(R.id.exGallery_progressbar);
        textView_noImages = findViewById(R.id.exGallery_tv_noImages);
        recyclerView_gallery = findViewById(R.id.exGallery_recView);
        recyclerView_gallery.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView_gallery.setHasFixedSize(true); // Helps improve performance

        getUploadedPhotos();

    }

    private void getUploadedPhotos() {

        progressBar.setVisibility(View.VISIBLE);

        String token = sessionManager.getAccessToken();

        uploadedPhotosAPI.getUploadedPhotos("Bearer " + token).enqueue(new Callback<List<ResUploadedPhotos>>() {
            @Override
            public void onResponse(Call<List<ResUploadedPhotos>> call, Response<List<ResUploadedPhotos>> response) {

                if (response.isSuccessful()) {

                    if (response.body() != null) {

                        if (response.body().size() > 0) {

                            for (int i = 0; i < response.body().size(); i++) {
                                ModelGallaryImage imageModel = new ModelGallaryImage();
                                imageModel.setId(response.body().get(i).getId());
                                imageModel.setThumb(response.body().get(i).getThumb());
                                imageModel.setPhoto(response.body().get(i).getPhoto());
                                imageData.add(imageModel);
                            }

                            mAdapter = new GalleryAdapter(ImageGalleryActivity.this, imageData);
                            recyclerView_gallery.setAdapter(mAdapter);

                            mAdapter.notifyDataSetChanged();

                            progressBar.setVisibility(View.GONE);


                        } else {
                            progressBar.setVisibility(View.GONE);
                            textView_noImages.setVisibility(View.VISIBLE);
                        }


                    } else {
                        progressBar.setVisibility(View.GONE);
                        //response body null
                    }


                } else {
                    progressBar.setVisibility(View.GONE);
                    //response not successful
                }

            }

            @Override
            public void onFailure(Call<List<ResUploadedPhotos>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Connection error", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }


//---------------------------------------- APIs -----------------------------------------------//

    UploadedPhotosAPI getUploadedPhotosAPIService(String baseUrl) {
        return RetrofitClient.getClient(baseUrl).create(UploadedPhotosAPI.class);
    }

    interface UploadedPhotosAPI {
        @Headers("X-Requested-With:XMLHttpRequest")
        @POST("getUploadedPhoto")
        Call<List<ResUploadedPhotos>> getUploadedPhotos(@Header("Authorization") String token);
    }

}
