package com.dvt.core.helpers

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.dvt.core.R
import com.dvt.core.helpers.Constants.MAP_CAMERA_ZOOM
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng



fun GoogleMap.moveCameraWithAnim(latLng: LatLng) {
    animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, MAP_CAMERA_ZOOM))
}
