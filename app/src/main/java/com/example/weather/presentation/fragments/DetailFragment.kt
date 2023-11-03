package com.example.weather.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.weather.R
import com.example.weather.databinding.FragmentDetailBinding
import com.example.weather.domain.entities.OneDayWeather
import com.example.weather.presentation.Params
import com.example.weather.presentation.viewModels.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()

    private var _binding: FragmentDetailBinding? = null

    private val binding: FragmentDetailBinding
        get() = _binding ?: throw RuntimeException("FragmentDetailBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkInfoIsLoading()
        checkInfo()
        val date: String =
            arguments?.getString(Params.PARAM_FOR_DETAIL)!!//Получение аргумента из главного фрагмента
        viewModel.getOneWeatherDay(date)//Получение данных из БД по времени измерения
    }

    //Установка данных во View элементы
    private fun setDataInView(data: OneDayWeather) {
        with(binding) {
            date.text = data.date
            cloudy.text = requireContext().getString(R.string.cloudy, data.cloudy)
            humidity.text = requireContext().getString(R.string.humidity, data.humidity)
            pressure.text = requireContext().getString(R.string.pressure, data.pressure)
            minTemp.text = requireContext().getString(R.string.min_temperature, data.minTemp)
            maxTemp.text = requireContext().getString(R.string.max_temperature, data.maxTemp)
            windSpeed.text = requireContext().getString(R.string.wind_speed, data.windSpeed)
        }
    }

    //Установка данных во View после получения данных
    private fun checkInfo() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.weatherInfo.collect {
                    if (it != null) {
                        setDataInView(it)
                    }
                }
            }
        }
    }

    //Проверка на загрузку
    private fun checkInfoIsLoading() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.infoIsLoading.collect {
                    if (it) {
                        binding.cvDetail.visibility = View.GONE
                        binding.pbDetail.visibility = View.VISIBLE
                    } else {
                        binding.pbDetail.visibility = View.GONE
                        binding.cvDetail.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    //Очистка binding
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}