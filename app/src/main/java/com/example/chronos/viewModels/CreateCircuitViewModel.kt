package com.example.chronos.viewModels

import android.util.Log
import androidx.lifecycle.*
import com.example.chronos.data.entities.Circuit
import com.example.chronos.data.repositories.CircuitRepository
import com.example.chronos.data.services.CircuitUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateCircuitViewModel(private val circuitRepository: CircuitRepository) : ViewModel()
{
    private var circuitToSave: Circuit? = null

    private val _circuits: MutableLiveData<MutableList<Circuit>> = MutableLiveData()

    private val _circuit: MutableLiveData<Circuit> = MutableLiveData(CircuitUtils.getDefaultCircuit())
    val circuit get() = _circuit as LiveData<Circuit>

    private fun notifyDataChanged()
    {
        _circuit.postValue(_circuit.value) // to force update even if only one property has changed
    }

    fun fetchData()
    {
        viewModelScope.launch {
            _circuits.value = circuitRepository.getAll()
        }
    }

    fun isAlreadyCircuitWithTitle() : Boolean
    {
        if (_circuit.value == null)
        {
            return false
        }
        _circuits.value?.forEach { c ->
            if (c.title == _circuit.value!!.title)
            {
                return true
            }
        }
        return false
    }

    fun save()
    {
        _circuit.value?.let {
            circuitToSave = it.copy()
            viewModelScope.launch(Dispatchers.IO) {
                circuitRepository.add(circuitToSave!!)
            }
        }
    }

    fun deleteLastSave()
    {
        circuitToSave?.let {
            viewModelScope.launch(Dispatchers.IO) {
                circuitRepository.delete(it)
            }
        }
    }

    fun updateTitle(title: String)
    {
        _circuit.value?.let { it.title = title }
    }

    fun decrementWarmupDuration()
    {
        _circuit.value?.also { CircuitUtils.modifyWarmup(it, -1000) }
        notifyDataChanged()
    }

    fun incrementWarmupDuration()
    {
        _circuit.value?.also { CircuitUtils.modifyWarmup(it, 1000) }
        notifyDataChanged()
    }

    fun decrementWorkoutDuration()
    {
        _circuit.value?.also { CircuitUtils.modifyWorkout(it, -1000) }
        notifyDataChanged()
    }

    fun incrementWorkoutDuration()
    {
        _circuit.value?.also { CircuitUtils.modifyWorkout(it, 1000) }
        notifyDataChanged()
    }

    fun decrementExerciseRestDuration()
    {
        _circuit.value?.also { CircuitUtils.modifyExerciseRest(it, -1000) }
        notifyDataChanged()
    }

    fun incrementExerciseRestDuration()
    {
        _circuit.value?.also { CircuitUtils.modifyExerciseRest(it, 1000) }
        notifyDataChanged()
    }

    fun decrementSetRestDuration()
    {
        _circuit.value?.also { CircuitUtils.modifyRestBetweenSets(it, -1000) }
        notifyDataChanged()
    }

    fun incrementSetRestDuration()
    {
        _circuit.value?.also { CircuitUtils.modifyRestBetweenSets(it, 1000) }
        notifyDataChanged()
    }

    fun decrementRound()
    {
        _circuit.value?.also { CircuitUtils.modifyNumberOfRounds(it, -1) }
        notifyDataChanged()
    }

    fun incrementRound()
    {
        _circuit.value?.also { CircuitUtils.modifyNumberOfRounds(it, 1) }
        notifyDataChanged()
    }

    fun decrementSet()
    {
        _circuit.value?.also { CircuitUtils.modifyNumberOfSets(it, -1) }
        notifyDataChanged()
    }

    fun incrementSet()
    {
        _circuit.value?.also { CircuitUtils.modifyNumberOfSets(it, 1) }
        notifyDataChanged()
    }
}