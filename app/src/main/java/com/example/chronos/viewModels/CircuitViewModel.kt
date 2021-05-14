package com.example.chronos.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chronos.data.models.Circuit
import com.example.chronos.data.models.CircuitHistory
import com.example.chronos.data.models.State
import com.example.chronos.data.repositories.CircuitHistoryRepository
import com.example.chronos.data.repositories.CircuitRepository
import com.example.chronos.data.services.CircuitHandler
import com.example.chronos.views.utils.DateHelper

class CircuitViewModel(
    circuitRepository: CircuitRepository,
    private val circuitHistoryRepository: CircuitHistoryRepository) : ViewModel()
{
    val circuits: LiveData<MutableList<Circuit>> = circuitRepository.getAll()
    val circuitHistories: LiveData<MutableList<CircuitHistory>> = circuitHistoryRepository.getAll()

    private val _time: MutableLiveData<Long> = MutableLiveData()
    val time get() = _time as LiveData<Long>

    private val _state: MutableLiveData<State> = MutableLiveData()
    val state get() = _state as LiveData<State>

    private var circuitSelected: Circuit? = circuits.value?.first()
    private var circuitHistory: CircuitHistory? = null
    private var circuitHandler = CircuitHandler(circuitSelected!!, _state, _time, ::updateHistory)

    private fun updateHistory(state: State)
    {
        val history: CircuitHistory = circuitHistory ?: return
        when (state)
        {
            State.SetResting -> ++history.numberOfSetsDone
            State.Done -> {
                ++history.numberOfSetsDone
                circuitHistoryRepository.add(history)
            }
            else -> return
        }
    }

    fun start()
    {
        circuitHandler.start()
        val circuit = circuitSelected as Circuit
        circuitHistory = CircuitHistory(circuit.title,
            DateHelper.getCurrentDate(),
            circuit.exercise.numberOfRounds,
            circuit.numberOfSets,
            0)
    }

    fun stop()
    {
        circuitHandler.stop()
    }

    fun reset()
    {
        circuitHandler.reset()
    }

    fun updateCircuitSelected(index: Int)
    {
        circuitSelected = circuits.value?.get(index)
        circuitHandler.stop()
        circuitHandler = CircuitHandler(circuitSelected!!, _state, _time, ::updateHistory)
    }
}