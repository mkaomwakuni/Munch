package com.est.munchy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
            MunchTheme () {
                val navController = rememberNavController()
                NavGraph(navController = navController )
            }
        }
    }
}