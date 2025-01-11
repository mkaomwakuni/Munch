package com.est.munchy.presentation.onboarding

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import com.est.munchy.R
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.math.sin

@Composable
fun MunchSteamLayout(navController: NavController) {
    val orangeBackground = Color(0xFFFF9800)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(orangeBackground),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 8.dp)
            ) {
                SteamingIcon(
                    modifier = Modifier.fillMaxSize()
                )
                Image(
                    painter = painterResource(id = R.drawable.mushroom),
                    contentDescription = "logo",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Text(
                text = "Munch",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun SteamingIcon(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val steamProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Box(modifier = modifier) {

        // animation
        Canvas(modifier = Modifier.matchParentSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            val steamPath = Path().apply {
                moveTo(canvasWidth * 0.4f, canvasHeight * 0.2f)

                val waveHeight = canvasHeight * 0.1f
                val waveWidth = canvasWidth * 0.2f

                for (i in 0..2) {
                    val x = canvasWidth * 0.4f + waveWidth * i
                    val y = canvasHeight * 0.2f - waveHeight * steamProgress - waveHeight * i
                    val controlX1 = x - waveWidth * 0.5f
                    val controlX2 = x + waveWidth * 0.5f
                    val controlY = y - waveHeight * 0.5f * sin((steamProgress + i * 0.3f) * 2 * Math.PI).toFloat()

                    quadraticTo(controlX1, controlY, x, y)
                    quadraticTo(controlX2, controlY, x + waveWidth, y)
                }
            }

            drawPath(
                path = steamPath,
                color = Color.White.copy(alpha = 0.7f - steamProgress * 0.5f)
            )
        }
    }
}

//@Preview
//@Composable
//fun PreviewMunchSteamLayout() {
//    MunchSteamLayout()
//}