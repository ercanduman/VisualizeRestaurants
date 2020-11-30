package ercanduman.visualizerestaurant.data.base

import androidx.lifecycle.LiveData
import ercanduman.visualizerestaurant.data.db.entity.Restaurant

/**
 * Base repository class to make sure that all derived classes have same implementation.
 *
 * @author ERCAN DUMAN
 * @since  30.11.2020
 */
interface BaseRepository {
    suspend fun getRestaurants(): LiveData<List<Restaurant>>
    suspend fun update(restaurant: Restaurant)
}