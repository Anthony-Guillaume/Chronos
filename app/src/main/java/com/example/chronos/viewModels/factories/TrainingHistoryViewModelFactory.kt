package com.example.chronos.viewModels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chronos.data.repositories.CircuitHistoryRepository
import com.example.chronos.viewModels.TrainingHistoryViewModel

class TrainingHistoryViewModelFactory(private val repository: CircuitHistoryRepository)
    : ViewModelProvider.NewInstanceFactory()
{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        return TrainingHistoryViewModel(repository) as T
    }
}