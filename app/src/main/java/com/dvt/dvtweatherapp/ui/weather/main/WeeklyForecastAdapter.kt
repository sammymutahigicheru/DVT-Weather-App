package com.dvt.dvtweatherapp.ui.weather.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dvt.dvtweatherapp.R
import com.dvt.dvtweatherapp.data.Forecast
import com.dvt.dvtweatherapp.databinding.WeeklyForecastRowItemBinding
import timber.log.Timber

class WeeklyForecastAdapter :
    ListAdapter<Forecast, WeeklyForecastAdapter.WeeklyForeCastViewHolder>(diffUtil) {

    inner class WeeklyForeCastViewHolder(private val binding: WeeklyForecastRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Forecast) {
            binding.apply {
                weekDayTextview.text = item.day
                temperatureValueTextview.text = "${item.temperature} ℃"
            }
            val weatherId = item.id.toString()
            Timber.e("Weather Id: $weatherId")
            Timber.e("Day ${item.day}")
            setIcons(weatherId)
        }

        private fun setIcons(id: String) {
            when {
                //Thunderstom 2XX
                id.startsWith("2", true) -> {
                    updateIcon(R.drawable.rain)
                }

                //Drizzle 3XX
                id.startsWith("3", true) -> {
                    updateIcon(R.drawable.rain)
                }

                // Rain 5XX
                id.startsWith("5", true) -> {
                    updateIcon(R.drawable.rain)
                }

                //Snow 6XX
                id.startsWith("6", true) -> {
                    updateIcon(R.drawable.rain)

                }

                //Atmosphere 7XX
                id.startsWith("7", true) -> {
                    updateIcon(R.drawable.partlysunny)

                }

                //sunny/clear 8XX
                id.startsWith("8", true) -> {
                    updateIcon(R.drawable.clear)
                }
            }
        }

        private fun updateIcon(iconId: Int) {
            binding.forecastIconImageview.setImageResource(iconId)
        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeeklyForeCastViewHolder {
        return WeeklyForeCastViewHolder(
            WeeklyForecastRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: WeeklyForeCastViewHolder,
        position: Int
    ) {
        val item = getItem(position)

        holder.bind(item)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Forecast>() {
            override fun areItemsTheSame(oldItem: Forecast, newItem: Forecast): Boolean {
                return oldItem.day == newItem.day
            }

            override fun areContentsTheSame(oldItem: Forecast, newItem: Forecast): Boolean {
                return oldItem == newItem
            }


        }
    }

}
