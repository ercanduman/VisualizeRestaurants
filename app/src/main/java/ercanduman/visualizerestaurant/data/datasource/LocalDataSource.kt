package ercanduman.visualizerestaurant.data.datasource

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ercanduman.visualizerestaurant.Constants
import ercanduman.visualizerestaurant.data.db.entity.Restaurant

/**
 * Responsible for reading json file from assets and returns list of Restaurants based on file content.
 *
 * @author ERCAN DUMAN
 * @since  26.11.2020
 */

class LocalDataSource(private val context: Context) {
    /**
     * Reads JSON file and returns list of Restaurant based of file content.
     *
     * @return List<Restaurant>
     */
    fun getRestaurants(): List<Restaurant> {
        return Gson().fromJson(getFileContent(), object : TypeToken<List<Restaurant>>() {}.type)
    }

    /**
     * Reads JSON file from assets folder and returns file content.
     *
     * @return File content as String
     */
    private fun getFileContent(): String = context.assets
        .open(Constants.JSON_FILE_NAME)
        .bufferedReader()
        .use { it.readText() }
}