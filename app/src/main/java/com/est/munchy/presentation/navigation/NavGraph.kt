package com.est.munchy.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.est.munchy.presentation.detail.RecipeDetailScreen
import com.est.munchy.presentation.favourites.FavouritesScreen
import com.est.munchy.presentation.home.HomeScreen
import com.est.munchy.presentation.jokes.FoodJokeScreen
import com.est.munchy.viewModels.RecipeViewModel

@Composable
fun NavGraph(
    navController: NavHostController
    ) {
    val recipeViewModel: RecipeViewModel = hiltViewModel()
    NavHost (
        navController = navController,
        startDestination = Routes.HomeScreen.route
    ) {
        composable(Routes.HomeScreen.route) {
            HomeScreen(
                navController
            )
        }
        composable(
            route = Routes.RecipeScreen.route + "/{recipeId}",
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: -1
            //fetch the recipe details using recipeId from ViewModel
            RecipeDetailScreen(
                navController = navController,
                recipeId = recipeId,
                viewModel = recipeViewModel
            )
        }
        composable(Routes.Favourites.route) {
            FavouritesScreen(navController)
        }
        composable(Routes.JokeScreen.route) {
            FoodJokeScreen(navController)
        }
    }
}