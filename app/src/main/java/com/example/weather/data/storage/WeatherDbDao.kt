package com.example.weather.data.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

//Запросы к БД
@Dao
interface WeatherDbDao {

    @Query("SELECT * FROM weather")
    suspend fun getAllWeatherInfo(): List<WeatherDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNewWeather(query: List<WeatherDbModel>)

    @Query("SELECT * FROM weather WHERE date = :date")
    suspend fun getOneDayWeather(date: String): WeatherDbModel

    @Query("DELETE  FROM weather")
    suspend fun deleteAllWeatheDbItems()
}