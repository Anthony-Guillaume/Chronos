package com.example.chronos.viewModels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chronos.data.repositories.CircuitRepository
import com.example.chronos.viewModels.CreateCircuitViewModel

class CreateCircuitViewModelFactory(private val repository: CircuitRepository)
    : ViewModelProvider.NewInstanceFactory()
{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        return CreateCircuitViewModel(repository) as T
    }
}