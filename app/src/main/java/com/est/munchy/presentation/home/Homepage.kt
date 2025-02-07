package com.est.munchy.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.est.munchy.domain.model.ModelResult
import com.est.munchy.presentation.components.RecipePreviewContent
import com.est.munchy.presentation.navigation.BottomNavigation
import com.est.munchy.presentation.navigation.Routes
import com.est.munchy.utils.ShimmerRecipeCardItem
import com.est.munchy.viewModels.MainViewModel
import com.est.munchy.viewModels.events.MainEvent
import timber.log.Timber


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
) {
    // Collect the UI state from the ViewModel
    val uiState by mainViewModel.uiState.collectAsState()
    val netState by mainViewModel.netState.collectAsState()
    // State hoisting for selected recipe and bottom sheet
    var selectedRecipe by remember { mutableStateOf<ModelResult?>(null) }
    val modalSheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }

    // Filter recipes based on the UI state
    val filteredRecipes = remember ( uiState.recipes ) {
        uiState.recipes.filter {
            it.title.isNotEmpty()
        }
    }

    // Handle network state changes
    LaunchedEffect(netState.isNetworkAvailable, netState.networkMessage) {
        // Handle network state changes
        if (!netState.isNetworkAvailable && uiState.recipes.isEmpty()) {
            snackBarHostState.showSnackbar(
                message = "Connection Lost.",
                duration = SnackbarDuration.Short
            )
        }

        netState.networkMessage?.let { message ->
            snackBarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short,
                withDismissAction = true
            )
            mainViewModel.onEvent(MainEvent.ClearError)
            mainViewModel.onEvent(MainEvent.RefreshRecipes)
        }
    }

    if (showBottomSheet && selectedRecipe != null){
        ModalBottomSheet(
            dragHandle = null,
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
        contentWindowInsets = WindowInsets.systemBars.exclude(WindowInsets.navigationBars),
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
        bottomBar = { BottomNavigation(navController) },
        snackbarHost = {
            Box(modifier = Modifier.fillMaxWidth()) {
                SnackbarHost(
                    hostState = snackBarHostState,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(10.dp)
                ){ data ->
                    Snackbar(
                        modifier = Modifier
                            .height(30.dp)
                            .fillMaxWidth(),
                        containerColor = when {
                            !netState.isNetworkAvailable -> Color.Red
                            netState.networkMessage == "Back Online" -> Color.Green
                            else -> MaterialTheme.colorScheme.inverseSurface
                        },
                        contentColor = Color.White
                    ){
                        Text(
                            text = data.visuals.message,
                            fontSize = 12.sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(
                    top = padding.calculateTopPadding(),
                    start = padding.calculateStartPadding(LayoutDirection.Ltr),
                    end = padding.calculateEndPadding(LayoutDirection.Ltr)
                )
                .padding(12.dp)
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
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(5) {
                            ShimmerRecipeCardItem()
                        }
                    }
                }
                uiState.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = uiState.error!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                filteredRecipes.isEmpty() -> {
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
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = filteredRecipes,
                            key = {it.recipeId}) { recipe ->

                            RecipeCard(
                                recipe = recipe.recipeId.let { recipe },
                                onFavoriteClick = {
                                    mainViewModel.onEvent(MainEvent.AddToFavorites(recipe))
                                    Timber.tag("Recipe").d("Adding to favorites: $recipe")
                                },
                                onItemClick = {
                                    selectedRecipe = recipe
                                    showBottomSheet = true
                                    Timber.tag("Recipe").d("Navigating with recipe: $recipe")
                                },
                                modifier = Modifier.animateItem())
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
    modifier: Modifier = Modifier,
    recipe: ModelResult,
    onFavoriteClick: () -> Unit,
    onItemClick: () -> Unit
) {
    Timber.tag("RecipeCard").d("Recipe image URL: ${recipe.image}")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(0.dp))
            .height(240.dp),
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
                            Icons.Outlined.FavoriteBorder,
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