package ercanduman.visualizerestaurant.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ercanduman.visualizerestaurant.data.db.entity.Restaurant
import ercanduman.visualizerestaurant.data.repository.AppRepository
import kotlinx.coroutines.launch

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
     * Updates object and send parameter object to repository.
     * @param restaurant Restaurant
     */
    internal fun update(restaurant: Restaurant) {
        viewModelScope.launch { repository.update(restaurant) }
    }

    private val allItems = MediatorLiveData<List<Restaurant>>()

    /**
     * Fetches all data from repository and returns LiveData of restaurant list.
     *
     * @return LiveData<List<Restaurant>>
     */
    internal fun getRestaurants(): LiveData<List<Restaurant>> {
        viewModelScope.launch {
            allItems.addSource(repository.getRestaurants()) { items ->
                allItems.value = sortItems(items)
            }
        }
        return allItems
    }

    private fun sortItems(items: List<Restaurant>) =
        items.sortedWith(
            compareByDescending<Restaurant> { it.isFavorite }   // favorites always at top.
                .thenComparing(::compareByOpeningState)         // Sort based on Opening state
                .thenComparing { o1, o2 ->
                    compareByBestMatch(o1, o2, sortType.name) // Sort based on Sorting Option
                }
        )

    private fun compareByBestMatch(
        o1: Restaurant,
        o2: Restaurant,
        fieldName: String
    ): Int {
        return when (fieldName) {
            "averageProductPrice" -> o1.sortingValues.averageProductPrice.compareTo(o2.sortingValues.averageProductPrice)
            "bestMatch" -> o1.sortingValues.bestMatch.compareTo(o2.sortingValues.bestMatch)
            "deliveryCosts" -> o1.sortingValues.deliveryCosts.compareTo(o2.sortingValues.deliveryCosts)
            "distance" -> o1.sortingValues.distance.compareTo(o2.sortingValues.distance)
            "minCost" -> o1.sortingValues.minCost.compareTo(o2.sortingValues.minCost)
            "newest" -> o1.sortingValues.newest.compareTo(o2.sortingValues.newest)
            "popularity" -> o1.sortingValues.popularity.compareTo(o2.sortingValues.popularity)
            "ratingAverage" -> o1.sortingValues.ratingAverage.compareTo(o2.sortingValues.ratingAverage)
            else -> 0
        }
    }

    private fun compareByOpeningState(o1: Restaurant, o2: Restaurant) =
        o1.status.length.compareTo(o2.status.length)
}