package com.example.chronos.viewModels

import android.content.Context
import com.example.chronos.data.Database
import com.example.chronos.data.repositories.CircuitHistoryRepository
import com.example.chronos.data.repositories.CircuitRepository

object ViewModelProvider
{
    fun provideCircuitViewModelFactory(context: Context): CircuitViewModelFactory
    {
        val circuitRepository = CircuitRepository.getInstance(Database.getInstance(context).circuitDao)
        val circuitHistoryRepository = CircuitHistoryRepository.getInstance(Database.getInstance(context).circuitHistoryDao)
        return CircuitViewModelFactory(circuitRepository, circuitHistoryRepository)
    }

    fun provideTrainingSettingViewModelFactory(context: Context): TrainingSettingViewModelFactory
    {
        val repository = CircuitRepository.getInstance(Database.getInstance(context).circuitDao)
        return TrainingSettingViewModelFactory(repository)
    }
}