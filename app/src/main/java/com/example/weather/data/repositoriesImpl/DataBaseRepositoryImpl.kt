package com.example.weather.data.repositoriesImpl

import com.example.weather.data.mappers.DataBaseMapper
import com.example.weather.data.storage.WeatherDbDao
import com.example.weather.domain.entities.OneDayWeather
import com.example.weather.domain.repositories.DataBaseRepository
import javax.inject.Inject

class DataBaseRepositoryImpl @Inject constructor(
    private val dao: WeatherDbDao,
    private val mapper: DataBaseMapper
) : DataBaseRepository {
    override suspend fun setNewData(data: List<OneDayWeather>) {
        dao.deleteAllWeatheDbItems()
        dao.saveNewWeather(mapper.mapWeatherListToModels(data))
    }

    override suspend fun getAllData(): List<OneDayWeather> {
        return mapper.mapDbModelsToEntities(dao.getAllWeatherInfo())
    }

    override suspend fun getOneWeatherDay(date: String): OneDayWeather {
        return mapper.mapDbModelToEntity(dao.getOneDayWeather(date))
    }
}