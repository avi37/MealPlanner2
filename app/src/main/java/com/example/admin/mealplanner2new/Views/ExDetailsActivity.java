package com.example.admin.mealplanner2new.Views;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.mealplanner2new.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExDetailsActivity extends AppCompatActivity {

    TextView textView_name, textView_description;
    ImageView imageView_exPhoto;
    Button button_watchVideo;

    private YouTubePlayer youTubePlayer;
    YouTubePlayerSupportFragment youTubePlayerFragment;

    private String exId, exName, thumbUrl, photoUrl, video_link, exDescription;

    private final static String link_expression = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_details);

        exId = getIntent().getStringExtra("id");                         //  coming from  ExListOnCatActivity
        exName = getIntent().getStringExtra("name");
        thumbUrl = getIntent().getStringExtra("thumb");
        photoUrl = getIntent().getStringExtra("photo");
        video_link = getIntent().getStringExtra("video_link");
        exDescription = getIntent().getStringExtra("description");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        textView_name = findViewById(R.id.exDetails_tv_exName);
        imageView_exPhoto = findViewById(R.id.exDetails_iv_exPhoto);
        button_watchVideo = findViewById(R.id.exDetails_btn_watchVideo);
        textView_description = findViewById(R.id.exDetails_tv_description);


        setAllData();

        button_watchVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                methodShowVideo(video_link);

                //Toast.makeText(ExDetailsActivity.this, video_link, Toast.LENGTH_SHORT).show();

            }
        });


    }


    public static String getVideoId(String videoUrl) {
        if (videoUrl == null || videoUrl.trim().length() <= 0) {
            return null;
        }

        Pattern pattern = Pattern.compile(link_expression);
        Matcher matcher = pattern.matcher(videoUrl);
        try {
            if (matcher.find())
                return matcher.group();
        } catch (ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }

        return null;
    }


    private void methodShowVideo(String video_link) {

        String video_id = getVideoId(video_link);

        youTubePlayerFragment = (YouTubePlayerSupportFragment) getSupportFragmentManager()
                .findFragmentById(R.id.exDetails_frame_video);


        if (youTubePlayerFragment == null) {
            return;
        }

        youTubePlayerFragment.initialize(getString(R.string.youTube_api_key), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean b) {

                if (!b) {

                    imageView_exPhoto.setVisibility(View.GONE);

                    youTubePlayer = player;

                    //set the player style default
                    //player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);

                    //cue the 1st video by default
                    youTubePlayer.loadVideo(video_id);
                }

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(ExDetailsActivity.this, "Youtube Player View initialization failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setAllData() {

        textView_name.setText(exName);
        Glide.with(ExDetailsActivity.this).load(photoUrl).into(imageView_exPhoto);
        textView_description.setText(exDescription);

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
