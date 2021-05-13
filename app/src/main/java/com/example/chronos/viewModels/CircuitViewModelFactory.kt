package com.example.chronos.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chronos.data.repositories.CircuitRepository

class CircuitViewModelFactory(private val repository: CircuitRepository)
    : ViewModelProvider.NewInstanceFactory()
{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        return CircuitViewModel(repository) as T
    }
}
