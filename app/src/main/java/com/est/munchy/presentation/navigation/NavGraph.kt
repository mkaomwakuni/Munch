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
import com.est.munchy.presentation.onboarding.OnboardStart
import com.est.munchy.presentation.registration.LoginScreen
import com.est.munchy.presentation.registration.SignUpScreen
import com.est.munchy.viewModels.RecipeViewModel

@Composable
fun NavGraph(
    navController: NavHostController
    ) {
    val recipeViewModel: RecipeViewModel = hiltViewModel()

    NavHost (
        navController = navController,
        startDestination = Routes.SignInScreen.route
    ) {
        composable(Routes.SignInScreen.route) {
            LoginScreen(navController)
        }
        composable(Routes.OnboardingScreen.route) {
            OnboardStart(navController)
        }
        composable(Routes.SignUpScreen.route) {
            SignUpScreen(navController)
        }
        composable(
            route = Routes.RecipeScreen.route + "/{recipeId}",
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId")?: - 1
            //fetch the recipe details using recipeId from ViewModel
            RecipeDetailScreen(
                navController = navController,
                recipeId = recipeId,
                viewModel = recipeViewModel
            )
        }
        composable(Routes.HomeScreen.route) {
            HomeScreen(
                navController

            )
        }
        composable(Routes.Favourites.route) {
            FavouritesScreen(navController)
        }
        composable(Routes.JokeScreen.route) {
            FoodJokeScreen(navController)
        }
//        composable(
//            route = "recipe/{recipeId}",
//            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
//        )
//        { backStackEntry ->
//            val recipeId = backStackEntry.arguments?.getInt("recipeId")?: return@composable
//            RecipeDetailScreen(navController = navController, recipeId = recipeId)
//        }
    }
}