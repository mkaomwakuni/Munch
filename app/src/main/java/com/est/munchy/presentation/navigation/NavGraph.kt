/*
 * MIT License
 * 
 * Copyright (c) 2025 Husty9 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
    navController: NavHostController, onToggleTheme:()-> Unit, isDarkTheme:Boolean
    ) {
    val recipeViewModel: RecipeViewModel = hiltViewModel()
    NavHost (
        navController = navController,
        startDestination = Routes.HomeScreen.route
    ) {
        composable(Routes.HomeScreen.route) {
            HomeScreen(
                navController, onToggleTheme = onToggleTheme, isDarkTheme = isDarkTheme
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