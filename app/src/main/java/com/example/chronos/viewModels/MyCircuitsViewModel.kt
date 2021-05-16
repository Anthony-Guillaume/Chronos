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

class MyCircuitsViewModel(
    private val circuitRepository: CircuitRepository,
    private val sharedCircuitRepository: SharedCircuitRepository)
    : ViewModel()
{
    private val _circuits: MutableLiveData<MutableList<Circuit>> = MutableLiveData()
    val circuits get() = _circuits as LiveData<MutableList<Circuit>>
    private val _circuit: MutableLiveData<Circuit> = MutableLiveData(CircuitUtils.getDefaultCircuit())
    val circuit get() = _circuit as LiveData<Circuit>

    fun fetchData()
    {
        viewModelScope.launch {
            _circuits.value = circuitRepository.getAll()
        }
    }

    fun setCircuitToEdit(index: Int)
    {
        sharedCircuitRepository.circuitToEdit = _circuits.value?.get(index)
    }

    fun deleteCircuit(index: Int)
    {
        val circuits = circuits.value ?: return
        val circuit = circuits[index]
        viewModelScope.launch(Dispatchers.IO) {
            circuitRepository.delete(circuit)
        }
    }
}