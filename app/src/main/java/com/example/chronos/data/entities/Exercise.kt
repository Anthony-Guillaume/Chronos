package com.example.chronos.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class Exercise(
    var workout: Long,
    var rest: Long,
    var numberOfRounds: Int
)
