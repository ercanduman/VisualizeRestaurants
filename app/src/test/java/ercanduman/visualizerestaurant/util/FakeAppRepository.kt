package ercanduman.visualizerestaurant.util

import ercanduman.visualizerestaurant.data.base.BaseRepository
import ercanduman.visualizerestaurant.data.db.entity.Restaurant
import ercanduman.visualizerestaurant.data.db.entity.SortingValues
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Fake implementation of Repository in order to use in ViewModel test cases.
 *
 * Basically, this class simulates behaviour of real app repository.
 *
 * Data structures will be used instead of using persistence database.
 *
 * @author ERCAN DUMAN
 * @since  30.11.2020
 */
class FakeAppRepository : BaseRepository {
    private val restaurants: MutableMap<Int, Restaurant> = getFakeRestaurants()

    /**
     * Simulates LiveData for getRestaurants() function.
     */
    private var observableRestaurants: Flow<List<Restaurant>> = flowOf(restaurants.values.toList())

    override suspend fun getRestaurants(): Flow<List<Restaurant>> {
        return observableRestaurants
    }

    override suspend fun update(restaurant: Restaurant) {
        restaurants.replace(restaurant.id, restaurant)
        observableRestaurants = flowOf(restaurants.values.toList())
    }

    private fun getFakeRestaurants(): MutableMap<Int, Restaurant> {
        val sortingValues = SortingValues(1, 1.1, 1, 1, 1, 1.1, 1.1, 1.1)
        val restaurant1 = Restaurant("Test Restaurant1", "open", sortingValues, id = 1)
        val restaurant2 = Restaurant("Test Restaurant2", "Closed", sortingValues, id = 2)
        val restaurant3 = Restaurant("Test Restaurant3", "Order ahead", sortingValues, id = 3)

        return mutableMapOf(
            1 to restaurant1,
            2 to restaurant2,
            3 to restaurant3
        )
    }
}