package com.example.chronos.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class CircuitHistory(
    var title: String,
    var date: Date,
    var numberOfRounds: Int,
    var numberOfSets: Int,
    var numberOfSetsDone: Int
)