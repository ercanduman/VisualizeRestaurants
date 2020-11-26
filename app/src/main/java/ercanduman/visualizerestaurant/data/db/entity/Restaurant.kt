package ercanduman.visualizerestaurant.data.db.entity


data class Restaurant(
    val name: String,
    val sortingValues: SortingValues,
    val status: String,
    var isFavorite: Boolean = false
)