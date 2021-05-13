package com.example.chronos.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Exercise(
    var workout: Long,
    var rest: Long,
    var numberOfRound: Int
)
