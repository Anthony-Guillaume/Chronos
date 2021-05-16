package com.example.chronos.viewModels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chronos.data.repositories.CircuitRepository
import com.example.chronos.data.repositories.SharedCircuitRepository
import com.example.chronos.viewModels.EditMyCircuitViewModel

class EditMyCircuitViewModelFactory(
    private val repository: CircuitRepository,
    private val sharedCircuitRepository: SharedCircuitRepository)
    : ViewModelProvider.NewInstanceFactory()
{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        return EditMyCircuitViewModel(repository, sharedCircuitRepository) as T
    }
}