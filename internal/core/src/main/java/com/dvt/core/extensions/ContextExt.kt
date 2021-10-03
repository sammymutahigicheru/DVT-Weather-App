package com.dvt.core.extensions

import android.app.Activity
import android.content.Context
import android.widget.Toast

fun Context.toast(message:String){
    Toast.makeText(this,message,Toast.LENGTH_LONG).show()
}