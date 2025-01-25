package com.est.munchy.presentation.navigation

sealed class Routes(val route: String) {

    data object HomeScreen : Routes(ScreenConstants.HOME_SCREEN)
    data object RecipeScreen : Routes(ScreenConstants.RECIPE_SCREEN)
    data object Favourites : Routes(ScreenConstants.FAVOURITES_SCREEN)
    data object JokeScreen : Routes(ScreenConstants.JOKE_SCREEN)

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}