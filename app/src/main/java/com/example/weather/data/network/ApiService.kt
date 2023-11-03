package com.example.weather.data.network

import com.example.weather.data.network.models.OneDayWeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/data/2.5/forecast")
    suspend fun getFirstDayWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("type") type: String = "hour",
        @Query("appid") appid: String = API_KEY,
        @Query("units") units: String = UNITS
    ): OneDayWeatherDto

    companion object{
        private const val API_KEY = "0a5658aa3f69e671e272e810a3eff323"
        private const val UNITS = "metric"
    }
}