package com.example.weather.domain.useCases

import com.example.weather.domain.entities.OneDayWeather
import com.example.weather.domain.repositories.DataBaseRepository
import javax.inject.Inject

class GetOneWeatherDayDbUseCase @Inject constructor(private val repo: DataBaseRepository) {
    suspend fun getOneWeatherDay(date: String): OneDayWeather {
        return repo.getOneWeatherDay(date)
    }
}