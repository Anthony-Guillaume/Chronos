package com.example.chronos.data.services

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.chronos.data.entities.Circuit
import com.example.chronos.data.entities.Exercise
import com.example.chronos.data.entities.State
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class TestCircuitStateHandler
{
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val state: MutableLiveData<State> = MutableLiveData()
    private val typicalCircuit1 = Circuit(
        "",
        10,
        10,
        Exercise(10, 10, 3),
        3)
    private val circuitZeroRound = Circuit(
        "",
        10,
        10,
        Exercise(10, 10, 0),
        3)
    private val circuitOneSet = Circuit(
        "",
        10,
        10,
        Exercise(10, 10, 2),
        1)
    private val circuitOneRound = Circuit(
        "",
        10,
        10,
        Exercise(10, 10, 1),
        2)
    private val circuitZeroSet = Circuit(
        "",
        20,
        20,
        Exercise(20, 20, 2),
        0)

    @Test
    fun nextInTypicalCase()
    {
        val sut = CircuitStateHandler(typicalCircuit1, state)
        sut.init()
        sut.handle()

        Assert.assertEquals(State.Warmup, state.value)
        sut.handle()
        Assert.assertEquals(State.Workout, state.value)
        sut.handle()
        Assert.assertEquals(State.ExerciseResting, state.value)
        sut.handle()
        Assert.assertEquals(State.Workout, state.value)
        sut.handle()
        Assert.assertEquals(State.ExerciseResting, state.value)
        sut.handle()
        Assert.assertEquals(State.Workout, state.value)
        sut.handle()

        Assert.assertEquals(State.SetResting, state.value)
        sut.handle()

        Assert.assertEquals(State.Workout, state.value)
        sut.handle()
        Assert.assertEquals(State.ExerciseResting, state.value)
        sut.handle()
        Assert.assertEquals(State.Workout, state.value)
        sut.handle()
        Assert.assertEquals(State.ExerciseResting, state.value)
        sut.handle()
        Assert.assertEquals(State.Workout, state.value)
        sut.handle()

        Assert.assertEquals(State.SetResting, state.value)
        sut.handle()

        Assert.assertEquals(State.Workout, state.value)
        sut.handle()
        Assert.assertEquals(State.ExerciseResting, state.value)
        sut.handle()
        Assert.assertEquals(State.Workout, state.value)
        sut.handle()
        Assert.assertEquals(State.ExerciseResting, state.value)
        sut.handle()
        Assert.assertEquals(State.Workout, state.value)
        sut.handle()

        Assert.assertEquals(State.Done, state.value)
    }

    @Test
    fun handleOneSet()
    {
        val sut = CircuitStateHandler(circuitOneSet, state)
        sut.init()
        Assert.assertEquals(state.value, State.Idle)
        sut.handle()
        Assert.assertEquals(state.value, State.Warmup)

        sut.handle()
        Assert.assertEquals(state.value, State.Workout)
        sut.handle()
        Assert.assertEquals(state.value, State.ExerciseResting)
        sut.handle()
        Assert.assertEquals(state.value, State.Workout)

        sut.handle()
        Assert.assertEquals(state.value, State.Done)
    }

    @Test
    fun handleOneRoundPerExercise()
    {
        val sut = CircuitStateHandler(circuitOneRound, state)
        sut.init()
        Assert.assertEquals(state.value, State.Idle)
        sut.handle()
        Assert.assertEquals(state.value, State.Warmup)
        sut.handle()

        Assert.assertEquals(state.value, State.Workout)
        sut.handle()

        Assert.assertEquals(state.value, State.SetResting)
        sut.handle()

        Assert.assertEquals(state.value, State.Workout)
        sut.handle()

        Assert.assertEquals(state.value, State.Done)
        sut.handle()
    }

    @Test
    fun handleZeroRoundPerExercise()
    {
        val sut = CircuitStateHandler(circuitZeroRound, state)
        sut.init()
        Assert.assertEquals(state.value, State.Idle)
        sut.handle()
        Assert.assertEquals(state.value, State.Warmup)
        sut.handle()
        Assert.assertEquals(state.value, State.Done)
    }

    @Test
    fun handleZeroSet()
    {
        val sut = CircuitStateHandler(circuitZeroSet, state)
        sut.init()
        Assert.assertEquals(state.value, State.Idle)
        sut.handle()
        Assert.assertEquals(state.value, State.Warmup)
        sut.handle()
        Assert.assertEquals(state.value, State.Done)
    }
}