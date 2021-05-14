package com.example.chronos.data.models

import kotlinx.serialization.Serializable

@Serializable
data class CircuitHistory(
    var title: String,
    var date: Date,
    var numberOfRounds: Int,
    var numberOfSets: Int,
    var numberOfSetsDone: Int
)