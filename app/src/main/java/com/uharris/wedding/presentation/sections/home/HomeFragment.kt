package com.uharris.wedding.presentation.sections.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.uharris.wedding.R
import android.os.CountDownTimer
import com.uharris.wedding.utils.StringUtils
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onPause() {
        super.onPause()
        countDown.stop()
        timer.cancel()
    }

    override fun onResume() {
        super.onResume()
        val today = Calendar.getInstance().timeInMillis
        val weddingDay = Calendar.getInstance()
        weddingDay.set(2019, 1, 23, 14, 0, 0)
        val timeInMillis = weddingDay.timeInMillis - today

        countDown.setStartDuration(StringUtils.hoursTime(timeInMillis))

        countDown.start()

        timer = object: CountDownTimer(timeInMillis, 86400000) {
            override fun onFinish() {
                daysTextView.text = "Falta muy poco!!!"
            }

            override fun onTick(millisUntilFinished: Long) {
                val days = StringUtils.daysTime(millisUntilFinished)
                if(days == "01")
                    daysTextView.text = "día"
                else
                    daysTextView.text = "días"
                dayTextView.text = days
            }
        }.start()
    }


    private lateinit var timer: CountDownTimer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}
