package ercanduman.visualizerestaurant.ui.utils

import android.util.Log
import ercanduman.visualizerestaurant.BuildConfig

/**
 * Stores view related utilities functions
 *
 * @author ERCAN DUMAN
 * @since  26.11.2020
 */

fun Any.logd(message: String) {
    if (BuildConfig.DEBUG) Log.d(this.javaClass.name, message)
}