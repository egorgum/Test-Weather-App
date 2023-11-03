package com.example.weather.data.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//Инициализация базы данных
@Database(entities = [WeatherDbModel::class], version = 1, exportSchema = false)
abstract class AppDb : RoomDatabase() {
    abstract fun weatherDao(): WeatherDbDao

    companion object {
        private const val DB_NAME = "dbWeather"
        private var db: AppDb? = null
        private val LOCK = Any()

        fun getInstance(context: Context): AppDb {
            synchronized(LOCK) {
                db?.let { return it }
                val instance =
                    Room.databaseBuilder(
                        context,
                        AppDb::class.java,
                        DB_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                db = instance
                return instance
            }
        }
    }
}