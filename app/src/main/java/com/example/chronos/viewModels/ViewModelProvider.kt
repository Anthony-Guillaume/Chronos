package com.example.chronos.viewModels

import android.content.Context
import com.example.chronos.data.Database
import com.example.chronos.data.repositories.CircuitHistoryRepository
import com.example.chronos.data.repositories.CircuitRepository
import com.example.chronos.data.repositories.SharedCircuitRepository
import com.example.chronos.viewModels.factories.*

object ViewModelProvider
{
    fun provideTrainingChronoViewModelFactory(context: Context): TrainingChronoFactory
    {
        val circuitRepository = CircuitRepository.getInstance(Database.getInstance(context).circuitDao)
        val circuitHistoryRepository = CircuitHistoryRepository.getInstance(Database.getInstance(context).circuitHistoryDao)
        return TrainingChronoFactory(circuitRepository, circuitHistoryRepository)
    }

    fun provideCreateCircuitViewModelFactory(context: Context): CreateCircuitViewModelFactory
    {
        val circuitRepository = CircuitRepository.getInstance(Database.getInstance(context).circuitDao)
        return CreateCircuitViewModelFactory(circuitRepository)
    }

    fun provideEditMyCircuitViewModelFactory(context: Context): EditMyCircuitViewModelFactory
    {
        val circuitRepository = CircuitRepository.getInstance(Database.getInstance(context).circuitDao)
        val sharedCircuitRepository = SharedCircuitRepository.getInstance()
        return EditMyCircuitViewModelFactory(circuitRepository, sharedCircuitRepository)
    }

    fun provideTrainingHistoryViewModelFactory(context: Context): TrainingHistoryViewModelFactory
    {
        val repository = CircuitHistoryRepository.getInstance(Database.getInstance(context).circuitHistoryDao)
        return TrainingHistoryViewModelFactory(repository)
    }

    fun provideMyCircuitsViewModelFactory(context: Context): MyCircuitsViewModelFactory
    {
        val circuitRepository = CircuitRepository.getInstance(Database.getInstance(context).circuitDao)
        val sharedCircuitRepository = SharedCircuitRepository.getInstance()
        return MyCircuitsViewModelFactory(circuitRepository, sharedCircuitRepository)
    }
}