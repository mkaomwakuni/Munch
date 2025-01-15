package com.est.munchy.presentation.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.est.munchy.R
import com.est.munchy.presentation.components.CommonButton
import com.est.munchy.presentation.components.OutlinedButtonComposable
import com.est.munchy.presentation.navigation.Routes

@Composable
fun SignUpFlow() {
    val navController = rememberNavController( )

    NavHost(navController = navController, startDestination = "signup") {
        composable("signup") { SignUpScreen(navController) }
        composable("verification") { Verification(navController) }
        composable("forgot password") { ForgotPasswordScreen(navController) }
        composable("congratulation") { CongratulationScreen(navController) }
    }
}

@Composable
fun SignUpScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 30.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            "Sign Up",
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            "Create account and choose favorite menu",
            style = MaterialTheme.typography.bodyLarge)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            placeholder = { Text("Your name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            placeholder = { Text("Your email") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            placeholder = { Text("Your password") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        if (passwordVisible) painterResource(R.drawable.visibility) else painterResource(R.drawable.disabled),
                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        CommonButton(
            "Register",
            onClick = {

            }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            TextButton(onClick = { /* Handle sign up navigation */ }){
                Text("Have an account? ")
            }
            Spacer(modifier = Modifier.width(10.dp))
            TextButton(onClick = {navController.navigate(route = Routes.MenuScreen.route)}) {
                Text("Sign in", color = Color(0xFF4CAF50))
            }
        }

        // Or with text
        Text(
            "Or with",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Google Sign In button
        OutlinedButtonComposable(
            text = "Sign up with Google",
            painter = painterResource(id = R.drawable.search),
            onClick = {

            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Apple Sign In button
        OutlinedButtonComposable(
            text = "Sign up with Apple",
            painter = painterResource(id = R.drawable.apple),
            onClick = {

            }
        )

        Spacer(modifier = Modifier.weight(1f))
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically){
            Checkbox(
               checked = false,
               onCheckedChange = {}
            )
            Text(
            "By clicking Register, you agree to our Terms and Data Policy.",
            style = MaterialTheme.typography.bodySmall
        )
        }
    }
}

@Preview
@Composable
fun Preview3(){
    SignUpFlow()
}