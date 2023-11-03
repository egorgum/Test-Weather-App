package com.example.weather.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.domain.useCases.GetFiveDaysWeatherUseCase
import com.example.weather.domain.entities.OneDayWeather
import com.example.weather.domain.useCases.GetAllDbWeatherDaysUseCase
import com.example.weather.domain.useCases.SaveWeatherToDbUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getFiveDaysWeatherUseCase: GetFiveDaysWeatherUseCase,
    private val getAllDbWeatherDaysUseCase: GetAllDbWeatherDaysUseCase,
    private val saveWeatherToDbUseCase: SaveWeatherToDbUseCase
) : ViewModel() {

    private var _weatherMeasurements = MutableStateFlow<List<OneDayWeather>>(emptyList())
    val weatherMeasurements
        get() = _weatherMeasurements.asStateFlow()

    private var _infoIsLoading = MutableStateFlow<Boolean>(true)
    val infoIsLoading
        get() = _infoIsLoading.asStateFlow()


    fun getFiveDaysWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                _weatherMeasurements.value = getFiveDaysWeatherUseCase.getFiveDaysWeather(lat, lon)
                saveWeatherToDbUseCase.saveWeatherToDb(weatherMeasurements.value)
            } catch (e: Exception) {
                _weatherMeasurements.value = getAllDbWeatherDaysUseCase.getAllDbWeatherDays()
            } finally {
                _infoIsLoading.value = false
            }

        }
    }
}