package com.example.chronos.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chronos.data.entities.CircuitHistory
import com.example.chronos.data.repositories.CircuitHistoryRepository
import kotlinx.coroutines.launch

class TrainingHistoryViewModel(private val circuitHistoryRepository: CircuitHistoryRepository) : ViewModel()
{
    private val _histories: MutableLiveData<MutableList<CircuitHistory>> = MutableLiveData()
    val histories get() = _histories as LiveData<MutableList<CircuitHistory>>

    fun fetchData()
    {
        viewModelScope.launch {
            _histories.value = circuitHistoryRepository.getAll()
        }
    }
}