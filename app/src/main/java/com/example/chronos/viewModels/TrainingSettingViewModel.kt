package com.example.chronos.viewModels

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chronos.data.models.Circuit
import com.example.chronos.data.models.Exercise
import com.example.chronos.data.repositories.CircuitRepository
import kotlin.math.max

class TrainingSettingViewModel(private val repository: CircuitRepository) : ViewModel()
{
    private var circuitToSave: Circuit? = null
    private val _circuit: MutableLiveData<Circuit> = MutableLiveData(
        Circuit("", 10000, 10000,
            Exercise(10000, 10000, 5),
        5))
    val circuit get() = _circuit as LiveData<Circuit>

    private fun notifyDataChanged()
    {
        _circuit.postValue(_circuit.value) // to force update even if only one property has changed
    }

    fun save()
    {
        _circuit.value?.let {
            circuitToSave = it.copy()
            repository.addCircuit(circuitToSave!!)
        }
    }

    fun deleteLastSave()
    {
        circuitToSave?.let { repository.deleteCircuit(it) }
    }

    fun updateTitle(title: String)
    {
        _circuit.value?.let { it.title = title }
    }

    fun decrementWarmupDuration()
    {
        _circuit.value?.also {
            it.warmup = max(0, it.warmup - 1000)
        }
        notifyDataChanged()
    }

    fun incrementWarmupDuration()
    {
        _circuit.value?.also {
            it.warmup += 1000
        }
        notifyDataChanged()
    }

    fun decrementWorkoutDuration()
    {
        _circuit.value?.also {
            it.exercise.workout = max(0, it.exercise.workout - 1000)
        }
        notifyDataChanged()
    }

    fun incrementWorkoutDuration()
    {
        _circuit.value?.also {
            it.exercise.workout += 1000
        }
        notifyDataChanged()
    }

    fun decrementExerciseRestDuration()
    {
        _circuit.value?.also {
            it.exercise.rest = max(0, it.exercise.rest - 1000)
        }
        notifyDataChanged()
    }

    fun incrementExerciseRestDuration()
    {
        _circuit.value?.also {
            it.exercise.rest += 1000
        }
        notifyDataChanged()
    }

    fun decrementSetRestDuration()
    {
        _circuit.value?.also {
            it.restBetweenSet = max(0, it.restBetweenSet - 1000)
        }
        notifyDataChanged()
    }

    fun incrementSetRestDuration()
    {
        _circuit.value?.also {
            it.restBetweenSet += 1000
        }
        notifyDataChanged()
    }

    fun decrementRound()
    {
        _circuit.value?.also {
            it.exercise.numberOfRound = max(0, it.exercise.numberOfRound - 1)
        }
        notifyDataChanged()
    }

    fun incrementRound()
    {
        _circuit.value?.also {
            it.exercise.numberOfRound += 1
        }
        notifyDataChanged()
    }

    fun decrementSet()
    {
        _circuit.value?.also {
            it.numberOfSet = max(0, it.numberOfSet - 1)
        }
        notifyDataChanged()
    }

    fun incrementSet()
    {
        _circuit.value?.also {
            it.numberOfSet += 1
        }
        notifyDataChanged()
    }
}