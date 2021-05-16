package com.example.chronos.data.dao

import android.content.Context
import com.example.chronos.R
import com.example.chronos.data.entities.Circuit
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CircuitDao(private val context: Context)
{
    private val fileKey: String = context.getString(R.string.PREFERENCE_FILE_KEY)

    suspend fun insertAll(vararg models: Circuit)
    {
        val sharedPref = context.getSharedPreferences(fileKey, Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            for (circuit in models)
            {
                putString(circuit.title, Json.encodeToString(circuit))
            }
            apply()
        }
    }

    suspend fun deleteAll(vararg models: Circuit)
    {
        val sharedPref = context.getSharedPreferences(fileKey, Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            for (circuit in models)
            {
                remove(circuit.title)
            }
            apply()
        }
    }

    suspend fun getAll(): List<Circuit>
    {
        val all: Map<String, String> = context.getSharedPreferences(fileKey, Context.MODE_PRIVATE).all as Map<String, String>
        val circuits = all.filter { (key, _) -> key != CircuitHistoryDao.key }
        return circuits.map {
            Json.decodeFromString(it.value)
        }
    }
}