package com.example.chronos.data

import android.content.Context
import com.example.chronos.data.dao.CircuitDao
import com.example.chronos.data.dao.CircuitHistoryDao

class Database private constructor(context: Context)
{
    var circuitDao = CircuitDao(context)
        private set

    var circuitHistoryDao = CircuitHistoryDao(context)
        private set

    companion object
    {
        @Volatile private var instance: Database? = null

        fun getInstance(context: Context): Database
        {
            return instance ?: synchronized(this) {
                instance ?: Database(context).also { instance = it }
            }
        }
    }
}