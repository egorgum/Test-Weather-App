package com.example.weather.domain.entities

data class OneDayWeather(
    val date: String,
    val cloudy: Int,
    val humidity: Int,
    val pressure: Int,
    val minTemp: Int,
    val maxTemp: Int,
    val windSpeed: Int
)
