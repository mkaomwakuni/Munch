package com.est.munchy.presentation.components
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.est.munchy.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailsBottomSheet(item: FoodItemData) {
  val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    BottomSheetScaffold(
        sheetContent = {
            Column(modifier = Modifier.padding(16.dp)) {
                Image(
                    painter = painterResource(id = item.imageRes),
                    contentDescription = item.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(item.name, style = MaterialTheme.typography.bodyMedium)
                        Text(item.category, color = Color.Gray)
                    }
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = "Favorite",
                        tint = Color.Red
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                QuantitySelector()
                Spacer(modifier = Modifier.height(16.dp))
                Text("Sause", style = MaterialTheme.typography.bodyMedium)
                SauceSelector()
                Spacer(modifier = Modifier.height(16.dp))
                Text("Add a Topping?", style = MaterialTheme.typography.bodyMedium)
                ToppingSelector()
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { /* Handle add to favourites */ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                ) {
                    Text("Add to Cart ($${item.price})", color = Color.White)
                }
            }
        },
        sheetPeekHeight = 64.dp,
        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
    ) {
        // Content of the screen behind the bottom sheet
    }
}

@Composable
fun QuantitySelector() {
    var quantity by remember { mutableStateOf(1) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { if (quantity > 1) quantity-- }) {
            Icon(painterResource(R.drawable.minus), contentDescription = "Decrease")
        }
        Text(quantity.toString(), modifier = Modifier.padding(horizontal = 8.dp))
        IconButton(onClick = { quantity++ }) {
            Icon(Icons.Default.Add, contentDescription = "Increase")
        }
    }
}

@Composable
fun SauceSelector() {
    var selectedSauce by remember { mutableStateOf("Teriyaki") }
    Column {
        listOf("Teriyaki", "Yakiniku").forEach { sauce ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = sauce == selectedSauce,
                    onClick = { selectedSauce = sauce },
                    colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF2E7D32))
                )
                Text(sauce)
                Spacer(modifier = Modifier.weight(1f))
                Text("$0")
            }
        }
    }
}

@Composable
fun ToppingSelector() {
    val toppings = listOf("Omelet" to 2, "Sausage" to 3, "Cheese" to 5)
    var selectedToppings by remember { mutableStateOf(setOf<String>()) }

    Column {
        toppings.forEach { (topping, price) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = topping in selectedToppings,
                    onCheckedChange = { checked ->
                        selectedToppings = if (checked) {
                            selectedToppings + topping
                        } else {
                            selectedToppings - topping
                        }
                    },
                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFF2E7D32))
                )
                Text(topping)
                Spacer(modifier = Modifier.weight(1f))
                Text("$$price")
            }
        }
    }
}

// Sample data classes
data class CartItemData(val name: String, val price: Double, val quantity: Int, val imageRes: Int)
data class FoodItemData(val name: String, val category: String, val price: Double, val imageRes: Int)

// Sample data
val sampleCartItems = listOf(
    CartItemData("Squid Sweet and Sour Salad", 19.99, 1, R.drawable.plate),
    CartItemData("Japan Hainanese Sashimi", 39.99, 1, R.drawable.plate),
    CartItemData("Black Pepper Beef Lumpia", 27.12, 1, R.drawable.plate)
)

@Preview
@Composable
fun ItemDetailsBottomSheetPreview() {

    val navController = rememberNavController()
    ItemDetailsBottomSheet(FoodItemData(
        "Squid Sweet and Sour Salad",
        "Salad",
        19.99,
        R.drawable.plate
    ))
}