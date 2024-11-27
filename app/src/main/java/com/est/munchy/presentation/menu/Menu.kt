package com.est.munchy.presentation.menu
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.est.munchy.R
import com.est.munchy.presentation.navigation.BottomNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Menu",
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
                "Our Food",
                style = MaterialTheme.typography.titleMedium,

                color = Color.Gray)
            Text(
                "Special For You",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D32)
            )
            Spacer(modifier = Modifier.height(16.dp))
            SearchBar(placeholder = "Search Your Menus")
            Spacer(modifier = Modifier.height(16.dp))
            CategoryTabs()
            Spacer(modifier = Modifier.height(16.dp))
            MenuGrid()
        }
    }
}

@Composable
fun SearchBar(placeholder: String = "Search on Munch") {
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
fun DeliveryCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2E7D32)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .height(120.dp)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "Delivery to Home",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Utama Street no. 14, Rumbai",
                    color = Color.White.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "2.4 km",
                    color = Color(0xFF2E7D32),
                    modifier = Modifier
                        .background(Color.White, RoundedCornerShape(6.dp))
                        .padding(4.dp)

                )
            }

            Icon(
                Icons.Default.ArrowForward,
                contentDescription = null,
                tint = Color.White)
        }
    }
}

@Composable
fun PromotionCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2E7D32).copy(alpha = 0.2f)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Chicken Teriyaki", fontWeight = FontWeight.Bold)
                Text("Discount 25%", color = Color(0xFF2E7D32))
                Button(
                    onClick = { /* Handle order */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Order Now", color = Color.White)
                }
            }
            Image(
                painter = painterResource(id = R.drawable.plate),
                contentDescription = "Chicken Teriyaki",
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun TopOfWeekItems() {
    LazyRow {
        items(3) { index ->
            Card(
                modifier = Modifier
                    .width(150.dp)
                    .padding(end = 16.dp)
            ) {
                Column {
                    Image(
                        painter = painterResource(id = R.drawable.plate),
                        contentDescription = "Food Item",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        contentScale = ContentScale.Crop
                    )
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("Food Item ${index + 1}", fontWeight = FontWeight.Bold)
                        Text("$${14.99 + index * 5}", color = Color(0xFF2E7D32))
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryTabs() {
    val categories = listOf("All", "Featured", "Top of Week", "Soup", "Seafood")
    var selectedCategory by remember { mutableStateOf("Featured") }

    ScrollableTabRow(
        selectedTabIndex = categories.indexOf(selectedCategory),
        containerColor = Color.Transparent,
        contentColor = Color(0xFF2E7D32),
        edgePadding = 0.dp
    ) {
        categories.forEach { category ->
            Tab(
                text = { Text(category) },
                selected = category == selectedCategory,
                onClick = { selectedCategory = category },
                selectedContentColor = Color(0xFF2E7D32),
                unselectedContentColor = Color.Gray
            )
        }
    }
}

@Composable
fun MenuGrid() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(4) { index ->
            Card(
                shape = RoundedCornerShape(8.dp)
            ) {
                Column {
                    Image(
                        painter = painterResource(id = R.drawable.plate),
                        contentDescription = "Menu Item",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        contentScale = ContentScale.Crop
                    )
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("Menu Item ${index + 1}", fontWeight = FontWeight.Bold)
                        Text("$${19.99 + index * 2.5}", color = Color(0xFF2E7D32))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MenuScreenPreview() {
    val navController = rememberNavController()
    MenuScreen(navController)
}

@Preview
@Composable
fun MenuScreenPreview2() {
    DeliveryCard()
}