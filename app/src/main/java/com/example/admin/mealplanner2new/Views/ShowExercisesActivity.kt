package com.example.admin.mealplanner2new.Views

import android.content.Intent
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.CardView
import android.util.Log
import android.view.MenuItem
import android.view.View

import com.example.admin.mealplanner2new.R

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

import kotlinx.android.synthetic.main.activity_show_exercises.*

class ShowExercisesActivity : AppCompatActivity(), View.OnClickListener {


    override fun onClick(v: View?) {

        val intent = Intent(this@ShowExercisesActivity, ExerciseDetailActivity::class.java)

        when (v?.id) {


            R.id.cvMonday -> {

                if (getCurrentDate() == cvMonday.tag) {
                    // Today is monday
                    intent.putExtra("flag", true)
                    startActivity(intent)

                } else {
                    intent.putExtra("flag", false)
                    startActivity(intent)
                }


            }

            R.id.cvTuesday -> {

                if (getCurrentDate() == cvTuesday.tag) {

                    intent.putExtra("flag", true)
                    startActivity(intent)
                } else {
                    intent.putExtra("flag", false)
                    startActivity(intent)
                }


            }

            R.id.cvWednesday -> {
                if (getCurrentDate() == cvWednesday.tag) {
                    intent.putExtra("flag", true)
                    startActivity(intent)

                } else {
                    intent.putExtra("flag", false)
                    startActivity(intent)
                }
            }

            R.id.cvThursDay -> {
                if (getCurrentDate() == cvThursDay.tag) {
                    intent.putExtra("flag", true)
                    startActivity(intent)

                } else {
                    intent.putExtra("flag", false)
                    startActivity(intent)
                }
            }

            R.id.cvFriday -> {
                if (getCurrentDate() == cvFriday.tag) {
                    intent.putExtra("flag", true)
                    startActivity(intent)

                } else {
                    intent.putExtra("flag", false)
                    startActivity(intent)
                }
            }

            R.id.cvSaturday -> {
                if (getCurrentDate() == cvSaturday.tag) {
                    intent.putExtra("flag", true)
                    startActivity(intent)

                } else {
                    intent.putExtra("flag", false)
                    startActivity(intent)
                }
            }

            R.id.cvSunday -> {
                if (getCurrentDate() == cvSunday.tag) {
                    intent.putExtra("flag", true)
                    startActivity(intent)

                } else {
                    intent.putExtra("flag", false)
                    startActivity(intent)
                }
            }


        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_exercises)

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        val format = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)

        cvMonday.setOnClickListener(this)
        cvTuesday.setOnClickListener(this)
        cvWednesday.setOnClickListener(this)
        cvThursDay.setOnClickListener(this)
        cvFriday.setOnClickListener(this)
        cvSaturday.setOnClickListener(this)
        cvSunday.setOnClickListener(this)


        val daysArray = arrayOfNulls<String>(7)
        for (i in 0..6) {
            daysArray[i] = format.format(calendar.time)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            Log.e("dates: ", daysArray[i])

            when (i) {

                0 -> {

                    tvDate.text = daysArray[i]
                    cvMonday.tag = daysArray[i]

                }
                1 -> {
                    tvDate2.text = daysArray[i]
                    cvTuesday.tag = daysArray[i]

                }
                2 -> {
                    tvDate3.text = daysArray[i]
                    cvWednesday.tag = daysArray[i]

                }
                3 -> {
                    tvDate4.text = daysArray[i]
                    cvThursDay.tag = daysArray[i]

                }
                4 -> {
                    tvDate5.text = daysArray[i]
                    cvFriday.tag = daysArray[i]

                }
                5 -> {
                    tvDate6.text = daysArray[i]
                    cvSaturday.tag = daysArray[i]

                }
                6 -> {
                    tvDate7.text = daysArray[i]
                    cvSunday.tag = daysArray[i]

                }
            }


        }


        //        cardView_tuesDay.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                startActivity(new Intent(ShowExercisesActivity.this, ExerciseDetailActivity.class));
        //            }
        //        });

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

}
