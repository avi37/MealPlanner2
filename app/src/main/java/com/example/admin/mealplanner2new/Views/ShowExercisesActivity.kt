package com.example.admin.mealplanner2new.Views

import android.content.Intent
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.admin.mealplanner2new.Models.DayWiseExersice
import com.example.admin.mealplanner2new.Models.Exercise

import com.example.admin.mealplanner2new.R

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

import kotlinx.android.synthetic.main.activity_show_exercises.*

class ShowExercisesActivity : AppCompatActivity() {
    private var dayWiseExersice: DayWiseExersice? = null
    private var dayWiseExersiceArrayList: ArrayList<DayWiseExersice>? = null
    private var exercises: ArrayList<Exercise>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_exercises)

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        val format = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)


        // --------------------------------------- DUMMY DATA-------------------------------------//
        exercises = ArrayList()

        val exercise1 = Exercise()
        exercise1.name = "Push ups"          // 1
        exercise1.reps = "30 Reps"
        exercise1.timeOfRep = 60L
        exercise1.id = "1"
        exercises!!.add(exercise1)

        val exercise2 = Exercise()
        exercise2.name = "Flat bench barbell"         // 2
        exercise2.reps = "3*12 Reps"
        exercise2.timeOfRep = 30L
        exercise2.id = "2"
        exercises!!.add(exercise2)

        val exercise3 = Exercise()
        exercise3.name = "Inclined bench barbell"         // 3
        exercise3.reps = "3*12 Reps"
        exercise3.timeOfRep = 120L
        exercise3.id = "3"
        exercises!!.add(exercise3)

        val exercise4 = Exercise()
        exercise4.name = "Cable fly"          // 4
        exercise4.reps = "2*12 Reps"
        exercise4.timeOfRep = 180L
        exercise4.id = "4"
        exercises!!.add(exercise4)

        val exercise5 = Exercise()
        exercise5.name = "Dec fly machine"          // 5
        exercise5.reps = "4*12"
        exercise5.timeOfRep = 120L
        exercise5.id = "5"
        exercises!!.add(exercise5)

        val exercise6 = Exercise()
        exercise6.name = "Hip twister"          // 6
        exercise6.reps = "50 Reps"
        exercise6.timeOfRep = 300L
        exercise6.id = "6"
        exercises!!.add(exercise6)

        val exercise7 = Exercise()
        exercise7.name = "dumbbell side bend"           // 7
        exercise7.reps = "3*10"
        exercise7.timeOfRep = 200L
        exercise7.id = "7"
        exercises!!.add(exercise7)

        val exercise8 = Exercise()
        exercise8.name = "dumbbell fly floor"           // 8
        exercise8.reps = "3*10"
        exercise8.timeOfRep = 600L
        exercise8.id = "8"
        exercises!!.add(exercise8)


        dayWiseExersice = DayWiseExersice()
        dayWiseExersice!!.setCat_name("Chest WorkOut")
        dayWiseExersice!!.setId("1")
        dayWiseExersice!!.setTitle("DAY - 1")
        dayWiseExersice!!.setDateOfExercise("20-9-2018")
        dayWiseExersice!!.setExerciseArrayList(exercises)

        dayWiseExersiceArrayList = java.util.ArrayList<DayWiseExersice>()
        dayWiseExersiceArrayList!!.add(dayWiseExersice!!)





        rvList.layoutManager = LinearLayoutManager(this@ShowExercisesActivity,LinearLayoutManager.VERTICAL,false)
        rvList.adapter = CustomAdapter(dayWiseExersiceArrayList!!)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }


    fun getCurrentDate(): String {

        val c = Calendar.getInstance().time
        println("Current time => $c")

        val df = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        return df.format(c)

    }

    inner class CustomAdapter(private val dataSet: ArrayList<DayWiseExersice>) :
            RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

        inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

            val tvCatName: TextView
            val tvTotalTime: TextView
            val tvStatus: TextView
            val tvDate: TextView
            val tvTitle: TextView


            init {
                // Define click listener for the ViewHolder's View.

                tvCatName = v.findViewById<TextView>(R.id.tvExerciseCategory)
                tvTotalTime = v.findViewById<TextView>(R.id.tvTotalDuration)

                tvStatus = v.findViewById<TextView>(R.id.tvStatus)

                tvDate = v.findViewById<TextView>(R.id.tvDate)
                tvTitle = v.findViewById<TextView>(R.id.tvDay)

                v.setOnClickListener {

                    val intent = Intent(this@ShowExercisesActivity,ExerciseDetailActivity::class.java)
                    intent.putParcelableArrayListExtra("data",dataSet[adapterPosition].exerciseArrayList)
                    intent.putExtra("ex_title",dataSet[adapterPosition].title)
                    intent.putExtra("flag",true)
                    startActivity(intent)
                }


            }
        }

        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            // Create a new view.
            val v = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.row_show_exercises, viewGroup, false)

            return ViewHolder(v)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

            // Get element from your dataset at this position and replace the contents of the view
            // with that element

            viewHolder.tvTitle.text = dataSet[position].title
            viewHolder.tvCatName.text = dataSet[position].cat_name
            viewHolder.tvDate.text = dataSet[position].dateOfExercise
            viewHolder.tvStatus.text = dataSet[position].status


        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = dataSet.size


    }

    companion object {
        private val TAG = "CustomAdapter"
    }


}
