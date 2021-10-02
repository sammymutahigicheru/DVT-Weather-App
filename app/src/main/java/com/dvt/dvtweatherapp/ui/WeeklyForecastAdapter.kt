package com.dvt.dvtweatherapp.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dvt.dvtweatherapp.R
import com.dvt.dvtweatherapp.databinding.WeeklyForecastRowItemBinding
import com.dvt.dvtweatherapp.utils.helpers.convertToDay
import com.dvt.network.models.Daily

class WeeklyForecastAdapter :
    ListAdapter<Daily, WeeklyForecastAdapter.WeeklyForeCastViewHolder>(diffUtil) {

    inner class WeeklyForeCastViewHolder(private val binding: WeeklyForecastRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Daily) {
            binding.apply {
                weekDayTextview.text = convertToDay(item.dt.toLong())
                temperatureValueTextview.text = "${item.temp.day} ℃"
            }
            val weatherId = item.weather[0].id.toString()
            val icon = item.weather[0].icon
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
                id.equals("800", true) -> {
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
    ): WeeklyForecastAdapter.WeeklyForeCastViewHolder {
        return WeeklyForeCastViewHolder(
            WeeklyForecastRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: WeeklyForecastAdapter.WeeklyForeCastViewHolder,
        position: Int
    ) {
        val item = getItem(position)

        holder.bind(item)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Daily>() {
            override fun areItemsTheSame(oldItem: Daily, newItem: Daily): Boolean {
                return oldItem.dt == newItem.dt
            }

            override fun areContentsTheSame(oldItem: Daily, newItem: Daily): Boolean {
                return oldItem == newItem
            }


        }
    }

}
