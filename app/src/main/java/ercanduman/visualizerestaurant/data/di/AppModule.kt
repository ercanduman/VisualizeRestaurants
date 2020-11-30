package ercanduman.visualizerestaurant.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import ercanduman.visualizerestaurant.Constants
import ercanduman.visualizerestaurant.data.base.BaseRepository
import ercanduman.visualizerestaurant.data.datasource.LocalDataSource
import ercanduman.visualizerestaurant.data.db.AppDatabase
import ercanduman.visualizerestaurant.data.db.dao.RestaurantDao
import ercanduman.visualizerestaurant.data.repository.AppRepository
import javax.inject.Singleton

/**
 * Tells Dagger how to generate instance of objects.
 * Annotating as InstallIn declares which component(s) the annotated class should be included in
 * when Hilt generates the components.
 *
 * ApplicationComponent means Hilt component has the lifetime of the application.
 *
 * @author ERCAN DUMAN
 * @since  26.11.2020
 */
@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    /**
     * Provides Room database and gives ability to Inject database into other classes.
     *
     * Singleton: Identifies that the injector only instantiates once.
     *
     * @param context Context
     * @return AppDatabase
     */
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, Constants.DB_NAME).build()

    /**
     * Provides data access object (RestaurantDao) that can be used for database queries.
     *
     * Singleton: Identifies that the injector only instantiates once.
     *
     * @param db AppDatabase
     * @return AppDatabase
     */
    @Singleton
    @Provides
    fun provideDAO(db: AppDatabase) = db.dao()

    /**
     * Provides LocalDataSource in order to read JSON data from file and retrieve restaurant list.
     *
     * Singleton: Identifies that the injector only instantiates once.
     *
     * @param context Context
     * @return AppDatabase
     */
    @Singleton
    @Provides
    fun provideLocalDataSource(@ApplicationContext context: Context) = LocalDataSource(context)

    @Singleton
    @Provides
    fun provideBaseRepository(
        dao: RestaurantDao,
        localDataSource: LocalDataSource
    ) = AppRepository(dao, localDataSource) as BaseRepository
}