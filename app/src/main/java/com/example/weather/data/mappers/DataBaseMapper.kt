package com.example.weather.data.mappers

import com.example.weather.data.storage.WeatherDbModel
import com.example.weather.domain.entities.OneDayWeather
import javax.inject.Inject

class DataBaseMapper @Inject constructor() {

    //Преобразование списка погоды в Модель БД
    fun mapWeatherListToModels(weatherList: List<OneDayWeather>): List<WeatherDbModel> {
        val result = mutableListOf<WeatherDbModel>()
        weatherList.forEach{
            with(it) {
                result.add(
                    WeatherDbModel(
                        date = date,
                        cloudy = cloudy,
                        humidity = humidity,
                        pressure = pressure,
                        minTemp = minTemp,
                        maxTemp = maxTemp,
                        windSpeed = windSpeed
                    )
                )
            }
        }
        return result
    }

    //Преобразование БД моделей в список сущностей
    fun mapDbModelsToEntities(models: List<WeatherDbModel>): List<OneDayWeather> {
        return models.map {
            with(it) {
                OneDayWeather(
                    date = date,
                    cloudy = cloudy,
                    humidity = humidity,
                    pressure = pressure,
                    minTemp = minTemp,
                    maxTemp = maxTemp,
                    windSpeed = windSpeed
                )
            }
        }
    }

    //Модель в сущность
    fun mapDbModelToEntity(model: WeatherDbModel): OneDayWeather {
        with(model) {
            return OneDayWeather(
                date = date,
                cloudy = cloudy,
                humidity = humidity,
                pressure = pressure,
                minTemp = minTemp,
                maxTemp = maxTemp,
                windSpeed = windSpeed
            )
        }
    }
}