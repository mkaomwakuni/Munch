package com.est.munchy.data

import com.est.munchy.data.database.LocalDataSource
import com.est.munchy.data.database.local.entities.BookedRecipeEntity
import com.est.munchy.data.database.local.entities.FoodJokeEntity
import com.est.munchy.data.database.local.entities.RecipeEntity
import com.est.munchy.data.remote.RemoteDataSource
import com.est.munchy.domain.model.FoodJokes
import com.est.munchy.domain.model.MunchRecipe
import com.est.munchy.utils.NetworkChecker
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@ViewModelScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
) {

    val remote = remoteDataSource
    val local = localDataSource

}