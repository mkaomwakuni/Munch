package com.est.munchy.presentation.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.est.munchy.presentation.components.CommonButton

@Composable
fun Verification(navController: NavController) {
    var code by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        Text(
            "Verification Email",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge)
        Text(

            "Please enter the code we just sent to email rfqyauxxx@gmail.com",
            style = MaterialTheme.typography.bodyLarge)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (i in 0 until 4) {
                OutlinedTextField(
                    value = if (code.length > i) code[i].toString() else "",
                    onValueChange = {
                        if (it.length <= 1) {
                            code = code.take(i) + it + code.drop(i + 1)
                        }
                    },
                    modifier = Modifier.weight(1f).padding(4.dp),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.labelLarge
                )
            }
        }

        TextButton(onClick = { /* Handle resend logic */ }) {
            Text("If you didn't receive a code? Resend")
        }

        CommonButton(
            "Continue",
            onClick = {

            }
        )

        // Number pad
        (1..9).chunked(3).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { number ->
                    TextButton(
                        onClick = { if (code.length < 4) code += number.toString() },
                        modifier = Modifier.size(64.dp)
                    ) {
                        Text(
                            number.toString(),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextButton(
                onClick = { /* Handle dot input */ },
                modifier = Modifier.size(64.dp)
            ) {
                Text(".", style = MaterialTheme.typography.labelMedium)
            }
            TextButton(
                onClick = { if (code.length < 4) code += "0" },
                modifier = Modifier.size(64.dp)
            ) {
                Text("0", style = MaterialTheme.typography.labelMedium)
            }
            IconButton(
                onClick = { if (code.isNotEmpty()) code = code.dropLast(1) },
                modifier = Modifier.size(64.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Delete")
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun Preview4() {
    val navController = rememberNavController()
    Verification(navController = navController)
}