package com.est.munchy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.est.munchy.presentation.navigation.NavGraph
import com.est.munchy.ui.theme.MunchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var darkTheme by remember {
                mutableStateOf(false)
            }

            fun onToggleTheme(){
                darkTheme = !darkTheme
            }
            MunchTheme (darkTheme = darkTheme) {
                val navController = rememberNavController()
                NavGraph(navController = navController,onToggleTheme = {onToggleTheme()}, isDarkTheme = darkTheme )
            }
        }
    }
}