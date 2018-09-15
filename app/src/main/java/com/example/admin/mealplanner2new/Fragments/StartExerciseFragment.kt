package com.example.admin.mealplanner2new.Fragments

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.admin.mealplanner2new.R
import kotlinx.android.synthetic.main.fragment_start_exercise.*

class StartExerciseFragment : Fragment() {


    var countDownTimer : CountDownTimer? = null
    val endValue = 30L
    var remainingTime = endValue
    var isCountDownTimerEnable = false
    var progressStatus = 0


    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_start_exercise,container,false)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setProgress(progressStatus,remainingTime.toInt())
         ivPlay.setOnClickListener {

             if(!isCountDownTimerEnable && (remainingTime).toInt() > 0){

                  startCountDown(remainingTime)
                  ivPlay.setImageResource(R.drawable.ic_pause_circle_outline_black_24dp)



             }
             else{

                 if (countDownTimer!=null){
                     countDownTimer!!.cancel()
                     countDownTimer!!.onFinish()
                 }

                 ivPlay.setImageResource(R.drawable.ic_play_circle_outline_black_24dp)

             }




         }


        ivReset.setOnClickListener {

            if (countDownTimer!=null){
                countDownTimer!!.cancel()
                countDownTimer!!.onFinish()
                remainingTime = endValue

                val seconds = (remainingTime*1000 / 1000).toInt() % 60
                val minutes = (remainingTime*1000 / (1000 * 60) % 60).toInt()
                val hours = (remainingTime*1000 / (1000 * 60 * 60) % 24).toInt()
                val newtime = hours.toString() + ":" + minutes + ":" + seconds
                tvCountDown.text = newtime.toString()
                progressStatus = 0
                setProgress(progressStatus,remainingTime.toInt())
            }


        }


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    override fun onResume() {
        super.onResume()
    }

    fun startCountDown(endTime:Long){
        countDownTimer = object:CountDownTimer(endTime*1000,1000){
            override fun onFinish() {

                isCountDownTimerEnable = false
                ivPlay.setImageResource(R.drawable.ic_play_circle_outline_black_24dp)
                //setProgress(progressStatus,endTime.toInt())
              //  Log.e("progress",progressStatus.toString())

            }

            override fun onTick(millisUntilFinished: Long) {

                progressStatus += 1
                setProgress(progressStatus,endTime.toInt())
                Log.e("progress",progressStatus.toString())

                remainingTime = millisUntilFinished/1000
                isCountDownTimerEnable = true
                val seconds = (millisUntilFinished / 1000).toInt() % 60
                val minutes = (millisUntilFinished / (1000 * 60) % 60).toInt()
                val hours = (millisUntilFinished / (1000 * 60 * 60) % 24).toInt()
                val newtime = hours.toString() + ":" + minutes + ":" + seconds

                tvCountDown.text = newtime


            }

        }

        countDownTimer!!.start()

    }

    fun setProgress(startTime: Int, endTime: Int) {
        progress_bar.setMax(endTime)
        progress_bar.setSecondaryProgress(endTime)
        progress_bar.setProgress(startTime)

    }
}