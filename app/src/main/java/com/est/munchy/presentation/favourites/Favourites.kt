package com.est.munchy.presentation.favourites


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.est.munchy.R
import com.est.munchy.domain.model.ModelResult
import com.est.munchy.presentation.navigation.BottomNavigation
import com.est.munchy.presentation.navigation.Routes
import com.est.munchy.viewModels.MainViewModel
import com.est.munchy.viewModels.events.MainEvent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun FavouritesScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val favoritesRecipes = viewModel.uiState.collectAsState().value.favoriteRecipes
    var showDeleteDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        bottomBar = { BottomNavigation(navController) },
        snackbarHost = { SnackbarHost(snackBarHostState) },
        contentWindowInsets = WindowInsets.navigationBars
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column {
                // Top Image with Fade Effect
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    // Image
                    Image(
                        painter = painterResource(id = R.drawable.food),
                        contentDescription = "Header Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black)
                    )

                    // Gradient Overlay
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        MaterialTheme.colorScheme.background
                                    )
                                )
                            )
                    )

                    // TopAppBar overlaid on the image
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 80.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Favourites",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2E7D32),
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Text(
                                text = "A Collection of Saved Recipes",
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }

                        IconButton(
                            onClick = { showDeleteDialog = true }
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = Color.Red
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Recipes Grid
                when {
                    favoritesRecipes.isEmpty() -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                modifier = Modifier.size(120.dp),
                                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                            )
                            Text("No Recipes", style = MaterialTheme.typography.headlineSmall)
                        }
                    }

                    else -> {
                        LazyVerticalStaggeredGrid(
                            columns = StaggeredGridCells.Adaptive(150.dp),
                            verticalItemSpacing = 8.dp,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(8.dp)
                        ) {
                            itemsIndexed(favoritesRecipes, key = { _, item -> item.id }) { index, recipe ->
                                val randomHeight = remember(index) { maxOf(250.dp, (200 + (index % 5) * 30).dp) }
                                FavoriteRecipeCard(
                                    recipe = recipe.result,
                                    height = randomHeight,
                                    index = index,
                                    onDeleteClick = {
                                        scope.launch {
                                            viewModel.onEvent(MainEvent.RemoveFromFavorites(recipe))
                                            snackBarHostState.showSnackbar(
                                                message = "Recipe Deleted",
                                                actionLabel = "Undo",
                                                duration = SnackbarDuration.Short
                                            ).let { result ->
                                                if (result == SnackbarResult.ActionPerformed) {
                                                    viewModel.onEvent(
                                                        MainEvent.AddToFavorites(
                                                            recipe.result
                                                        )
                                                    )
                                                }
                                            }
                                        }
                                    },
                                    onRecipeClick = {
                                        navController.navigate(Routes.RecipeScreen.route + "/${recipe.result.recipeId}")
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete All Favorites") },
            text = { Text("Are you sure you want to delete all favorite recipes?") },
            confirmButton = {
                TextButton(onClick = {
                    scope.launch {
                        favoritesRecipes.forEach {
                            viewModel.onEvent(
                                MainEvent.RemoveFromFavorites(
                                    it
                                )
                            )
                        }
                        snackBarHostState.showSnackbar("All recipes removed")
                    }
                    showDeleteDialog = false
                }) { Text("Delete") }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Cancel") }
            }
        )
    }
}


@Composable
fun FavoriteRecipeCard(
    recipe: ModelResult,
    height: Dp,
    index: Int,
    onDeleteClick: () -> Unit,
    onRecipeClick: () -> Unit
) {
    Card(
        onClick = onRecipeClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .padding(
                top = (index % 3 * 10).dp, // Slight overlap for staggered effect
                bottom = 16.dp // Minimum padding at the bottom to prevent excessive overlap
            )
            .offset(y = if (index > 2) (-20).dp else 0.dp) // Slight offset for cascading effect
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = recipe.image,
                contentDescription = recipe.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Gradient Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.4f)),
                            startY = 0.6f
                        )
                    )
            )

            // Content
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            ) {
                Text(
                    text = recipe.title,
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}