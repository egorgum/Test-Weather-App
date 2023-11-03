package com.example.weather.domain.repositories

import com.example.weather.domain.entities.OneDayWeather

interface DataBaseRepository {

    suspend fun setNewData(data: List<OneDayWeather>)

    suspend fun getAllData(): List<OneDayWeather>

    suspend fun getOneWeatherDay(date: String): OneDayWeather
}