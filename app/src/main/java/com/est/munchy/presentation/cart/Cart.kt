package com.est.munchy.presentation.cart
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.est.munchy.R
import com.est.munchy.presentation.components.CartItemData
import com.est.munchy.presentation.components.CommonButton
import com.est.munchy.presentation.components.sampleCartItems


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCartScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(
                    "My Cart",
                    fontWeight = FontWeight.Bold
                ) },
            )
        },
        bottomBar = {
            CommonButton(
                "Place an order",
                onClick = {

                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Your Order (3)",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(sampleCartItems) { item ->
                    CartItem(item)
                    HorizontalDivider()
                }
            }
        }
    }
}
@Composable
fun CartItem(item: CartItemData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = item.imageRes),
            contentDescription = item.name,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(item.name, fontWeight = FontWeight.Bold)
                Text("$${item.price}", color = Color(0xFF2E7D32))
            }
            IconButton(onClick = { /* Decrease quantity */ }) {
                Icon(painterResource(R.drawable.minus), contentDescription = "Decrease")
            }
            Text(item.quantity.toString())
            IconButton(
                onClick = { /* Increase quantity */ }) {
                Icon(
                    Icons.Default.Add,
                    tint = Color(0xFF2E7D32),
                    contentDescription = "Increase"
                )
            }
        }
    }
}
@Preview
@Composable
fun CartItemPreview() {
    val navController = rememberNavController()
    MyCartScreen(navController = NavController(LocalContext.current))
}