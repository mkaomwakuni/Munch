package com.est.munchy.di

import android.content.Context
import androidx.room.Room
import com.est.munchy.data.database.local.database.RecipeDatabase
import com.est.munchy.utils.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
/**
 * [DatabaseModule] provides dependencies related to the Room database.
 *
 * This Hilt module is responsible for creating and providing instances of the
 * [RecipeDatabase] and its associated [MunchDao]. By using Hilt, the database and DAO can be
 * easily injected into other parts of the application where they are needed.
 *
 * The module is installed in [SingletonComponent], making the database and DAO instances
 * available throughout the application's lifecycle as singletons.
 *
 * @Module Indicates that this is a Hilt module.
 * @InstallIn(SingletonComponent::class) Specifies that the module should be installed in
 *           the application-scoped component.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Provides a singleton instance of the [RecipeDatabase].
     *
     * This method creates the Room database using the provided [Context] and configures it with
     * the [RecipeDatabase] class and database name. It also includes a fallback to destructive
     * migration strategy in case of schema changes.
     *
     * @param context The application context. Injected by Hilt.
     * @return A singleton instance of [RecipeDatabase].
     *
     * @Provides Indicates that this method provides a dependency.
     * @Singleton Ensures only one instance of the database is created.
     */
    @Provides
    @Singleton
    fun providesDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        RecipeDatabase::class.java,
        AppConstants.DATABASE_NAME
    ).fallbackToDestructiveMigration()
        .build()

    /**
     * Provides a singleton instance of the [MunchDao].
     *
     * This method retrieves the [MunchDao] from the provided [RecipeDatabase] instance.
     *
     * @param database The [RecipeDatabase] instance. Injected by Hilt.
     * @return A singleton instance of [MunchDao].
     *
     * @Provides Indicates that this method provides a dependency.
     * @Singleton Ensures only one instance of the DAO is created.
     */
    @Provides
    @Singleton
    fun providesDao(database: RecipeDatabase) = database.munchDao()
}