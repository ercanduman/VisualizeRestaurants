package ercanduman.visualizerestaurant.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import ercanduman.visualizerestaurant.data.base.BaseRepository
import ercanduman.visualizerestaurant.data.repository.FakeAppRepository
import ercanduman.visualizerestaurant.util.MainDispatcherRule
import ercanduman.visualizerestaurant.util.getOrAwaitValueTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Test class to store all unit test cases for [AppViewModel]
 *
 * @author ercanduman
 * @since 24.03.2021
 */
@ExperimentalCoroutinesApi
class AppViewModelTest {
    /**
     * Swaps the background executor used by the Architecture Components which executes each task synchronously.
     */
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    lateinit var repository: BaseRepository
    lateinit var viewModel: AppViewModel

    @Before
    fun setUp() {
        repository = FakeAppRepository()
        viewModel = AppViewModel(repository)
    }

    @Test
    fun test_get_restaurant_list() {
        val restaurants = viewModel.getRestaurants().getOrAwaitValueTest()
        assertThat(restaurants.size).isEqualTo(FakeAppRepository.ITEM_COUNT)
    }

    @Test
    fun test_update_restaurant() {
        val restaurants = viewModel.getRestaurants().getOrAwaitValueTest()

        val updatedName = "Test Restaurant UPDATED"
        val currentRestaurant = restaurants[0]

        viewModel.update(currentRestaurant.copy(name = updatedName))
    }

    @Test
    fun test_update_restaurant_and_check_if_available() {
        val restaurants = viewModel.getRestaurants().getOrAwaitValueTest()

        val updatedName = "Test Restaurant UPDATED"
        val currentRestaurant = restaurants[0]

        viewModel.update(currentRestaurant.copy(name = updatedName))

        val updatedRestaurants = viewModel.getRestaurants().getOrAwaitValueTest()
        val updated = updatedRestaurants.first { it.id == currentRestaurant.id }
        assertThat(updated.name).isEqualTo(updatedName)
    }

    @Test
    fun test_favorite_items_always_at_top() {
        val restaurantList = viewModel.getRestaurants().getOrAwaitValueTest()

        val updated = restaurantList[restaurantList.size - 1]
        viewModel.update(updated.copy(isFavorite = true))

        val updatedList = viewModel.getRestaurants().getOrAwaitValueTest()
        val favoriteItem = updatedList[0]

        assertThat(updated.name).isEqualTo(favoriteItem.name)
    }

    @Test
    fun test_sorted_items_based_on_opening_state() {
        val restaurants = viewModel.getRestaurants().getOrAwaitValueTest()
        val currentItem = restaurants[0]
        assertThat(currentItem.status).isEqualTo("open")
    }

    @Test
    fun test_sort_items_based_on_nearest_distance() {
        viewModel.sortType = SortType.distance
        val restaurants = viewModel.getRestaurants().getOrAwaitValueTest()
        val minDistanceItem = restaurants[0]

        // find restaurant based on min distance
        val filteredItem = restaurants.minByOrNull { it.sortingValues.distance }
        assertThat(minDistanceItem).isEqualTo(filteredItem)
    }
}