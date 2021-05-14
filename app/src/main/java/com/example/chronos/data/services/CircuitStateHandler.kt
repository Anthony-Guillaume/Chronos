package com.example.chronos.data.services

import androidx.lifecycle.MutableLiveData
import com.example.chronos.data.models.Circuit
import com.example.chronos.data.models.State


class CircuitStateHandler(private val circuit: Circuit,
                          private val state: MutableLiveData<State>)
{
    private var exerciseCount: Int = 0
    private var setCount: Int = 0

    fun init()
    {
        state.value = State.Idle
        exerciseCount = 0
        setCount = 0
    }

    fun handle()
    {
        when (state.value)
        {
            State.Idle -> handleIdle()
            State.Warmup -> handleWarmup()
            State.Workout -> handleWorkout()
            State.ExerciseResting -> handleExerciseResting()
            State.SetResting -> handleSetResting()
            State.Done -> handleDone()
        }
    }

    private fun handleIdle()
    {
        state.value = State.Warmup
    }

    private fun handleWarmup()
    {
        if ((circuit.numberOfSets == 0) or (circuit.exercise.numberOfRounds == 0))
        {
            state.value = State.Done
        }
        else
        {
            state.value = State.Workout
            setCount = 0
            exerciseCount = 0
        }
    }

    private fun handleWorkout()
    {
        ++exerciseCount
        if (setCount == circuit.numberOfSets)
        {
            state.value = State.Done
        }
        else
        {
            if (exerciseCount == circuit.exercise.numberOfRounds)
            {
                ++setCount
                if (setCount == circuit.numberOfSets)
                {
                    state.value = State.Done
                }
                else
                {
                    state.value = State.SetResting
                }
            }
            else
            {
                state.value = State.ExerciseResting
            }
        }
    }

    private fun handleExerciseResting()
    {
        state.value = State.Workout
    }

    private fun handleSetResting()
    {
        state.value = State.Workout
        exerciseCount = 0
    }

    private fun handleDone()
    {
    }
}