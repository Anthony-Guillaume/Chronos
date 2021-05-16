package com.example.chronos.viewModels

import androidx.lifecycle.*
import com.example.chronos.data.entities.Circuit
import com.example.chronos.data.entities.CircuitHistory
import com.example.chronos.data.entities.State
import com.example.chronos.data.repositories.CircuitHistoryRepository
import com.example.chronos.data.repositories.CircuitRepository
import com.example.chronos.data.services.CircuitHandler
import com.example.chronos.data.services.CircuitUtils
import com.example.chronos.views.utils.DateHelper
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class TrainingChronoViewModel(
    private val circuitRepository: CircuitRepository,
    private val circuitHistoryRepository: CircuitHistoryRepository)
    : ViewModel()
{
    private val _circuits: MutableLiveData<MutableList<Circuit>> = MutableLiveData()
    val circuits get() = _circuits as LiveData<MutableList<Circuit>>

    private val _time: MutableLiveData<Long> = MutableLiveData()
    val time get() = _time as LiveData<Long>

    private val _canStart: MutableLiveData<Boolean> = MutableLiveData(true)
    val canStart get() = _canStart as LiveData<Boolean>

    private val _state: MutableLiveData<State> = MutableLiveData()
    val state get() = _state as LiveData<State>

    private var circuitSelected: Circuit
    private var circuitHandler: CircuitHandler
    private var circuitHistory: CircuitHistory? = null

    init
    {
        when (circuits.value?.isEmpty())
        {
            true, null -> {
                circuitSelected = CircuitUtils.getDefaultCircuit()
                circuitHandler = CircuitHandler(circuitSelected, _state, _time, ::updateHistory)
                viewModelScope.launch(IO) {
                    circuitRepository.add(circuitSelected)
                }
            }
            false -> {
                circuitSelected = circuits.value?.first()!!
                circuitHandler = CircuitHandler(circuitSelected, _state, _time, ::updateHistory)
            }
        }
    }

    fun fetchData()
    {
        viewModelScope.launch {
            _circuits.value = circuitRepository.getAll()
            when (circuits.value?.isEmpty())
            {
                true, null -> {
                    circuitSelected = CircuitUtils.getDefaultCircuit()
                    circuitHandler = CircuitHandler(circuitSelected, _state, _time, ::updateHistory)
                    viewModelScope.launch(IO) {
                        circuitRepository.add(circuitSelected)
                    }
                }
                false -> {
                    circuitSelected = circuits.value?.first()!!
                    circuitHandler = CircuitHandler(circuitSelected, _state, _time, ::updateHistory)
                }
            }
        }
    }

    private fun updateHistory(state: State)
    {
        val history: CircuitHistory = circuitHistory ?: return
        when (state)
        {
            State.SetResting -> ++history.numberOfSetsDone
            State.Done -> {
                ++history.numberOfSetsDone
                viewModelScope.launch(IO) {
                    circuitHistoryRepository.add(history)
                }
            }
            else -> return
        }
    }

    fun start()
    {
        if (_canStart.value == true)
        {
            circuitHandler.start()
            val circuit = circuitSelected
            circuitHistory = CircuitHistory(circuit.title,
                DateHelper.getCurrentDate(),
                circuit.exercise.numberOfRounds,
                circuit.numberOfSets,
                0)
            _canStart.value = false
        }
    }

    fun stop()
    {
        if (_canStart.value == false)
        {
            circuitHandler.stop()
            _canStart.value = true
        }
    }

    fun reset()
    {
        if (_canStart.value == false)
        {
            circuitHandler.reset()
            _canStart.value = true
        }
    }

    fun updateCircuitSelected(index: Int)
    {
        circuitHandler.stop()
        circuits.value?.let {
            circuitSelected = it[index]
            circuitHandler = CircuitHandler(circuitSelected, _state, _time, ::updateHistory)
        }
    }
}