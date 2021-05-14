package com.example.chronos.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Date(
    var day: Int,
    var month: Int,
    var year: Int
)
