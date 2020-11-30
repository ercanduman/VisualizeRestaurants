package ercanduman.visualizerestaurant.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import ercanduman.visualizerestaurant.data.db.AppDatabase
import javax.inject.Named

/**
 * @author ERCAN DUMAN
 * @since  30.11.2020
 */

@Module
@InstallIn(ApplicationComponent::class)
object TestModule {

    /**
     * Creates an instance of AppDatabase for an in memory database
     * which means it wont effect our real AppDatabase in persistence storage.
     *
     * @param context Context
     * @return AppDatabase inMemoryDatabase instance.
     */
    @Provides
    @Named("test_db")
    fun provideImMemoryDatabase(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .build()
}