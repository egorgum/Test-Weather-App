package com.example.weather.data.mappers

import android.util.Log
import com.example.weather.data.network.models.OneDayWeatherDto
import com.example.weather.domain.entities.OneDayWeather
import javax.inject.Inject
import kotlin.math.roundToInt

class WeatherMapper @Inject constructor() {

    //Преобразование Dto в сущность
    fun mapDtoToEntity(dto: OneDayWeatherDto): List<OneDayWeather> {
        val result = mutableListOf<OneDayWeather>()
        for (day in dto.list){
            with(day) {
                Log.d("LOL", "$this")
                val item = OneDayWeather(
                    date = dt_txt,
                    cloudy = clouds.all.roundToInt(),
                    humidity = main.humidity.roundToInt(),
                    pressure = main.pressure.roundToInt(),
                    minTemp = main.temp_min.roundToInt(),
                    maxTemp = main.temp_max.roundToInt(),
                    windSpeed = wind.speed.roundToInt()
                )
                result.add(item)
            }
        }
        return result
    }
}