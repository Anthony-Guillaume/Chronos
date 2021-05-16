package com.example.chronos.data.entities

import kotlinx.serialization.Serializable

enum class State { Idle, Warmup, Workout, ExerciseResting, SetResting, Done }

@Serializable
data class Circuit(
    var title: String,
    var warmup: Long,
    var restBetweenSets: Long,
    val exercise: Exercise,
    var numberOfSets: Int,
)
