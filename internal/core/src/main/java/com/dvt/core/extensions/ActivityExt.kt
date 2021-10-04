package com.dvt.core.extensions

import android.app.Activity
import android.content.Intent
import com.google.android.material.dialog.MaterialAlertDialogBuilder

inline fun <reified T> Activity.navigate() {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
}

fun Activity.showErrorDialog(title: String, message: String, shouldExit: Boolean = true, onclick: () -> Unit) {
    MaterialAlertDialogBuilder(this).apply {
        setCancelable(false)
        setTitle(title)
        setMessage(message)
        setPositiveButton("Retry") { dialog, _ ->
            dialog.dismiss()
            onclick.invoke()
        }
        setNegativeButton("cancel") { dialog, _ ->
            dialog.dismiss()

            if (shouldExit) finish()
        }
        show()
    }

}