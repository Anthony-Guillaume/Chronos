package com.example.chronos.viewModels

import androidx.lifecycle.*
import com.example.chronos.data.entities.Circuit
import com.example.chronos.data.entities.CircuitHistory
import com.example.chronos.data.entities.State
import com.example.chronos.data.repositories.CircuitHistoryRepository
import com.example.chronos.data.repositories.CircuitRepository
import com.example.chronos.data.services.CircuitHandler
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

    private val _canPlaySound: MutableLiveData<Boolean> = MutableLiveData(false)
    val canPlaySound get() = _canPlaySound as LiveData<Boolean>

    private val _state: MutableLiveData<State> = MutableLiveData()
    val state get() = _state as LiveData<State>

    private lateinit var circuitSelected: Circuit
    private lateinit var circuitHandler: CircuitHandler
    private var circuitHistory: CircuitHistory? = null
    private var historySaved: Boolean = false

    private val _needToCreateCircuit: MutableLiveData<Boolean> = MutableLiveData()
    val needToCreateCircuit get() = _needToCreateCircuit as LiveData<Boolean>

    init
    {
        initialize()
    }

    private fun initialize()
    {
        when (circuits.value?.isEmpty())
        {
            true, null -> {
                _needToCreateCircuit.value = true
            }
            false -> {
                _needToCreateCircuit.value = false
                circuitSelected = circuits.value?.first()!!
                circuitHandler = CircuitHandler(circuitSelected, _state, _time, ::handleSound, ::updateHistory)
            }
        }
    }

    fun fetchData()
    {
        viewModelScope.launch {
            _circuits.value = circuitRepository.getAll()
            initialize()
        }
    }

    private fun handleSound(millisUntilFinished: Long)
    {
        if (millisUntilFinished < 3000 && !_canPlaySound.value!!)
        {
            _canPlaySound.value = true
        }
    }

    fun saveHistory()
    {
        if (!historySaved)
        {
            circuitHistory?.let {
                historySaved = true
                viewModelScope.launch(IO) {
                    circuitHistoryRepository.add(it)
                }
            }
        }
    }

    private fun updateHistory(state: State)
    {
        _canPlaySound.value = false
        val history: CircuitHistory = circuitHistory ?: return
        when (state)
        {
            State.SetResting -> ++history.numberOfSetsDone
            State.Done -> {
                ++history.numberOfSetsDone
                saveHistory()
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
            historySaved = false
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
            _canPlaySound.value = false
            circuitHandler.stop()
            _canStart.value = true
        }
    }

    fun reset()
    {
        if (_canStart.value == false)
        {
            _canPlaySound.value = false
            saveHistory()
            circuitHandler.reset()
            _canStart.value = true
        }
    }

    fun updateCircuitSelected(index: Int)
    {
        circuitHandler.stop()
        circuits.value?.let {
            circuitSelected = it[index]
            circuitHandler = CircuitHandler(circuitSelected, _state, _time, ::handleSound, ::updateHistory)
        }
    }
}