package com.est.munchy.presentation.components

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.est.munchy.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(
                    "Location",
                    fontWeight = FontWeight.Bold
                ) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // Map placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.LightGray)
            ) {
                Text("Map Placeholder", modifier = Modifier.align(Alignment.Center))
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row (modifier = Modifier.padding(16.dp)) {
                Text(
                    "Detail Address",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(painterResource(R.drawable.track), contentDescription = "location")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Utama Street No.20", fontWeight = FontWeight.Bold)
                    Text("Dumbo Street No.20, Dumbo, New York 10001, United States of America", color = Color.Gray)
                    TextButton(onClick = { /* Handle address change */ }) {
                        Text("Change", color = Color(0xFF2E7D32))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Save Address As", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf("Home", "Office", "Others").forEach { label ->
                    OutlinedButton(
                        onClick = { /* Handle selection */ },
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color(0xFF2E7D32).copy(alpha = 0.2f),
                            contentColor = Color(0xFF2E7D32).copy(alpha = 0.8f))
                    ) {
                        Text(label)
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            CommonButton(
                "Confirmation",
                onClick = {

                }
            )
        }
    }
}

@Preview
@Composable
fun LocationScreenPreview() {
    val navController = rememberNavController()
    LocationScreen(navController)
}