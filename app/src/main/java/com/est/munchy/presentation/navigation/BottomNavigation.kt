package com.est.munchy.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BottomNavigation(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        NavigationBar(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            containerColor = Color.Transparent,
        ) {
            NavigationBarItem(
                icon = { Icon(Icons.Outlined.Home, contentDescription = null) },
                selected = navController.currentDestination?.route == Routes.HomeScreen.route,
                onClick = { navController.navigate(route = Routes.HomeScreen.route) }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Default.FavoriteBorder, contentDescription = null) },
                selected = navController.currentDestination?.route == Routes.Favourites.route,
                onClick = { navController.navigate(route = Routes.Favourites.route) }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Outlined.Info, contentDescription = null) },
                selected = false,
                onClick = { navController.navigate(route = Routes.JokeScreen.route) }
            )
        }
    }
}