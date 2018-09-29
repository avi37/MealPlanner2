package com.example.admin.mealplanner2new.Adapters;

import android.content.Context;
import android.support.v7.app.AlertDialog;
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
    private String BASE_THUMB_URL = "http://code-fuel.in/healthbotics/storage/app/public/thumb/";
    private String BASE_PHOTO_URL = "http://code-fuel.in/healthbotics/storage/app/public/user/";


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

        Glide.with(context).load(BASE_THUMB_URL + data.get(position).getThumb())
                .thumbnail(0.5f)
                .apply(new RequestOptions().override(200, 200).diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(((MyItemHolder) holder).mImg);

        ((MyItemHolder) holder).mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRealItemImage(BASE_PHOTO_URL + data.get(position).getPhoto());
            }
        });

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

        public ImageView getmImg() {
            return mImg;
        }

    }


    private void openRealItemImage(String url_realImage) {

        AlertDialog.Builder popupDialogBuilder = new AlertDialog.Builder(context);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_full_screen_image, null);

        ImageView popupImv = view.findViewById(R.id.fullScreenImage_iv_realImage);
        popupDialogBuilder.setView(view);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.color.font_grey);
        Glide.with(context).setDefaultRequestOptions(requestOptions).load(url_realImage).into(popupImv);

        AlertDialog alertDialog = popupDialogBuilder.create();
        alertDialog.show();
    }


}
