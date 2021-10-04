package com.dvt.core.extensions

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

fun Context.toast(message:String){
    Toast.makeText(this,message,Toast.LENGTH_LONG).show()
}

fun Context.bitmapFromVector(vectorResId: Int): BitmapDescriptor {
    val bitmapDrawable = ContextCompat.getDrawable(this, vectorResId) as BitmapDrawable


    val color = Color.WHITE

    val smallMarker = Bitmap.createScaledBitmap(bitmapDrawable.bitmap, 200, 200, false)

    val paint = Paint()
    val canvas = Canvas(smallMarker)

    paint.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(smallMarker, 0F, 0F, paint)
    return BitmapDescriptorFactory.fromBitmap(smallMarker)

}