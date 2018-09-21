package com.example.admin.mealplanner2new.Views

import android.app.FragmentTransaction
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.admin.mealplanner2new.Common.SessionManager
import com.example.admin.mealplanner2new.Fragments.FirstPhotoUploadFragment
import com.example.admin.mealplanner2new.Fragments.StartExerciseFragment
import com.example.admin.mealplanner2new.Models.Exercise
import com.example.admin.mealplanner2new.R

import kotlinx.android.synthetic.main.activity_start_exercise.*


class StartExerciseActivity : AppCompatActivity() {

    lateinit var exerciseList: ArrayList<Exercise>
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_exercise)
        setSupportActionBar(toolbar)

        if (intent != null) {

            exerciseList = intent.getParcelableArrayListExtra("data")

        }


        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {

            if (fragmentManager.backStackEntryCount > 0){
            fragmentManager.popBackStack()
            }
            else{
              finish()
            }

        }


        val startExerciseFragment = StartExerciseFragment()
        val bundle = Bundle()
        bundle.putLong("time", exerciseList[0].timeOfRep)
        bundle.putString("ex_name", exerciseList[0].name)
        bundle.putString("ex_rep", exerciseList[0].reps)
        bundle.putInt("ex_id", id)
        startExerciseFragment.arguments = bundle


        if (SessionManager(this@StartExerciseActivity).isFirstPhotoUploaded){
            fragmentManager.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .add(R.id.container_exercise, startExerciseFragment, id.toString())
                    //.addToBackStack(id.toString())
                    .commit()


        }
        else{

            val bundle2 = Bundle()
            bundle2.putParcelableArrayList("data",exerciseList)
            bundle2.putLong("time", exerciseList[0].timeOfRep)
            bundle2.putString("ex_name", exerciseList[0].name)
            bundle2.putString("ex_rep", exerciseList[0].reps)
            bundle2.putInt("ex_id", id)
            val firstPhotoUploadFragment = FirstPhotoUploadFragment()
            firstPhotoUploadFragment.arguments = bundle2

            fragmentManager.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .add(R.id.container_exercise, firstPhotoUploadFragment, FirstPhotoUploadFragment::class.java.simpleName)
                    //.addToBackStack(id.toString())
                    .commit()


        }



    }




}
