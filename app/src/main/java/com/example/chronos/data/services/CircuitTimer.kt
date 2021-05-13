package com.example.chronos.data.services

import android.os.CountDownTimer

class CircuitTimer(private val listener: CircuitTimerListener)
{
    interface CircuitTimerListener
    {
        fun onTick(millisUntilFinished: Long)
        fun onFinish()
    }

    var countDownInterval: Long = 100
    private var _timer: CountDownTimer? = null
    private val timer get() = _timer!!

    fun start(duration: Long)
    {
        _timer?.cancel()
        setCountDownTimer(duration)
        timer.start()
    }

    fun stop()
    {
        _timer?.cancel()
        _timer = null
    }

    private fun setCountDownTimer(duration: Long)
    {
        _timer = object: CountDownTimer(duration, countDownInterval) {
            override fun onTick(millisUntilFinished: Long)
            {
                listener.onTick(millisUntilFinished)
            }

            override fun onFinish()
            {
                listener.onFinish()
            }
        }
    }
}