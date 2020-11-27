package ercanduman.visualizerestaurant.data.db.entity

import com.google.gson.annotations.SerializedName

/**
 * GSON helper class for reading json file content and implement to Kotlin objects.
 *
 * @author ERCAN DUMAN
 * @since  26.11.2020
 */
data class GsonHelperClass(
    @SerializedName("restaurants") val restaurants: List<Restaurant>
)
