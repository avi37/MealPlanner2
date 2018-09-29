package com.example.admin.mealplanner2new.Fragments

import android.app.Activity
import android.app.Fragment
import android.content.Context
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
import com.example.admin.mealplanner2new.Views.FinalImageUploadActivity
import com.example.admin.mealplanner2new.Views.ShowExercisesActivity
import com.example.admin.mealplanner2new.Views.StartExerciseActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_final_save_exercise.*

class FinalSubmitExerciseFragment : Fragment() {

    private lateinit var savedExerciseData: SavedExerciseData

    private var status = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {

            savedExerciseData = arguments.getParcelable("data")

        }

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (status == "1") {
            tvLableSaveExercise.setText("Congratulations, you have completed exercise")
            btnSaveExercise.setText("Complete Review")
        }

        btnSaveExercise?.setOnClickListener {


            Log.e("exercise json", Gson().toJson(savedExerciseData))

            if (status == "0") {
                val intent = Intent(activity, MyJobIntentService::class.java)
                intent.putExtra("data", savedExerciseData)
                MyJobIntentService.enqueueWork(activity.applicationContext, intent)

                Toast.makeText(activity, "Exercise completed", Toast.LENGTH_LONG).show()

            } else {

                Toast.makeText(activity, "Review completed", Toast.LENGTH_SHORT)
            }

            val intent22 = Intent(activity, ExNavigationActivity::class.java)
            intent22.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent22)

            if (status == "0") {

                val intent33 = Intent(activity, FinalImageUploadActivity::class.java)
                startActivity(intent33)

            }


        }


    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater?.inflate(R.layout.fragment_final_save_exercise, container, false)!!
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        status = (context as StartExerciseActivity).status
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        status = (activity as StartExerciseActivity).status
    }
}