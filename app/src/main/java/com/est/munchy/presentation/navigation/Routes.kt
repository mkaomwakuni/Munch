package com.est.munchy.presentation.navigation

sealed class Routes(val route: String) {
    data object OnboardingScreen : Routes(ScreenConstants.ONBOARDING_SCREEN)
    data object SignInScreen : Routes(ScreenConstants.SIGN_IN_SCREEN)
    data object SignUpScreen : Routes(ScreenConstants.SIGN_UP_SCREEN)
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