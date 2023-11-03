package com.example.weather.data.repositoriesImpl

import com.example.weather.data.mappers.WeatherMapper
import com.example.weather.data.network.ApiService
import com.example.weather.domain.entities.OneDayWeather
import com.example.weather.domain.repositories.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: WeatherMapper
): WeatherRepository {

    override suspend fun getFiveDaysWeather(lat: Double, lon: Double): List<OneDayWeather> {
        val list = apiService.getFirstDayWeather(
            lat = lat,
            lon = lon,
        )
        return mapper.mapDtoToEntity(list)
    }
}