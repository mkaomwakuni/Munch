package com.est.munchy.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.est.munchy.presentation.navigation.BottomNavigation
import com.est.munchy.presentation.menu.DeliveryCard
import com.est.munchy.presentation.menu.PromotionCard
import com.est.munchy.presentation.menu.SearchBar
import com.est.munchy.presentation.menu.TopOfWeekItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(
                    "Home",
                    fontWeight = FontWeight.Bold)
                        },
                actions = {
                    IconButton(onClick = { /* Handle notification */ }) {
                        Icon(
                            Icons.Outlined.Notifications,
                            contentDescription = "Notifications"
                        )
                    }
                }
            )
        },
        bottomBar = { BottomNavigation(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            SearchBar()
            Spacer(modifier = Modifier.height(16.dp))
            DeliveryCard()
            Spacer(modifier = Modifier.height(16.dp))
            PromotionCard()
            Spacer(modifier = Modifier.height(16.dp))
            Text("Top of Week", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            TopOfWeekItems()
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController)
}