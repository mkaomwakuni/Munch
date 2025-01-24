package com.est.munchy.presentation.navigation

import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun BottomNavigation(
    navController: NavController) {
    NavigationBar(
        modifier = Modifier
            .background(Color.Transparent)
            .clip(RoundedCornerShape(10.dp))
            .padding(10.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = Color(0xFF2E7D32)
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.Home, contentDescription = null) },
            label = { Text("Menu") },
            selected = navController.currentDestination?.route == Routes.HomeScreen.route,
            onClick = { navController.navigate(route = Routes.HomeScreen.route) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.FavoriteBorder, contentDescription = null) },
            label = { Text("Saved") },
            selected = navController.currentDestination?.route == Routes.Favourites.route,
            onClick = { navController.navigate(route = Routes.Favourites.route) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.Info, contentDescription = null) },
            label = { Text("Jokes") },
            selected = false,
            onClick = { navController.navigate(route = Routes.JokeScreen.route) }
        )
    }
}

@Preview
@Composable
fun BottomNavigationPreview() {
    val navController = rememberNavController()
    BottomNavigation(navController)
}