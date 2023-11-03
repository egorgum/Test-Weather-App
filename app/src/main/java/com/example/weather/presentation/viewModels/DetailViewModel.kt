package com.example.weather.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.domain.entities.OneDayWeather
import com.example.weather.domain.useCases.GetOneWeatherDayDbUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getOneWeatherDayDbUseCase: GetOneWeatherDayDbUseCase
): ViewModel() {

    private var _weatherInfo = MutableStateFlow<OneDayWeather?>(null)
    val weatherInfo
        get() = _weatherInfo.asStateFlow()

    private var _infoIsLoading = MutableStateFlow<Boolean>(true)
    val infoIsLoading
        get() = _infoIsLoading.asStateFlow()

    fun getOneWeatherDay(date: String) {
        viewModelScope.launch {
            _weatherInfo.value = getOneWeatherDayDbUseCase.getOneWeatherDay(date)
            _infoIsLoading.value = false
        }
    }
}