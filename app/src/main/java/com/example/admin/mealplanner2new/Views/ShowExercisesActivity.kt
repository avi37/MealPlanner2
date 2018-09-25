package com.example.admin.mealplanner2new.Views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
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
import android.widget.Toast
import com.example.admin.mealplanner2new.Common.RetrofitClient
import com.example.admin.mealplanner2new.Common.SessionManager
import com.example.admin.mealplanner2new.Models.*

import com.example.admin.mealplanner2new.R

import java.text.DateFormat
import java.text.SimpleDateFormat

import kotlinx.android.synthetic.main.activity_show_exercises.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.time.LocalDate
import java.util.*

class ShowExercisesActivity : AppCompatActivity() {
    private var dayWiseExersice: DayWiseExersice? = null
    private var dayWiseExersiceArrayList: ArrayList<DayWiseExersice>? = null
    private var exercises: ArrayList<Exercise>? = null
    private lateinit var roomDatabase: WordRoomDatabase
    private lateinit var getDayWiseExercise: GetDayWiseExercise
    private val BASE_URL = "http://code-fuel.in/healthbotics/api/auth/"
    private lateinit var token: String
    private lateinit var u_id: String
    private lateinit var currentTimeStamp: String
    private lateinit var nextDayTimeStamp: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_exercises)


        currentTimeStamp = getCurrentDate()
        Log.e("current time", currentTimeStamp)


        token = SessionManager(applicationContext).accessToken
        u_id = SessionManager(applicationContext).keyUId

        roomDatabase = WordRoomDatabase.getDatabase(applicationContext)
        getDayWiseExercise = RetrofitClient.getClient(BASE_URL).create(GetDayWiseExercise::class.java)

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        val format = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)



        getDayWiseExercise.getExerciseDayList(u_id).enqueue(object : Callback<ArrayList<CategoryMaster>> {
            override fun onFailure(call: Call<ArrayList<CategoryMaster>>, t: Throwable) {
                progress_bar.visibility = View.GONE


            }

            override fun onResponse(call: Call<ArrayList<CategoryMaster>>, response: Response<ArrayList<CategoryMaster>>) {
                progress_bar.visibility = View.GONE

                if (response!!.isSuccessful) {

                    if (response.body()!!.isNotEmpty()) {

                        var categoryList = response.body()
                        rvList.layoutManager = LinearLayoutManager(this@ShowExercisesActivity,
                                LinearLayoutManager.VERTICAL, false)
                        rvList.adapter = CustomAdapter(categoryList!!)

                        insertAsyncTask(roomDatabase.wordDao()).execute(categoryList)


                    }


                } else {

                }


            }

        })


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

    inner class CustomAdapter(private val dataSet: ArrayList<CategoryMaster>) :
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

                    val exerciseDateTime = getYMDDate(dataSet[adapterPosition].dateOf).toString()
                    Log.e("exercise time", exerciseDateTime)


                    val intent = Intent(this@ShowExercisesActivity, ExerciseDetailActivity::class.java)
                    // intent.putParcelableArrayListExtra("data", dataSet[adapterPosition].dateOf)
                    intent.putExtra("ex_title", dataSet[adapterPosition].title)
                    intent.putExtra("cat_id", dataSet[adapterPosition].cat_id)
                    intent.putExtra("date", dataSet[adapterPosition].dateOf)
                    intent.putExtra("task_id", dataSet[adapterPosition].task_id)
                    intent.putExtra("com_id",dataSet[adapterPosition].component_id)

                    if (currentTimeStamp == exerciseDateTime && dataSet[adapterPosition].status == "0") {
                        intent.putExtra("flag", true)

                    } else {
                        intent.putExtra("flag", false)
                    }

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

            viewHolder.tvTitle.text = "Day - ".plus(dataSet[position].id)
            viewHolder.tvCatName.text = dataSet[position].title
            viewHolder.tvDate.text = dataSet[position].dateOf

            if (dataSet[position].status == "0") {
                viewHolder.tvStatus.text = "Pending"

            } else if (dataSet[position].status == "1") {
                viewHolder.tvStatus.text = "Completed"

            }


        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = dataSet.size


    }

    companion object {
        private val TAG = "CustomAdapter"
    }


    @SuppressLint("StaticFieldLeak")
    private inner class insertAsyncTask internal constructor(private val mAsyncTaskDao: WordDao) : AsyncTask<ArrayList<CategoryMaster>, Void, Void>() {

        override fun doInBackground(vararg params: ArrayList<CategoryMaster>): Void? {
            mAsyncTaskDao.inserCategoryList(params[0])
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
        }
    }


    interface GetDayWiseExercise {
        @POST("getworkout")
        @FormUrlEncoded
        fun getExerciseDayList(@Field("u_id") u_id: String): Call<ArrayList<CategoryMaster>>


    }

    fun getYMDDate(dateString: String): String {


//        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
//        val date = format.parse(dateString)

        val dateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(dateString)
        return SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(dateTime)

        //Log.e("formated date", newstring)

        // return SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(newstring)


    }

}
