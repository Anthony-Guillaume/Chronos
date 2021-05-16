package com.example.chronos.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chronos.data.entities.Circuit
import com.example.chronos.data.repositories.CircuitRepository
import com.example.chronos.data.repositories.SharedCircuitRepository
import com.example.chronos.data.services.CircuitUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditMyCircuitViewModel(
    private val circuitRepository: CircuitRepository,
    private val sharedCircuitRepository: SharedCircuitRepository)
    : ViewModel()
{
    private val _circuit: MutableLiveData<Circuit> = MutableLiveData()
    val circuit get() = _circuit as LiveData<Circuit>

    private fun notifyDataChanged()
    {
        _circuit.postValue(_circuit.value) // to force update even if only one property has changed
    }

    fun fetchData()
    {
        viewModelScope.launch {
            _circuit.value = sharedCircuitRepository.circuitToEdit
        }
    }

    fun save()
    {
        _circuit.value?.let {
            val circuitToSave = it.copy()
            viewModelScope.launch(Dispatchers.IO) {
                circuitRepository.add(circuitToSave)
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