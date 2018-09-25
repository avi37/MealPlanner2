package com.example.admin.mealplanner2new.Fragments

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.admin.mealplanner2new.Models.SavedExerciseData
import com.example.admin.mealplanner2new.R
import com.example.admin.mealplanner2new.Service.MyJobIntentService
import com.example.admin.mealplanner2new.Views.ExNavigationActivity
import com.example.admin.mealplanner2new.Views.ShowExercisesActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_final_save_exercise.*

class FinalSubmitExerciseFragment : Fragment() {

    private lateinit var savedExerciseData: SavedExerciseData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {

            savedExerciseData = arguments.getParcelable("data")

        }


    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSaveExercise?.setOnClickListener {


            Log.e("exercise json", Gson().toJson(savedExerciseData))

            val intent = Intent(activity, MyJobIntentService::class.java)
            intent.putExtra("data", savedExerciseData)
            MyJobIntentService.enqueueWork(activity.applicationContext, intent)


            val intent22 = Intent(activity, ExNavigationActivity::class.java)
            intent22.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent22)

            Toast.makeText(activity, "Exercise completed", Toast.LENGTH_LONG).show()

        }


    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater?.inflate(R.layout.fragment_final_save_exercise, container, false)!!
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


}