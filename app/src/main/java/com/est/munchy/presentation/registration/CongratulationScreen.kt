package com.est.munchy.presentation.registration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.est.munchy.R

@Composable
fun CongratulationScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.plate),
            contentDescription = "Congratulati ons",
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Congratulation!", style = MaterialTheme.typography.labelSmall)
        Text("Your account is complete, please enjoy the best detail from us.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(32.dp))
        TextButton (
            onClick = { /* Handle get started action */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Get Started")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview5() {
    val navController = rememberNavController()
    CongratulationScreen(navController = navController)
}