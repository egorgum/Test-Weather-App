package com.example.weather.data.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherDbModel(
    @PrimaryKey
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "cloudy")
    val cloudy: Int,
    @ColumnInfo(name = "humidity")
    val humidity: Int,
    @ColumnInfo(name = "pressure")
    val pressure: Int,
    @ColumnInfo(name = "minTemp")
    val minTemp: Int,
    @ColumnInfo(name = "maxTemp")
    val maxTemp: Int,
    @ColumnInfo(name = "windSpeed")
    val windSpeed: Int
)
