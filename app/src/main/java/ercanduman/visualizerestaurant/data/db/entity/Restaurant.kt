package ercanduman.visualizerestaurant.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class to store object fields.
 *
 * This class will have a mapping SQLite table called Restaurant in the database.
 * And class fields will be matched to table columns.
 * Each entity must have at least 1 field annotated with PrimaryKey.
 *
 * @property name String
 * @property sortingValue SortingValues
 * @property status String
 * @property isFavorite Boolean
 * @constructor
 */
@Entity
data class Restaurant(
    val name: String,
    val sortingValue: SortingValues,
    val status: String,
    var isFavorite: Boolean = false
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}