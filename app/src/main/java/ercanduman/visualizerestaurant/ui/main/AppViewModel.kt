package ercanduman.visualizerestaurant.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import ercanduman.visualizerestaurant.data.repository.AppRepository

/**
 * The ViewModel will be available for creation by the HiltViewModelFactory and can be retrieved by
 * default in an Activity or Fragment annotated with @AndroidEntryPoint
 *
 * @author ERCAN DUMAN
 * @since  26.11.2020
 */
class AppViewModel
@ViewModelInject constructor(private val repository: AppRepository) : ViewModel() {

    var sortType = SortType.popularity

    /**
     * Fetches all data from repository and returns LiveData of restaurant list.
     *
     * @return LiveData<List<Restaurant>>
     */
    suspend fun getRestaurants() = repository.getRestaurants(sortType.name)
}