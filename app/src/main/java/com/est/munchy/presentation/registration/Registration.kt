package com.est.munchy.presentation.registration

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import com.est.munchy.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.est.munchy.presentation.components.CommonButton
import com.est.munchy.presentation.components.OutlinedButtonComposable
import com.est.munchy.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(30.dp))

        // Welcome Back text
        Text(
            text = "Welcome Back \uD83D\uDC4B",
            fontSize = 24.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
        )
        Text(
            text = "Sign to your account",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Email field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            placeholder = { Text("Your email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            placeholder = { Text("Your password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        if (passwordVisible) painterResource(R.drawable.visibility) else painterResource(R.drawable.disabled),
                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Forgot Password
        TextButton(
            onClick = { /* Handle forgot password */  },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Forgot Password?", color = Color(0xFF4CAF50))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Login button
        CommonButton(
            "Login",
            onClick = {
                navController.navigate(route = Routes.HomeScreen.route)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Sign Up text
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            TextButton(onClick = { /* Handle sign up navigation */ }){
                Text("Don't have an account? ")
            }
            Spacer(modifier = Modifier.width(10.dp))
            TextButton(onClick = { navController.navigate(route = Routes.SignUpScreen.route) }) {
                Text("Sign Up", color = Color(0xFF4CAF50))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Or with text
        Text(
            "Or with",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Google Sign In button
        OutlinedButtonComposable(
            text = "Sign in with Google",
            painter = painterResource(id = R.drawable.search),
            onClick = {

            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Apple Sign In button
        OutlinedButtonComposable(
            text = "Sign in with Apple",
            painter = painterResource(id = R.drawable.apple),
            onClick = {

            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview1(){
    val navController = rememberNavController()
    LoginScreen(navController)
}