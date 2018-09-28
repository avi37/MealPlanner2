package com.example.admin.mealplanner2new.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.admin.mealplanner2new.Models.ModelGallaryImage;
import com.example.admin.mealplanner2new.R;

import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<ModelGallaryImage> data = new ArrayList<>();
    private String BASE_PHOTO_URL = "http://code-fuel.in/healthbotics/storage/app/public/thumb/";


    public GalleryAdapter(Context context, List<ModelGallaryImage> data) {
        this.context = context;
        this.data = data;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_gallary_image, parent, false);
        viewHolder = new MyItemHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Glide.with(context).load(BASE_PHOTO_URL + data.get(position).getThumb())
                .thumbnail(0.5f)
                .apply(new RequestOptions().override(200, 200).diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(((MyItemHolder) holder).mImg);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyItemHolder extends RecyclerView.ViewHolder {
        ImageView mImg;


        public MyItemHolder(View itemView) {
            super(itemView);

            mImg = (ImageView) itemView.findViewById(R.id.row_gallery_image);
        }

    }


}
