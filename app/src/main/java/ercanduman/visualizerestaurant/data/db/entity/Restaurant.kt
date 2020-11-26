package ercanduman.visualizerestaurant.data.db.entity

import androidx.room.Entity

@Entity
data class Restaurant(
    val name: String,
    val sortingValues: SortingValues,
    val status: String,
    var isFavorite: Boolean = false
)