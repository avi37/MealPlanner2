package com.example.admin.mealplanner2new.Service;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.JobIntentService;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.mealplanner2new.Common.RetrofitClient;
import com.example.admin.mealplanner2new.Models.ResCommon;
import com.example.admin.mealplanner2new.Models.SavedExerciseData;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Random;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * This is a simple job to take a number and toast that number of random numbers, one every 10 seconds.
 */
public class MyJobIntentService extends JobIntentService {

    /**
     * Unique job ID for this service.
     */
    static final int JOB_ID = 1000;
    private static final String BASE_URL = "http://code-fuel.in/healthbotics/api/auth/";
    final String TAG = "MyJobIntenetService";
    final Handler mHandler = new Handler();
    // Random number generator
    private final Random mGenerator = new Random();
    private SavedExerciseData savedExerciseData;
    private SaveExerciseData saveExerciseData;

    /**
     * Convenience method for enqueuing work in to this service.
     */
    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, MyJobIntentService.class, JOB_ID, work);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        saveExerciseData = RetrofitClient.getClient(BASE_URL).create(SaveExerciseData.class);

    }

    @Override
    protected void onHandleWork(Intent intent) {
        // We have received work to do.  The system or framework is already
        // holding a wake lock for us at this point, so we can just go.
        if (intent != null)
            savedExerciseData = intent.getParcelableExtra("data");
        Log.e("saved_exercise", new Gson().toJson(savedExerciseData));

        if (savedExerciseData != null) {

            try {
                final ResCommon resCommon = saveExerciseData.sendExercise(savedExerciseData).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.wtf(TAG, "All work complete");
        toast("All work complete");
    }

    // Helper for showing tests
    void toast(final CharSequence text) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyJobIntentService.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }


    interface SaveExerciseData {
        @POST("update_workout_status")
        Call<ResCommon> sendExercise(@Body SavedExerciseData savedExerciseData);


    }
}