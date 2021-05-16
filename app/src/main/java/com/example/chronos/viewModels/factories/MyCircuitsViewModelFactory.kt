package com.example.chronos.viewModels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chronos.data.repositories.CircuitRepository
import com.example.chronos.data.repositories.SharedCircuitRepository
import com.example.chronos.viewModels.MyCircuitsViewModel

class MyCircuitsViewModelFactory(
    private val circuitRepository: CircuitRepository,
    private val sharedCircuitRepository: SharedCircuitRepository)
    : ViewModelProvider.NewInstanceFactory()
{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        return MyCircuitsViewModel(circuitRepository, sharedCircuitRepository) as T
    }
}