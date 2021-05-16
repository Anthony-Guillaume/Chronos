package com.example.chronos.data.repositories

import android.util.Log
import com.example.chronos.data.dao.CircuitDao
import com.example.chronos.data.entities.Circuit

class CircuitRepository private constructor(private val dao: CircuitDao)
{
    private var _cache: MutableList<Circuit>? = null

    private suspend fun getData() : MutableList<Circuit>
    {
        Log.i("TEST", "getData $_cache")
        return _cache ?: dao.getAll().toMutableList().also { _cache = it }
    }

    suspend fun add(model: Circuit)
    {
        val data = getData()
        for (m in data)
        {
            if (m.title == model.title)
            {
                delete(m)
                break
            }
        }
        data.add(model)
        dao.insertAll(model)
    }

    suspend fun delete(model: Circuit)
    {
        getData().remove(model)
        dao.deleteAll(model)
    }

    suspend fun getAll() : MutableList<Circuit>
    {
        return getData()
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