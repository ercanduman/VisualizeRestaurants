package ercanduman.visualizerestaurant.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ercanduman.visualizerestaurant.Constants
import ercanduman.visualizerestaurant.data.db.dao.RestaurantDao
import ercanduman.visualizerestaurant.data.db.entity.Restaurant
import ercanduman.visualizerestaurant.data.db.entity.SortingValues

/**
 * Inherits from RoomDatabase and creates SqLite Database for storing and retrieving all items locally.
 *
 * Notice: Singleton functionality and creation of AppDatabase instance will be handled by Dagger-Hilt library
 *
 * @author ERCAN DUMAN
 * @since  26.11.2020
 */
@Database(entities = [Restaurant::class], version = Constants.DB_VERSION, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): RestaurantDao
}

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun objectToString(sortingValues: SortingValues): String = gson.toJson(sortingValues)

    @TypeConverter
    fun stringToObject(jsonData: String): SortingValues {
        val itemType = object : TypeToken<SortingValues>() {}.type
        return gson.fromJson(jsonData, itemType)
    }
}