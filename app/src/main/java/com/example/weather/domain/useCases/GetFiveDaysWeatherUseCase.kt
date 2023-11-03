package com.example.weather.domain.useCases

import com.example.weather.domain.entities.OneDayWeather
import com.example.weather.domain.repositories.WeatherRepository
import javax.inject.Inject

class GetFiveDaysWeatherUseCase @Inject constructor(private val repository: WeatherRepository) {
    suspend fun getFiveDaysWeather(lat: Double, lon: Double): List<OneDayWeather> {
        return repository.getFiveDaysWeather(lat, lon)
    }
}