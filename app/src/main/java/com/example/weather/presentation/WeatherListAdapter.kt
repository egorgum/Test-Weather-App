package com.example.weather.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.databinding.OneDayTemperatureBinding
import com.example.weather.domain.entities.OneDayWeather

class WeatherListAdapter(private val onClick: (String) -> Unit) :
    ListAdapter<OneDayWeather, OneDayViewHolder>(DiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OneDayViewHolder {
        val binding =
            OneDayTemperatureBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return OneDayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OneDayViewHolder, position: Int) {
        val item = getItem(position)
        with(holder) {
            binding.date.text = item.date
            binding.cloudy.text = itemView.context.getString(R.string.cloudy, item.cloudy)
            binding.humidity.text = itemView.context.getString(R.string.humidity, item.humidity)
            binding.pressure.text = itemView.context.getString(R.string.pressure, item.pressure)
            binding.minTemp.text =
                itemView.context.getString(R.string.min_temperature, item.minTemp)
            binding.maxTemp.text =
                itemView.context.getString(R.string.max_temperature, item.maxTemp)
            binding.windSpeed.text = itemView.context.getString(R.string.wind_speed, item.windSpeed)
            binding.cvWeather.setOnClickListener { onClick(item.date) }
        }
    }
}

class OneDayViewHolder(
    val binding: OneDayTemperatureBinding
) : RecyclerView.ViewHolder(binding.root)

class DiffUtilCallback : DiffUtil.ItemCallback<OneDayWeather>() {
    override fun areItemsTheSame(oldItem: OneDayWeather, newItem: OneDayWeather): Boolean {
        return oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: OneDayWeather, newItem: OneDayWeather): Boolean {
        return oldItem == newItem
    }
}