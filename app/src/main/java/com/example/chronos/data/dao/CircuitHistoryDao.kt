package com.example.chronos.data.dao

import android.content.Context
import androidx.compose.runtime.key
import com.example.chronos.R
import com.example.chronos.data.models.CircuitHistory
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CircuitHistoryDao(private val context: Context)
{
    companion object {
        const val key: String = "CircuitHistoryKey165847325471263"
    }
    private val fileKey: String = context.getString(R.string.PREFERENCE_FILE_KEY)

    fun insertAll(models: List<CircuitHistory>)
    {
        val sharedPref = context.getSharedPreferences(fileKey, Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putString(key, Json.encodeToString(models))
            apply()
        }
    }

    fun deleteAll(vararg models: CircuitHistory)
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

    fun getAll(): List<CircuitHistory>
    {
        val model = context.getSharedPreferences(fileKey, Context.MODE_PRIVATE).getString(key, null)
        return if (model!= null) Json.decodeFromString(model) else listOf()
    }
}