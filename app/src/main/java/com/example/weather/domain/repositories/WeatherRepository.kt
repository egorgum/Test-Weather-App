package com.example.weather.domain.repositories

import com.example.weather.domain.entities.OneDayWeather

interface WeatherRepository {

    suspend fun getFiveDaysWeather(lat: Double, lon: Double): List<OneDayWeather>

}