package com.example.admin.mealplanner2new.Fragments

import android.app.Fragment
import android.app.FragmentTransaction
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.admin.mealplanner2new.Models.Exercise
import com.example.admin.mealplanner2new.R
import com.example.admin.mealplanner2new.Views.StartExerciseActivity
import kotlinx.android.synthetic.main.fragment_start_exercise.*

class StartExerciseFragment : Fragment() {


    var countDownTimer: CountDownTimer? = null
    var endValue = 30L
    var remainingTime = endValue
    var isCountDownTimerEnable = false
    var progressStatus = 0
    var isfromPauseButton = false
    var exersiceName = ""
    var exerciseId = 0
    var exerciseReps = ""
    lateinit var contexts: Context
    lateinit var exerciseList: ArrayList<Exercise>
    var timeInMilliseconds = 0L


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        contexts = context!! as StartExerciseActivity
        exerciseList = (contexts as StartExerciseActivity).exerciseList
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            endValue = arguments.getLong("time", 0L)
            exerciseId = arguments.getInt("ex_id")
            exerciseReps = arguments.getString("ex_rep")
            exersiceName = arguments.getString("ex_name")
            remainingTime = endValue
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_start_exercise, container, false)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setProgress(progressStatus, endValue.toInt())

        val seconds = (endValue * 1000 / 1000).toInt() % 60
        val minutes = (endValue * 1000 / (1000 * 60) % 60).toInt()
        val hours = (endValue * 1000 / (1000 * 60 * 60) % 24).toInt()
        val newtime = hours.toString() + ":" + minutes + ":" + seconds
        tvCountDown?.text = newtime.toString()
        tvExerciseName?.text = exersiceName
        tvNoOfRep?.text = exerciseReps


        btnSkip?.setOnClickListener {


            if (SystemClock.elapsedRealtime() - timeInMilliseconds < 1000) {
                return@setOnClickListener
            }
            timeInMilliseconds = SystemClock.elapsedRealtime()

            if (exerciseId < exerciseList.size - 1 && !isCountDownTimerEnable) {


                val startExerciseFragment = StartExerciseFragment()
                val bundle = Bundle()
                bundle.putLong("time", exerciseList[exerciseId + 1].timeOfRep)
                bundle.putString("ex_name", exerciseList[exerciseId + 1].name)
                bundle.putString("ex_rep", exerciseList[exerciseId + 1].reps)
                bundle.putInt("ex_id", exerciseId + 1)
                startExerciseFragment.arguments = bundle

                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.animator.fragment_slide_left_enter,
                                R.animator.fragment_slide_left_exit,R.animator.fragment_slide_right_enter,
                                R.animator.fragment_slide_right_exit)
                        .add(R.id.container_exercise, startExerciseFragment, (exerciseId).toString())
                        .addToBackStack((exerciseId).toString())
                        .hide(this@StartExerciseFragment)
                        .commit()
            }


        }


        btnNext?.setOnClickListener {

            if (!isCountDownTimerEnable && remainingTime == 0L && exerciseId < exerciseList.size - 1) {

                val startExerciseFragment = StartExerciseFragment()
                val bundle = Bundle()
                bundle.putLong("time", exerciseList[exerciseId + 1].timeOfRep)
                bundle.putString("ex_name", exerciseList[exerciseId + 1].name)
                bundle.putString("ex_rep", exerciseList[exerciseId + 1].reps)
                bundle.putInt("ex_id", exerciseId + 1)
                startExerciseFragment.arguments = bundle

                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.animator.fragment_slide_right_enter,
                                R.animator.fragment_slide_right_exit,R.animator.fragment_slide_right_enter,
                                R.animator.fragment_slide_right_exit)
                        .add(R.id.container_exercise, startExerciseFragment, (exerciseId).toString())
                        .addToBackStack((exerciseId).toString())
                        .hide(this@StartExerciseFragment)
                        .commit()


            }


        }


        ivPlay?.setOnClickListener {

            if (!isCountDownTimerEnable && remainingTime > 0) {

                startCountDown(remainingTime)
                ivPlay?.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp)

            } else if (remainingTime > 0) {

                if (countDownTimer != null) {
                    countDownTimer!!.cancel()
                    countDownTimer!!.onFinish()
                }

                ivPlay?.setImageResource(R.drawable.ic_play_circle_outline_black_24dp)

            }


        }


        ivReset.setOnClickListener {

            if (countDownTimer != null) {
                countDownTimer!!.cancel()
                countDownTimer!!.onFinish()
                remainingTime = endValue

                val seconds = (remainingTime * 1000 / 1000).toInt() % 60
                val minutes = (remainingTime * 1000 / (1000 * 60) % 60).toInt()
                val hours = (remainingTime * 1000 / (1000 * 60 * 60) % 24).toInt()
                val newtime = hours.toString() + ":" + minutes + ":" + seconds
                tvCountDown?.text = newtime.toString()
                progressStatus = 0
                setProgress(progressStatus, remainingTime.toInt())
            }


        }


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    override fun onResume() {
        super.onResume()
    }

    fun startCountDown(endTime: Long) {
        countDownTimer = object : CountDownTimer(endTime * 1000, 1000) {
            override fun onFinish() {

                if (remainingTime.toInt() == 1) {
                    isCountDownTimerEnable = false
                    progressStatus += 1
                    remainingTime -= 1
                    ivPlay?.setImageResource(R.drawable.ic_play_circle_outline_black_24dp)

                    setProgress(progressStatus, endValue.toInt())
                    Log.e("progress", progressStatus.toString())

                    val seconds = (remainingTime * 1000 / 1000).toInt() % 60
                    val minutes = (remainingTime * 1000 / (1000 * 60) % 60).toInt()
                    val hours = (remainingTime * 1000 / (1000 * 60 * 60) % 24).toInt()
                    val newtime = hours.toString() + ":" + minutes + ":" + seconds
                    tvCountDown.text = newtime.toString()


                } else {
                    isCountDownTimerEnable = false

                    ivPlay?.setImageResource(R.drawable.ic_play_circle_outline_black_24dp)

                    setProgress(progressStatus, endValue.toInt())
                    //  Log.e("progress",progressStatus.toString())

                    val seconds = (remainingTime * 1000 / 1000).toInt() % 60
                    val minutes = (remainingTime * 1000 / (1000 * 60) % 60).toInt()
                    val hours = (remainingTime * 1000 / (1000 * 60 * 60) % 24).toInt()
                    val newtime = hours.toString() + ":" + minutes + ":" + seconds
                    tvCountDown?.text = newtime.toString()
                }


            }

            override fun onTick(millisUntilFinished: Long) {

                progressStatus += 1
                setProgress(progressStatus, endValue.toInt())
                //Log.e("progress",progressStatus.toString())

                remainingTime = millisUntilFinished / 1000
                Log.e("remaining", remainingTime.toString())

                isCountDownTimerEnable = true
                val seconds = (millisUntilFinished / 1000).toInt() % 60
                val minutes = (millisUntilFinished / (1000 * 60) % 60).toInt()
                val hours = (millisUntilFinished / (1000 * 60 * 60) % 24).toInt()
                val newtime = hours.toString() + ":" + minutes + ":" + seconds

                tvCountDown?.text = newtime

//                if (remainingTime == 1L){
//                    countDownTimer?.cancel()
//                    countDownTimer?.onFinish()
//                }


            }

        }

        countDownTimer!!.start()

    }

    fun setProgress(startTime: Int, endTime: Int) {


        progress_bar?.progress = startTime.toFloat()
        progress_bar?.progressMax = endTime.toFloat()

    }


}