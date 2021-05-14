package com.example.chronos.views.utils

import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View


class ContinuousClickHandler(view: View, val callback: (() -> Unit))
{
    private val handler: Handler = Handler(Looper.getMainLooper())
    private val delay: Long = 75
    private var toBeCall: Boolean = false

    inner class RepeatedRunnable : Runnable
    {
        override fun run()
        {
            if (toBeCall)
            {
                callback()
                handler.postDelayed(this, delay)
            }
        }
    }

    init
    {
        view.setOnClickListener { callback() }
        view.setOnLongClickListener {
            toBeCall = true
            handler.post(RepeatedRunnable())
            false
        }
        view.setOnTouchListener({ v, event ->
            if (event.action == MotionEvent.ACTION_UP && toBeCall)
            {
                toBeCall = false
            }
            false
        })
    }
}