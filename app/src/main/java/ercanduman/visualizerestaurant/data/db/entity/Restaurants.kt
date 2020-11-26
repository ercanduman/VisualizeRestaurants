package ercanduman.visualizerestaurant.data.db.entity

import com.google.gson.annotations.SerializedName

/**
 * @author ERCAN DUMAN
 * @since  26.11.2020
 */
data class Restaurants(
    @SerializedName("restaurants") val restaurants: List<Restaurant>
)
