package com.dvt.dvtweatherapp.ui.favourites.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dvt.data.database.entities.Favourites
import com.dvt.dvtweatherapp.ui.favourites.list.FavouriteListFragment
import com.dvt.dvtweatherapp.ui.favourites.map.FavouritesMapFragment

class FavouritesPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val favourites: List<Favourites>
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                FavouriteListFragment(favourites)
            }
            1 -> {
                FavouritesMapFragment(favourites)
            }
            else -> {
                FavouriteListFragment(favourites)
            }
        }
    }
}