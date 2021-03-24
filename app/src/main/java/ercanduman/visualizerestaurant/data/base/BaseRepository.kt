package ercanduman.visualizerestaurant.data.base

import ercanduman.visualizerestaurant.data.db.entity.Restaurant
import kotlinx.coroutines.flow.Flow

/**
 * Base repository class to make sure that all derived classes have same implementation.
 *
 * @author ERCAN DUMAN
 * @since  30.11.2020
 */
interface BaseRepository {
    suspend fun getRestaurants(): Flow<List<Restaurant>>
    suspend fun update(restaurant: Restaurant)
}