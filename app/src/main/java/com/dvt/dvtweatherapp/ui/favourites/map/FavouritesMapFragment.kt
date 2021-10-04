package com.dvt.dvtweatherapp.ui.favourites.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dvt.core.extensions.bitmapFromVector
import com.dvt.core.helpers.moveCameraWithAnim
import com.dvt.data.database.entities.Favourites
import com.dvt.dvtweatherapp.R
import com.dvt.dvtweatherapp.databinding.FragmentFavouritesMapBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions


class FavouritesMapFragment(
    private val favourites: List<Favourites>
) : Fragment(), OnMapReadyCallback {

    private val binding: FragmentFavouritesMapBinding by lazy {
        FragmentFavouritesMapBinding.inflate(layoutInflater)
    }

    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapSupportFragment =
            childFragmentManager.findFragmentById(binding.map.id) as SupportMapFragment
        mapSupportFragment.getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.apply {
            setMinZoomPreference(2F)
            isBuildingsEnabled = false
            uiSettings.isMapToolbarEnabled = false
            uiSettings.isMyLocationButtonEnabled = false
            uiSettings.isRotateGesturesEnabled = true
            isMyLocationEnabled = true
            setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    binding.root.context,
                    R.raw.maps_retro_styling
                )
            )
        }
        setupMap()
    }

    private fun setupMap() {
        if (favourites.isNotEmpty()) {

            for (favouriteLocation in favourites) {
                val itemLatLng = LatLng(favouriteLocation.latitude, favouriteLocation.longitude)
                val description = favouriteLocation.description
                val markerOption = MarkerOptions().apply {
                    position(itemLatLng)
                    title(favouriteLocation.locationName)
                    when {
                        description.contains("rain", true) -> {
                            icon(binding.root.context.bitmapFromVector(R.drawable.rain3x))
                        }
                        description.contains("sun", true) -> {
                            icon(binding.root.context.bitmapFromVector(R.drawable.clear3x))
                        }
                        description.contains("cloud", true) -> {
                            icon(binding.root.context.bitmapFromVector(R.drawable.partlysunny3x))
                        }
                    }
                }

                map.moveCameraWithAnim(itemLatLng)

                map.apply {
                    addMarker(markerOption)
                }


            }
        }
    }


}