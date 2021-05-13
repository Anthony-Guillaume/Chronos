package com.example.chronos.data.models

import kotlinx.serialization.Serializable

enum class State { Idle, Warmup, Workout, ExerciseResting, SetResting, Done }

@Serializable
data class Circuit(
    var title: String,
    var warmup: Long,
    var restBetweenSet: Long,
    val exercise: Exercise,
    var numberOfSet: Int,
)
