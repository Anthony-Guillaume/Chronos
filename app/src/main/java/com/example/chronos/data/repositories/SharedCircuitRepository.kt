package com.example.chronos.data.repositories

import com.example.chronos.data.entities.Circuit

class SharedCircuitRepository private constructor()
{
    var circuitToEdit: Circuit? = null

    companion object
    {
        @Volatile private var instance: SharedCircuitRepository? = null

        fun getInstance() : SharedCircuitRepository
        {
            return instance ?: synchronized(this) {
                instance ?: SharedCircuitRepository().also { instance = it }
            }
        }
    }
}