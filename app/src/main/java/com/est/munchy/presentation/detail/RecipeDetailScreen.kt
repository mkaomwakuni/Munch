package com.est.munchy.presentation.detail
import android.accessibilityservice.GestureDescription
import android.annotation.SuppressLint
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key.Companion.I
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.est.munchy.R
import com.est.munchy.domain.model.Ingredients
import com.est.munchy.domain.model.ModelResult
import com.est.munchy.presentation.navigation.BottomNavigation
import com.est.munchy.utils.AppConstants
import com.est.munchy.viewModels.RecipeViewModel
import java.nio.file.WatchEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    navController: NavController,
    recipeId: Int,
    viewModel: RecipeViewModel = hiltViewModel()
) {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Recipe Details",
                        fontWeight = FontWeight.Bold
                    ) },
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
            Text(
                "Our Recipes",
                style = MaterialTheme.typography.titleMedium,

                color = Color.Gray)
            Text(
                "Special For You",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D32)
            )
            Spacer(modifier = Modifier.height(16.dp))
            SearchBar(
                value = "",
                onValueChange = { },
                placeholder = "Search Your Menus"
            )
            Spacer(modifier = Modifier.height(16.dp))
            CategoryTabs()
        }
    }
}

@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "Search on Munch") {
    TextField(
        value = "",
        onValueChange = { },
        placeholder = { Text(placeholder) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray.copy(alpha = 0.2f), RoundedCornerShape(14.dp)),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}



@Composable
fun CategoryTabs() {
    val tabs = listOf("Overview", "Ingredients", "Instructions")
    var selectedCategory by remember { mutableStateOf("Overview") }

    ScrollableTabRow(
        selectedTabIndex = tabs.indexOf(selectedCategory),
        containerColor = Color.Transparent,
        contentColor = Color(0xFF2E7D32),
        edgePadding = 0.dp
    ) {
        tabs.forEach { category ->
            Tab(
                text = { Text(category) },
                selected = category == selectedCategory,
                onClick = { selectedCategory = category },
                selectedContentColor = Color(0xFF2E7D32),
                unselectedContentColor = Color.Gray
            )
        }
    }
    when(selectedCategory) {
        "Overview" -> OverviewSection()
        "Ingredients" -> IngredientsSection(
            ingredients = 
        )
        "Instructions" -> InstructionsSection()
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun InstructionsSection(

) {

    var isLoading by remember { mutableStateOf(true) }
    var hasError by remember { mutableStateOf(false) }

    Column (modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()){
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        webViewClient = object: WebViewClient() {
                            override fun onPageFinished(
                                view: WebView?, url: String?) {
                                super.onPageFinished(view, url)
                            }

                            override fun onReceivedError(
                                view: WebView?,
                                errorCode: Int,
                                description: String?,
                                faillingUrl: String?,

                            ) {
                                super.onReceivedError(view, errorCode, description,faillingUrl)
                                hasError = true
                                isLoading = false
                            }
                        }
                        settings.apply {
                            javaScriptEnabled = true
                            loadWithOverviewMode = true
                            useWideViewPort = true
                        }
                    }
                },
                modifier = Modifier.fillMaxSize(),
                update = { webView->
                    webView.loadUrl()
                }
            )
            if (isLoading){
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(48.dp),
                    color = Color(0xFF2E7D32)
                )
            }
            if (hasError) {
                Column(modifier = Modifier
                    .align(Alignment.Center)
                    .padding(14.dp)) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Error",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Failed to load instructions",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            hasError = false
                            isLoading = true
                        }
                    ) {
                        Text("Retry")
                    }
                }
            }
        }
    }
}

@Composable
fun IngredientsSection(ingredients: List<Ingredients>?) {
    if (ingredients == null) return
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(ingredients.size) {
            IngredientItem(ingredients = ingredients[it])
        }
    }
}

@Composable
fun IngredientItem(ingredients: Ingredients) {
    Card (modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically) {
            //Image
            AsyncImage(
                model = "${AppConstants.BASE_IMAGE_URL}${ingredients.image}}",
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.plate),
                error = painterResource(id = R.drawable.ic_launcher_foreground)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column (modifier = Modifier.weight(1f)) {
                Text(
                    text = ingredients.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row (
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.fillMaxWidth()){
                    Text(
                        text = "${ingredients.amount} ${ingredients.unit}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = ingredients.original,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = ingredients.consistency,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = ingredients.original,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun OverviewSection(recipe: ModelResult? = null) {
    LazyColumn (
        modifier = Modifier
        .fillMaxWidth()
        .padding()) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                AsyncImage(
                    model = recipe?.image,
                    contentDescription = "Recipe Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = recipe?.title.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    RowItem(
                        icon = Icons.Default.Favorite,
                        value = recipe?.aggregateLikes.toString(),
                        label = "Likes"
                    )
                    RowItem(
                        icon = Icons.Default.CheckCircle,
                        value = recipe?.readyInMinutes.toString(),
                        label = "Minutes"
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    DietaryTag(
                        icon = Icons.Default.CheckCircle,
                        text = "Vegetarian",
                        isActive = recipe?.vegetarian == true
                    )
                }
                item {
                    DietaryTag(
                        icon = Icons.Default.CheckCircle,
                        text = "Vegan",
                        isActive = recipe?.vegan == true
                    )
                }
                item {
                    DietaryTag(
                        icon = Icons.Default.CheckCircle,
                        text = "Gluten Free",
                        isActive = recipe?.glutenFree == true
                    )
                }
                item {
                    DietaryTag(
                        icon = Icons.Default.CheckCircle,
                        text = "Dairy Free",
                        isActive = recipe?.dairyFree == true
                    )
                }
                item {
                    DietaryTag(
                        icon = Icons.Default.CheckCircle,
                        text = "Healthy",
                        isActive = recipe?.veryHealthy == true
                    )
                }
                item {
                    DietaryTag(
                        icon = Icons.Default.CheckCircle,
                        text = "Cheap",
                        isActive = recipe?.cheap == true
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            //Summary
            Text(
                text = HtmlCompat.fromHtml(
                    recipe?.summary.toString(),
                    HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
                ,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun RowItem(
    icon: ImageVector,
    value: String,
    label: String
){
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center){
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(4.dp))
        Column {
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun DietaryTag(
    icon: ImageVector,
    text: String,
    isActive: Boolean
){
    Surface (
        shape = RoundedCornerShape(8.dp),
        color = if (isActive){
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surfaceVariant
        }
    ){
        Row(modifier = Modifier
            .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isActive){
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isActive){
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        }
    }
}

@Preview
@Composable
fun MenuScreenPreview() {
    val navController = rememberNavController()
    RecipeDetailScreen(navController = navController, recipeId = 1)
}