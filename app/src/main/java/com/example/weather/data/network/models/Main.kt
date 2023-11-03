package com.example.weather.data.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Main(
    @Json(name = "feels_like")
    val feels_like: Double,
    @Json(name = "humidity")
    val humidity: Double,
    @Json(name = "pressure")
    val pressure: Double,
    @Json(name = "temp")
    val temp: Double,
    @Json(name = "temp_max")
    val temp_max: Double,
    @Json(name = "temp_min")
    val temp_min: Double
)