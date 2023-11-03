package com.example.weather.di

import android.content.Context
import com.example.weather.data.mappers.DataBaseMapper
import com.example.weather.data.repositoriesImpl.WeatherRepositoryImpl
import com.example.weather.data.mappers.WeatherMapper
import com.example.weather.data.network.ApiFactory
import com.example.weather.data.network.ApiService
import com.example.weather.data.repositoriesImpl.DataBaseRepositoryImpl
import com.example.weather.data.storage.AppDb
import com.example.weather.data.storage.WeatherDbDao
import com.example.weather.domain.repositories.DataBaseRepository
import com.example.weather.domain.repositories.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    //Network
    @Provides
    @Singleton
    fun provideWeatherRepository(
        mapper: WeatherMapper,
        apiService: ApiService
    ): WeatherRepository {
        return WeatherRepositoryImpl(apiService, mapper)
    }

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return ApiFactory.apiService
    }

    //DataBase
    @Provides
    @Singleton
    fun provideDataBaseRepository(
        mapper: DataBaseMapper,
        dao: WeatherDbDao
    ): DataBaseRepository {
        return DataBaseRepositoryImpl(dao, mapper)
    }

    @Provides
    @Singleton
    fun provideWeatherDbDao(@ApplicationContext context: Context): WeatherDbDao {
        return AppDb.getInstance(context).weatherDao()
    }
}