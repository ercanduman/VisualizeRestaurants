package ercanduman.visualizerestaurant.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ercanduman.visualizerestaurant.Constants
import ercanduman.visualizerestaurant.data.db.dao.RestaurantDao
import ercanduman.visualizerestaurant.data.db.entity.Restaurant

/**
 * Inherits from RoomDatabase and creates SqLite Database for storing and retrieving all items locally.
 *
 * Notice: Singleton functionality and creation of AppDatabase instance will be handled by Dagger-Hilt library
 *
 * @author ERCAN DUMAN
 * @since  26.11.2020
 */
@Database(entities = [Restaurant::class], version = Constants.DB_VERSION)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): RestaurantDao
}