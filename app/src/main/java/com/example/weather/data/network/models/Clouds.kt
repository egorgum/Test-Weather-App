package com.example.weather.data.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Clouds(
    @Json(name = "all")
    val all: Double
)