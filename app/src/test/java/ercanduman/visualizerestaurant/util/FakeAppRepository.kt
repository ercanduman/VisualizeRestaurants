package ercanduman.visualizerestaurant.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ercanduman.visualizerestaurant.data.base.BaseRepository
import ercanduman.visualizerestaurant.data.db.entity.Restaurant

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
    private val restaurants = mutableMapOf<Int, Restaurant>()

    /**
     * Simulates LiveData for getRestaurants() function.
     */
    private val observableRestaurants = MutableLiveData(restaurants.values.toList())

    override suspend fun getRestaurants(): LiveData<List<Restaurant>> {
        return observableRestaurants
    }

    override suspend fun update(restaurant: Restaurant) {
        restaurants.replace(restaurant.id, restaurant)
        observableRestaurants.postValue(restaurants.values.toList())
    }
}