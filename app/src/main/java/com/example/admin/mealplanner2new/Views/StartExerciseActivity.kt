package com.example.admin.mealplanner2new.Views

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.example.admin.mealplanner2new.Fragments.StartExerciseFragment
import com.example.admin.mealplanner2new.R

import kotlinx.android.synthetic.main.activity_start_exercise.*

class StartExerciseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_exercise)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {

            finish()

        }


        val startExerciseFragment = StartExerciseFragment()

        fragmentManager.beginTransaction()
                .replace(R.id.container_exercise, startExerciseFragment, startExerciseFragment::class.java.simpleName)
                .commit()


    }

}
