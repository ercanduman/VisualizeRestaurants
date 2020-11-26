package ercanduman.visualizerestaurant.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ercanduman.visualizerestaurant.data.db.entity.Restaurant
import ercanduman.visualizerestaurant.data.db.entity.SortingValues

/**
 * Data Access Object which contains functions for database queries.
 *
 * @author ERCAN DUMAN
 * @since  26.11.2020
 */
@Dao
interface RestaurantDao {
    /**
     * Insert all items to database.
     * If there any conflicts with objects than it will replace old data.
     * @param list List<Restaurant>
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<Restaurant>)

    /**
     * Updates current object in database
     * @param restaurant Restaurant
     */
    @Update
    suspend fun update(restaurant: Restaurant)

    /**
     * Provides all items in database based on sortingValue parameter.
     *
     * This function will LiveData that we can observe and changes later on.
     *
     * @return LiveData<List<Restaurant>>
     */
    @Query("SELECT * FROM Restaurant ORDER BY isFavorite, sortingValue =:sortingValue ASC")
    fun getAllRestaurants(sortingValue: SortingValues): LiveData<List<Restaurant>>
}