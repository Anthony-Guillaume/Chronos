package com.example.chronos.data.services

import androidx.core.math.MathUtils.clamp
import com.example.chronos.data.entities.Circuit
import com.example.chronos.data.entities.Exercise

object CircuitUtils
{
    private const val minDuration: Long = 0
    private const val maxDuration: Long = 1000 * 60 * 60
    private const val minRound: Long = 1
    private const val maxRound: Long = 100
    private const val minSet: Long = 1
    private const val maxSet: Long = 100

    fun getDefaultCircuit() : Circuit
    {
        return Circuit("Default", 10000, 10000,
            Exercise(10000, 10000, 5),
            5)
    }

    fun modifyNumberOfRounds(circuit: Circuit, amount: Long)
    {
        circuit.exercise.numberOfRounds =
            clamp(circuit.exercise.numberOfRounds + amount, minRound, maxRound).toInt()
    }

    fun modifyNumberOfSets(circuit: Circuit, amount: Long)
    {
        circuit.numberOfSets = clamp(circuit.numberOfSets + amount, minSet, maxSet).toInt()
    }

    fun modifyWarmup(circuit: Circuit, amount: Long)
    {
        circuit.warmup = clamp(circuit.warmup + amount, minDuration, maxDuration)
    }

    fun modifyWorkout(circuit: Circuit, amount: Long)
    {
        circuit.exercise.workout = clamp(circuit.exercise.workout + amount, minDuration, maxDuration)
    }

    fun modifyExerciseRest(circuit: Circuit, amount: Long)
    {
        circuit.exercise.rest = clamp(circuit.exercise.rest + amount, minDuration, maxDuration)
    }

    fun modifyRestBetweenSets(circuit: Circuit, amount: Long)
    {
        circuit.restBetweenSets = clamp(circuit.restBetweenSets + amount, minDuration, maxDuration)
    }
}