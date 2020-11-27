package ercanduman.visualizerestaurant.data.repository

import androidx.lifecycle.LiveData
import ercanduman.visualizerestaurant.data.datasource.LocalDataSource
import ercanduman.visualizerestaurant.data.db.dao.RestaurantDao
import ercanduman.visualizerestaurant.data.db.entity.Restaurant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Middle class which responsible for collecting all data from different data sources
 * and supply this data to ViewModel.
 *
 * Notice: Here Constructor Injection used instead of Field Injection because when field injection used later on
 * it cannot be clear what objects injected in Repository and classes will be tightly coupled.
 * For more drawbacks of field Injection: https://stackoverflow.com/a/39892204/4308897
 *
 * @author ERCAN DUMAN
 * @since  26.11.2020
 */
class AppRepository @Inject constructor(
    private val dao: RestaurantDao,
    private val localSource: LocalDataSource
) {
    /**
     * Retrieves all items from local database and returns LiveData
     *
     * If local database does not contain any item, then uses localSource to read json file and store items into database.
     *
     * @return LiveData<List<Restaurant>>
     */
    suspend fun getRestaurants(): LiveData<List<Restaurant>> {
        return withContext(Dispatchers.IO) {
            val count = dao.getCount()
            if (count == 0) {
                dao.insert(localSource.getRestaurants())
            }
            dao.getAllRestaurants()
        }
    }

    /**
     * Sends passed object to data access object (DAO).
     *
     * @param restaurant Restaurant
     */
    suspend fun update(restaurant: Restaurant) = dao.update(restaurant)
}