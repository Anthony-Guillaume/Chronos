package com.example.chronos.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chronos.data.models.Circuit
import com.example.chronos.data.models.State
import com.example.chronos.data.repositories.CircuitRepository
import com.example.chronos.data.services.CircuitHandler

class CircuitViewModel(repository: CircuitRepository) : ViewModel()
{
    val circuits: LiveData<MutableList<Circuit>> = repository.getCircuits()

    private val _time: MutableLiveData<Long> = MutableLiveData()
    val time get() = _time as LiveData<Long>

    private val _state: MutableLiveData<State> = MutableLiveData()
    val state get() = _state as LiveData<State>

    private var circuitSelected: Circuit? = circuits.value?.first()
    private var circuitHandler = CircuitHandler(circuitSelected!!, _state, _time)

    fun start()
    {
        circuitHandler.start()
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
        circuitHandler = CircuitHandler(circuitSelected!!, _state, _time)
    }
}