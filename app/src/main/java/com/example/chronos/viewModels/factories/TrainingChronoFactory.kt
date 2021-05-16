package com.example.chronos.viewModels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chronos.data.repositories.CircuitHistoryRepository
import com.example.chronos.data.repositories.CircuitRepository
import com.example.chronos.viewModels.TrainingChronoViewModel

class TrainingChronoFactory(
    private val circuitRepository: CircuitRepository,
    private val circuitHistoryRepository: CircuitHistoryRepository)
    : ViewModelProvider.NewInstanceFactory()
{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        return TrainingChronoViewModel(circuitRepository, circuitHistoryRepository) as T
    }
}
