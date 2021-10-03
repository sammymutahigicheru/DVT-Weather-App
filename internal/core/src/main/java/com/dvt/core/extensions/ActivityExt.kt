package com.dvt.core.extensions

import android.app.Activity
import android.content.Intent

inline fun <reified T> Activity.navigate() {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
}