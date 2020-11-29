package ercanduman.visualizerestaurant.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import ercanduman.visualizerestaurant.data.db.dao.RestaurantDao
import ercanduman.visualizerestaurant.data.db.entity.Restaurant
import ercanduman.visualizerestaurant.data.db.entity.SortingValues
import ercanduman.visualizerestaurant.util.getOrAwaitValue
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author ERCAN DUMAN
 * @since  29.11.2020
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class AppDatabaseTest {
    /**
     * Swaps the background executor used by the Architecture Components which executes each task synchronously.
     */
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dao: RestaurantDao
    private lateinit var database: AppDatabase

    @Before
    fun setup() {
        /**
         * inMemoryDatabaseBuilder creates an instance of AppDatabase for an in memory database
         * which means it wont effect our real AppDatabase in persistence storage. And no need to
         * create instance of database for each test case.
         */
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()

        dao = database.dao()
    }

    @After
    fun destroy() {
        database.close()
    }


    /**
     * runBlockingTest executes a test inside an immediate execution dispatcher.
     */
    @Test
    fun test_insert_new_restaurant_item_into_database() = runBlockingTest {
        val sortingValues = SortingValues(1, 1.1, 1, 1, 1, 1.1, 1.1, 1.1)
        val restaurant = Restaurant("Test Restaurant1", "open", sortingValues)
        dao.insert(listOf(restaurant))

        val itemCount = dao.getCount()
        assertEquals(itemCount, 1)
    }

    @Test
    fun test_add_3_different_items_and_check_count() = runBlockingTest {
        val sortingValues = SortingValues(1, 1.1, 1, 1, 1, 1.1, 1.1, 1.1)
        val restaurant1 = Restaurant("Test Restaurant1", "open", sortingValues)
        val restaurant2 = Restaurant("Test Restaurant2", "Closed", sortingValues)
        val restaurant3 = Restaurant("Test Restaurant3", "Order ahead", sortingValues)
        dao.insert(listOf(restaurant1, restaurant2, restaurant3))

        val itemsCount = dao.getCount()
        assertEquals(itemsCount, 3)
    }

    @Test
    fun test_insert_a_new_item_and_update_it() = runBlockingTest {
        val sortingValues = SortingValues(1, 1.1, 1, 1, 1, 1.1, 1.1, 1.1)
        var restaurant = Restaurant("Test Restaurant1", "open", sortingValues)
        dao.insert(listOf(restaurant))

        restaurant = Restaurant("Test Restaurant UPDATED", "Closed", sortingValues)
        dao.update(restaurant)
    }

    @Test
    fun test_insert_new_item_and_observe_it_in_live_data() = runBlockingTest {
        val sortingValues = SortingValues(1, 1.1, 1, 1, 1, 1.1, 1.1, 1.1)
        val restaurant = Restaurant("Test Restaurant1", "open", sortingValues)
        dao.insert(listOf(restaurant))

        val items: List<Restaurant> = dao.getAllRestaurants().getOrAwaitValue()
        assertEquals(items.contains(restaurant), true)
    }
}