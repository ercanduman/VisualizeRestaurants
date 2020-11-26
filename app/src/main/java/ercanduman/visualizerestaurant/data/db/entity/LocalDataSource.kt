package ercanduman.visualizerestaurant.data.db.entity

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ercanduman.visualizerestaurant.Constants

/**
 * Responsible for reading json file from assets and returns list of Restaurants based on file content.
 *
 * @author ERCAN DUMAN
 * @since  26.11.2020
 */

object LocalDataSource {
    /**
     * Reads JSON file and returns list of Restaurant based of file content.
     *
     * @param context Context
     * @return List<Restaurant>
     */
    fun getRestaurants(context: Context): List<Restaurant> {
        val content = getFileContent(context)
        return Gson().fromJson(content, object : TypeToken<List<Restaurant>>() {}.type)
    }

    /**
     * Reads JSON file from assets folder and returns file content.
     *
     * @param context Context
     * @return File content as String
     */
    private fun getFileContent(context: Context): String = context.assets
        .open(Constants.JSON_FILE_NAME)
        .bufferedReader()
        .use { it.readText() }
}