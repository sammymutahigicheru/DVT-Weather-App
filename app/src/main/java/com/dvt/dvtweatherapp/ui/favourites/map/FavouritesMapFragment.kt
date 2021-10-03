package com.dvt.dvtweatherapp.ui.favourites.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dvt.data.database.entities.Favourites
import com.dvt.dvtweatherapp.databinding.FragmentFavouritesMapBinding

class FavouritesMapFragment(favourites: List<Favourites>) : Fragment() {

    private val binding: FragmentFavouritesMapBinding by lazy {
        FragmentFavouritesMapBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

}