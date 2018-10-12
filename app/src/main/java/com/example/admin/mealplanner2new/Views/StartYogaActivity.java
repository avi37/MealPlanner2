package com.example.admin.mealplanner2new.Views;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.admin.mealplanner2new.Fragments.YogaExerciseFragment;
import com.example.admin.mealplanner2new.Models.ModelYogaList;
import com.example.admin.mealplanner2new.R;

import java.util.ArrayList;

public class StartYogaActivity extends AppCompatActivity {

    public ArrayList<ModelYogaList> resYogaArrayList;

    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_yoga);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setYogaList();


        YogaExerciseFragment yogaExerciseFragment = new YogaExerciseFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("yoga_id", id);

        yogaExerciseFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .add(R.id.activity_start_yoga, yogaExerciseFragment, String.valueOf(id))
                .commit();

    }

    private void setYogaList() {

        resYogaArrayList = new ArrayList<>();

        resYogaArrayList.add(new ModelYogaList("1", "Crescent Lunge", R.drawable.yoga_crescent_lunge));
        resYogaArrayList.add(new ModelYogaList("2", "Warrior Pose", R.drawable.yoga_warrior_pose));
        resYogaArrayList.add(new ModelYogaList("3", "Cobra Pose", R.drawable.yoga_cobra_pose));
        resYogaArrayList.add(new ModelYogaList("4", "Camel Pose", R.drawable.yoga_camel_pose));
        resYogaArrayList.add(new ModelYogaList("5", "Pigeon Pose", R.drawable.yoga_pigeon_pose));
        resYogaArrayList.add(new ModelYogaList("6", "One Legged-King Pigeon", R.drawable.yoga_pigeon_king));
        resYogaArrayList.add(new ModelYogaList("7", "Low Lunge", R.drawable.yoga_low_lunge));
        resYogaArrayList.add(new ModelYogaList("8", "Bow Pose", R.drawable.yoga_bow_pose));
        resYogaArrayList.add(new ModelYogaList("9", "Downward Facing Dog Pose", R.drawable.yoga_downward_dog));
        resYogaArrayList.add(new ModelYogaList("10", "Warrior Pose on One Foot", R.drawable.yoga_warrior_on_one_leg));



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
