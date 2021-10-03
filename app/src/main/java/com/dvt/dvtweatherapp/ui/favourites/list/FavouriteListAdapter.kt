package com.dvt.dvtweatherapp.ui.favourites.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dvt.data.database.entities.CurrentWeather
import com.dvt.data.database.entities.Favourites
import com.dvt.dvtweatherapp.databinding.FavouriteRowItemBinding

class FavouriteListAdapter :
    ListAdapter<Favourites, FavouriteListAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: FavouriteRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Favourites) {
            binding.apply {
                locationNameTextview.text =
                    if (item.locationName.isNotEmpty()) item.locationName else ""
                temperatureValue.text = "${item.currentTemperature} ℃"
                descriptionTextview.text = item.description
            }
        }

    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Favourites>() {
            override fun areItemsTheSame(
                oldItem: Favourites,
                newItem: Favourites
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Favourites,
                newItem: Favourites
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = FavouriteRowItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { holder.bind(it) }
    }
}