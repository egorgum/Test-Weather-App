package com.example.weather.domain.useCases

import com.example.weather.domain.entities.OneDayWeather
import com.example.weather.domain.repositories.DataBaseRepository
import javax.inject.Inject

class GetAllDbWeatherDaysUseCase @Inject constructor(private val repo: DataBaseRepository) {
    suspend fun getAllDbWeatherDays(): List<OneDayWeather> {
        return repo.getAllData()
    }
}