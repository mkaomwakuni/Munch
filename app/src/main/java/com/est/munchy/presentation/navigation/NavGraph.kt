package com.est.munchy.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.est.munchy.presentation.cart.MyCartScreen
import com.est.munchy.presentation.home.HomeScreen
import com.est.munchy.presentation.menu.MenuScreen
import com.est.munchy.presentation.onboarding.MunchSteamLayout
import com.est.munchy.presentation.onboarding.OnboardStart
import com.est.munchy.presentation.registration.LoginScreen
import com.est.munchy.presentation.registration.SignUpScreen

@Composable
fun NavGraph(navController: NavHostController) {
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
        composable(Routes.MenuScreen.route) {
            MenuScreen(navController)
        }
        composable(Routes.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(Routes.Favourites.route) {
            MyCartScreen(navController)
        }
        composable(Routes.ProfileScreen.route) {
            MyCartScreen(navController)
        }
    }
}