package com.example.chronos.viewModels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chronos.data.repositories.CircuitRepository
import com.example.chronos.viewModels.TrainingSettingViewModel

class TrainingSettingViewModelFactory(private val repository: CircuitRepository)
    : ViewModelProvider.NewInstanceFactory()
{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        return TrainingSettingViewModel(repository) as T
    }
}