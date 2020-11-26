package ercanduman.visualizerestaurant

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * This base class will take care of injecting members into the Android class as well as handling
 * instantiating the proper Hilt components at the right point in the lifecycle.
 *
 * Annotation as HiltAndroidApp will make the MainApplication class injectable and
 * tells the Dagger to generate components.
 *
 *
 * @author ERCAN DUMAN
 * @since  26.11.2020
 */
@HiltAndroidApp
class MainApplication : Application()