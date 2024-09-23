package com.timrashard.foodorderapp_bootcamp.utils

import android.content.Context
import android.widget.Toast

object DialogUtils {

    fun showToast(context: Context, message: String) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast.show()
    }
}