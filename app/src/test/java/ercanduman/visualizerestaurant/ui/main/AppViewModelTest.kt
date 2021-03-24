package ercanduman.visualizerestaurant.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import ercanduman.visualizerestaurant.data.base.BaseRepository
import ercanduman.visualizerestaurant.util.FakeAppRepository
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
        assertThat(restaurants.size).isEqualTo(3)
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
}