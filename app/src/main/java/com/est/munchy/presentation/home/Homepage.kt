package com.est.munchy.presentation.home

import android.R.attr.contentDescription
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.est.munchy.domain.model.ModelResult
import com.est.munchy.presentation.components.RecipePreviewContent
import com.est.munchy.presentation.navigation.BottomNavigation
import com.est.munchy.presentation.navigation.Routes
import com.est.munchy.viewModels.MainViewModel
import com.est.munchy.viewModels.RecipeViewModel
import com.est.munchy.viewModels.events.MainEvent
import okhttp3.Route
import timber.log.Timber


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    recipesViewModel: RecipeViewModel = hiltViewModel()
) {
    val uiState = mainViewModel.uiState.collectAsState().value
    var selectedRecipe by remember { mutableStateOf<ModelResult?>(null) }
    val modalSheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        mainViewModel.onEvent(MainEvent.RefreshRecipes)
    }

    if (showBottomSheet && selectedRecipe != null){
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
                selectedRecipe = null
            },
            sheetState = modalSheetState
        ) {
            RecipePreviewContent(
                recipe = selectedRecipe!!,
                onViewFullRecipe = {
                    showBottomSheet = false
                    navController.navigate(Routes.RecipeScreen.route + "/${selectedRecipe!!.recipeId}")
                }
            )
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Munch",
                        fontWeight = FontWeight.Bold
                    )
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
            // Welcome Text
            Text(
                "Find Best Recipe",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D32)
            )
            Text(
                "For Your Cooking",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Search Bar
            SearchBar(
                value = uiState.searchQuery,
                onValueChange = { query ->
                    mainViewModel.onEvent(MainEvent.SearchRecipes(query))
                },
                placeholder = "Search recipes..."
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Content
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                uiState.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = uiState.error,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                uiState.recipes.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No recipes found",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(uiState.recipes) { recipe ->
                            RecipeCard(
                                recipe = recipe.recipeId.let { recipe },
                                onFavoriteClick = {
                                    mainViewModel.onEvent(MainEvent.AddToFavorites(recipe))
                                },
                                onItemClick = {
                                    navController.navigate("${Routes.RecipeScreen.route}/${recipe.recipeId}")
                                    showBottomSheet = true
                                    Timber.tag("Recipe").d("Navigating with recipe: $recipe")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeCard(
    recipe: ModelResult,
    onFavoriteClick: () -> Unit,
    onItemClick: () -> Unit
) {
    // Debug log to verify the image URL
    Timber.tag("RecipeCard").d("Recipe image URL: ${recipe.image}")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        onClick = onItemClick
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Recipe Image
            AsyncImage(
                model = recipe.image,
                contentDescription = "Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
                onLoading = {
                    Timber.tag("RecipeCard").d("Loading image: ${recipe.image}")
                },
                onSuccess = {
                    Timber.tag("RecipeCard").d("Successfully loaded image: ${recipe.image}")
                },
                onError = {
                    Timber.tag("RecipeCard")
                        .e(it.result.throwable, "Error loading image: ${recipe.image}")
                }
            )

            // Overlay gradient and content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Recipe Title
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                // Recipe Info
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Recipe details
                    Column {
                        Text(
                            text = "${recipe.readyInMinutes} mins",
                            color = Color.White,
                            fontSize = 14.sp
                        )
                        if (recipe.sourceName != null) {
                            Text(
                                text = "By ${recipe.sourceName}",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                        }
                    }

                    // Favorite button
                    IconButton(
                        onClick = onFavoriteClick
                    ) {
                        Icon(
                            Icons.Outlined.FavoriteBorder, // Replace with appropriate favorite icon
                            contentDescription = "Add to favorites",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        singleLine = true
    )
}