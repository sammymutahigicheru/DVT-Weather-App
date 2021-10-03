package com.dvt.core.extensions

import android.content.Context
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

fun FloatingActionButton.setImageTint(color:Int) {
    imageTintList = ContextCompat.getColorStateList(context,color)
}