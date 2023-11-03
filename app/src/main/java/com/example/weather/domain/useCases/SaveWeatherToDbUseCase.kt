package com.example.weather.domain.useCases

import com.example.weather.domain.entities.OneDayWeather
import com.example.weather.domain.repositories.DataBaseRepository
import javax.inject.Inject

class SaveWeatherToDbUseCase @Inject constructor(private val repository: DataBaseRepository) {
    suspend fun saveWeatherToDb(listOfWeather: List<OneDayWeather>) {
        repository.setNewData(listOfWeather)
    }
}