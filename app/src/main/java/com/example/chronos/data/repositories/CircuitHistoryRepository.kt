package com.example.chronos.data.repositories

import com.example.chronos.data.dao.CircuitHistoryDao
import com.example.chronos.data.entities.CircuitHistory

class CircuitHistoryRepository private constructor(private val dao: CircuitHistoryDao)
{
    private var _cache: MutableList<CircuitHistory>? = null
    private val maxSize: Int = 30

    private suspend fun getData() : MutableList<CircuitHistory>
    {
        return _cache ?: dao.getAll().toMutableList().also { _cache = it }
    }

    suspend fun add(model: CircuitHistory)
    {
        val data = getData()
        if (data.size >= maxSize)
        {
            delete(data.first())
        }
        data.add(model)
        dao.insertAll(data)
    }

    suspend fun delete(model: CircuitHistory)
    {
        getData().remove(model)
        dao.deleteAll(model)
    }

    suspend fun getAll() : MutableList<CircuitHistory>
    {
        return getData()
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
