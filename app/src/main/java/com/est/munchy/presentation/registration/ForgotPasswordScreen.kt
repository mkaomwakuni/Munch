package com.est.munchy.presentation.registration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.est.munchy.R

@Composable
fun ForgotPasswordScreen(navController: NavController) {
    var selectedOption by remember { mutableStateOf("Email") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        Text("Forgot Password", style = MaterialTheme.typography.labelSmall)
        Text("Select which contact details should we us e to reset your password", style = MaterialTheme.typography.labelSmall)

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painterResource(R.drawable.visibility), contentDescription = "Email")
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text("Email", style = MaterialTheme.typography.labelLarge)
                    Text("Send to your email", style = MaterialTheme.typography.labelSmall)
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painterResource(R.drawable.visibility), contentDescription = "Phone")
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text("Phone Number", style = MaterialTheme.typography.labelLarge)
                    Text("Send to your phone number", style = MaterialTheme.typography.labelSmall)
                }
            }
        }

        TextButton(
            onClick = { /* Handle password reset */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continue")
        }
    }
}
@Preview(showBackground = true)
@Composable
fun Preview6() {
    val navController = rememberNavController()
    ForgotPasswordScreen(navController = navController)
}