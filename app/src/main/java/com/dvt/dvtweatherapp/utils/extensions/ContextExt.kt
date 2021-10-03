package com.dvt.dvtweatherapp.utils.extensions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat

fun Context.isLocationPermissionEnabled(): Boolean {
    return ActivityCompat.checkSelfPermission(
        applicationContext,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

fun Context.isLocationEnabled(): Boolean {
    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return LocationManagerCompat.isLocationEnabled(locationManager)

}