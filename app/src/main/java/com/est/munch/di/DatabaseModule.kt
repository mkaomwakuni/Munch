package com.est.munch.di

import android.content.Context
import androidx.room.Room
import com.est.munch.data.database.local.database.RecipeDatabase
import com.est.munch.utils.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        RecipeDatabase::class.java,
        AppConstants.DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun providesDao(database: RecipeDatabase) = database.munchDao()
}