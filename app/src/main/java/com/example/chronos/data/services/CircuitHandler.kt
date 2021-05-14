package com.example.chronos.data.services

import androidx.lifecycle.MutableLiveData
import com.example.chronos.data.models.Circuit
import com.example.chronos.data.models.State


class CircuitHandler(
    private val circuit: Circuit,
    private val state: MutableLiveData<State>,
    private val time: MutableLiveData<Long>,
    private val onFinish: ((State) -> Unit))
{
    private var timeCache: Long = 0
    var paused: Boolean = false
        private set
    private val circuitStateHandler = CircuitStateHandler(circuit, state)
    private val circuitTimer = CircuitTimer(object: CircuitTimer.CircuitTimerListener {
        override fun onTick(millisUntilFinished: Long)
        {
            timeCache = millisUntilFinished
            time.value = (millisUntilFinished)
        }

        override fun onFinish()
        {
            circuitStateHandler.handle()
            start()
            state.value?.let { onFinish.invoke(it) }
        }
    })

    init
    {
        circuitStateHandler.init()
        time.value = circuit.warmup
    }

    fun start()
    {
        circuitTimer.stop()
        if (paused)
        {
            circuitTimer.start((timeCache))
            paused = false
            return
        }
        when (state.value)
        {
            State.Idle -> {
                circuitStateHandler.handle()
                circuitTimer.start(circuit.warmup)
            }
            State.Warmup -> circuitTimer.start(circuit.warmup)
            State.Workout -> circuitTimer.start(circuit.exercise.workout)
            State.ExerciseResting -> circuitTimer.start(circuit.exercise.rest)
            State.SetResting -> circuitTimer.start(circuit.restBetweenSets)
            State.Done -> return
        }
    }

    fun stop()
    {
        paused = true
        circuitTimer.stop()
    }

    fun reset()
    {
        paused = false
        circuitTimer.stop()
        time.value = circuit.warmup
        timeCache = 0
        circuitStateHandler.init()
    }
}