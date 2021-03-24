package ercanduman.visualizerestaurant.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Data class to store object fields.
 *
 * This class will have a mapping SQLite table called Restaurant in the database.
 * And class fields will be matched to table columns.
 * Each entity must have at least 1 field annotated with PrimaryKey.
 *
 * @property name String
 * @property status String
 * @property sortingValues SortingValues
 * @property isFavorite Boolean
 * @property id Int primary key of table
 * @constructor
 */
@Entity
data class Restaurant(
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: String,
    @SerializedName("sortingValues") val sortingValues: SortingValues,
    var isFavorite: Boolean = false,
    @PrimaryKey(autoGenerate = true) var id: Int = 0
)