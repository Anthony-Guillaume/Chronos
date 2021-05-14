package com.example.chronos.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chronos.data.dao.CircuitHistoryDao
import com.example.chronos.data.models.CircuitHistory

class CircuitHistoryRepository private constructor(private val dao: CircuitHistoryDao) {
    private val models: LiveData<MutableList<CircuitHistory>> by lazy {
        initModels()
    }
    private val maxSize: Int = 30

    private fun initModels(): LiveData<MutableList<CircuitHistory>> {
        val daoModels = dao.getAll().toMutableList()
        if (daoModels.isEmpty()) {
            return MutableLiveData(mutableListOf())
        }
        return MutableLiveData(daoModels)
    }

    fun add(model: CircuitHistory) {
        val histories = models.value ?: return
        if (histories.size >= maxSize)
        {
            histories.removeFirst()
        }
        histories.add(model)
        dao.insertAll(histories)
    }

    fun delete(model: CircuitHistory)
    {
        models.value?.remove(model)
        dao.deleteAll(model)
    }

    fun getAll(): LiveData<MutableList<CircuitHistory>>
    {
        return models
    }

    companion object {
        @Volatile
        private var instance: CircuitHistoryRepository? = null

        fun getInstance(dao: CircuitHistoryDao): CircuitHistoryRepository {
            return instance ?: synchronized(this) {
                instance ?: CircuitHistoryRepository(dao).also { instance = it }
            }
        }
    }
}
