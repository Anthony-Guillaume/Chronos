package com.example.chronos.viewModels

import android.content.Context
import com.example.chronos.data.Database
import com.example.chronos.data.repositories.CircuitRepository

object ViewModelProvider
{
    fun provideCircuitViewModelFactory(context: Context): CircuitViewModelFactory
    {
        val repository = CircuitRepository.getInstance(Database.getInstance(context).circuitDao)
        return CircuitViewModelFactory(repository)
    }

    fun provideTrainingSettingViewModelFactory(context: Context): TrainingSettingViewModelFactory
    {
        val repository = CircuitRepository.getInstance(Database.getInstance(context).circuitDao)
        return TrainingSettingViewModelFactory(repository)
    }
}