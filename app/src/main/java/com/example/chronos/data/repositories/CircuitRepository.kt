package com.example.chronos.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chronos.data.dao.CircuitDao
import com.example.chronos.data.models.Circuit
import com.example.chronos.data.models.Exercise

class CircuitRepository private constructor(private val dao: CircuitDao)
{
    private val models: LiveData<MutableList<Circuit>> by lazy {
        initModels()
    }

    private fun initModels() : LiveData<MutableList<Circuit>>
    {
        val daoModels = dao.getAll().toMutableList()
        if (daoModels.isEmpty())
        {
            return MutableLiveData(mutableListOf(Circuit("Default", 10000, 10000,
                Exercise(10000, 10000, 3),
                3)))
        }
        return MutableLiveData(daoModels)
    }

    fun add(model: Circuit)
    {
        models.value?.add(model)
        dao.insertAll(model)
    }

    fun delete(model: Circuit)
    {
        models.value?.remove(model)
        dao.deleteAll(model)
    }

    fun getAll() : LiveData<MutableList<Circuit>>
    {
        return models
    }

    companion object
    {
        @Volatile private var instance: CircuitRepository? = null

        fun getInstance(dao: CircuitDao) : CircuitRepository
        {
            return instance ?: synchronized(this) {
                instance ?: CircuitRepository(dao).also { instance = it }
            }
        }
    }
}