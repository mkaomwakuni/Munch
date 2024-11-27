package com.est.munchy.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun BottomNavigation(navController: NavController) {
    NavigationBar(
        containerColor = Color.White,
        contentColor = Color(0xFF2E7D32)
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text("Home") },
            selected = navController.currentDestination?.route == "home",
            onClick = { navController.navigate("home") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Menu, contentDescription = null) },
            label = { Text("Menu") },
            selected = navController.currentDestination?.route == "menu",
            onClick = { navController.navigate("menu") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = null) },
            label = { Text("Profile") },
            selected = false,
            onClick = { /* Handle profile navigation */ }
        )
    }
}

@Preview
@Composable
fun BottomNavigationPreview() {
    val navController = rememberNavController()
    BottomNavigation(navController)
}