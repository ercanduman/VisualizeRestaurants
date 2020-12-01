package ercanduman.visualizerestaurant.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ercanduman.visualizerestaurant.data.base.BaseRepository
import ercanduman.visualizerestaurant.data.db.entity.Restaurant
import kotlinx.coroutines.launch

/**
 * The ViewModel will be available for creation by the HiltViewModelFactory and can be retrieved by
 * default in an Activity or Fragment annotated with @AndroidEntryPoint
 *
 * @author ERCAN DUMAN
 * @since  26.11.2020
 */
class AppViewModel
@ViewModelInject constructor(private val repository: BaseRepository) : ViewModel() {

    var sortType = SortType.bestMatch

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

    /**
     * Sorts all restaurant list based on following options and returns sorted list
     *
     * Priority of the sorting (highest to the lowest priority)
     *     1. Favourites: Favourite restaurants are at the top
     *     2. Openings state:
     *          open (top), order ahead (middle), closed (bottom)
     *     3. Sort options which can be best match, newest, ratingAverage, etc.
     *
     * NOTICE:
     *  if there are multiple favourite restaurants, then sorting should be based on
     *  current opening state and current selected sort.
     *
     * @param items List<Restaurant>
     * @return List<Restaurant>
     */
    private fun sortItems(items: List<Restaurant>) =
        items.sortedWith(
            compareBy<Restaurant> { it.isFavorite }      // Favorites always at top.
                .thenComparing(::compareByOpeningState)  // Sort based on Opening state
                .thenComparing(::compareBySortingOption) // Sort based on Sorting Option
                .reversed())

    private fun compareByOpeningState(o1: Restaurant, o2: Restaurant): Int {
        val compareStatus = o1.status.compareTo(o2.status)
        if (compareStatus != 0) {
            if (o1.status == "open") return 1
            else if (o2.status == "open") return -1
        }
        return compareStatus
    }

    private fun compareBySortingOption(
        o1: Restaurant,
        o2: Restaurant
    ): Int {
        return when (sortType.name) {
            "bestMatch" -> o1.sortingValues.bestMatch.compareTo(o2.sortingValues.bestMatch)
            "newest" -> o1.sortingValues.newest.compareTo(o2.sortingValues.newest)
            "ratingAverage" -> o1.sortingValues.ratingAverage.compareTo(o2.sortingValues.ratingAverage)
            "distance" -> o1.sortingValues.distance.compareTo(o2.sortingValues.distance)
            "popularity" -> o1.sortingValues.popularity.compareTo(o2.sortingValues.popularity)
            "averageProductPrice" -> o1.sortingValues.averageProductPrice.compareTo(o2.sortingValues.averageProductPrice)
            "deliveryCosts" -> o1.sortingValues.deliveryCosts.compareTo(o2.sortingValues.deliveryCosts)
            "minCost" -> o1.sortingValues.minCost.compareTo(o2.sortingValues.minCost)
            else -> 0
        }
    }
}