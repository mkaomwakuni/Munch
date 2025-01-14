package com.est.munchy.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.est.munchy.R
import com.est.munchy.domain.model.ModelResult
import com.est.munchy.presentation.menu.SearchBar
import com.est.munchy.presentation.navigation.BottomNavigation
import com.est.munchy.viewModels.MainViewModel
import com.est.munchy.viewModels.RecipeViewModel
import com.est.munchy.viewModels.events.MainEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    recipesViewModel: RecipeViewModel = hiltViewModel()) {

    val mainUistate = mainViewModel.uiState.collectAsState()
    val recipesUistate = recipesViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        mainViewModel.onEvent(MainEvent.RefreshRecipes)
    }

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
            SearchBar(
                value = mainUistate.value.searchQuery,
                onValueChange = { query ->
                    mainViewModel.onEvent(MainEvent.SearchRecipes(query))
                },
                placeholder = "Search Your Menus"
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (!recipesUistate.value.isOnline) {
                Text(
                    "No Internet Connection",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
            }

            if (mainUistate.value.isLoading) {
                Text(
                    "Loading...",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
            }

            mainUistate.value.error?.let {
                Text(
                    text = "error",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            // PromotionCard()
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Top of Week",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(recipesUistate.value.recipes.size) { recipe ->
                    TopRecipes(
                        recipe = recipesUistate.value.recipes[recipe],
                        onItemClick = {
                            mainViewModel.onEvent(
                                MainEvent.AddToFavorites(recipesUistate.value.recipes[recipe])
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TopRecipes(
    recipe: ModelResult,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    // Image at the start (left)
                    AsyncImage(
                        model = recipe.image,
                        contentDescription = recipe.title,
                        modifier = Modifier
                            .size(160.dp) // Set a fixed size for the image
                            .clip(MaterialTheme.shapes.medium), // Optional: Add rounded corners
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(8.dp)) // Add some spacing between image and text

                    // Text descriptions
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp),
                        verticalArrangement = Arrangement.Center // Center the text vertically
                    ) {
                        Text(
                            text = recipe.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        IconButton(onClick = onItemClick) {
                            Icon(
                                Icons.Default.Favorite,
                                contentDescription = "Add to Favorites",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
    Spacer(modifier = Modifier.height(8.dp))
}

@Preview
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController)
}