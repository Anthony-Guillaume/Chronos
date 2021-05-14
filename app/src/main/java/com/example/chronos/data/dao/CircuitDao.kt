package com.example.chronos.data.dao

import android.content.Context
import android.os.Environment
import android.util.Log
import com.example.chronos.R
import com.example.chronos.data.models.Circuit
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import java.io.FileOutputStream

class CircuitDao(private val context: Context)
{
    private val fileKey: String = context.getString(R.string.PREFERENCE_FILE_KEY)

    fun insertAll(vararg models: Circuit)
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

    fun deleteAll(vararg models: Circuit)
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

    fun getAll(): List<Circuit>
    {
        val all: Map<String, String> = context.getSharedPreferences(fileKey, Context.MODE_PRIVATE).all as Map<String, String>
        val circuits = all.filter { (key, _) -> key != CircuitHistoryDao.key }
        return circuits.map {
            Json.decodeFromString(it.value)
        }
    }
}