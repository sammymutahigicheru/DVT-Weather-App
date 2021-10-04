package com.dvt.core.extensions

import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(message: String) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)

    snackbar.apply {
        this.setBackgroundTint(
            ContextCompat.getColor(view.context, android.R.color.white)
        )
        this.setTextColor(ContextCompat.getColor(this.context, android.R.color.black))
        show()
    }
}