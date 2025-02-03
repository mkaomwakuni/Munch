package com.est.munchy.utils

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerRecipeCardItem() {
    val transition = rememberInfiniteTransition(label = "")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    val shimmerColorShades = listOf(
        Color.LightGray.copy(0.9f),
        Color.LightGray.copy(0.4f),
        Color.LightGray.copy(0.9f)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = shimmerColorShades,
                            start = Offset(translateAnim.value - 1000f, 0f),
                            end = Offset(translateAnim.value, 0f)
                        )
                    )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(20.dp)
                    .padding(horizontal = 8.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = shimmerColorShades,
                            start = Offset(translateAnim.value - 1000f, 0f),
                            end = Offset(translateAnim.value, 0f)
                        )
                    )
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
@Composable
fun ShimmerLazyRowItem() {
    val transition = rememberInfiniteTransition(label = "")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    val shimmerColorShades = listOf(
        Color.LightGray.copy(0.9f),
        Color.LightGray.copy(0.4f),
        Color.LightGray.copy(0.9f)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // Simulate 3 items in a LazyRow
        repeat(3) {
            Card(
                modifier = Modifier
                    .width(150.dp)
                    .height(200.dp)
                    .padding(end = 8.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.linearGradient(
                                colors = shimmerColorShades,
                                start = Offset(translateAnim.value - 1000f, 0f),
                                end = Offset(translateAnim.value, 0f)
                            )
                        )
                ) {
                    // Simulate an image placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = shimmerColorShades,
                                    start = Offset(translateAnim.value - 1000f, 0f),
                                    end = Offset(translateAnim.value, 0f)
                                )
                            )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Simulate a title placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(16.dp)
                            .padding(horizontal = 8.dp)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = shimmerColorShades,
                                    start = Offset(translateAnim.value - 1000f, 0f),
                                    end = Offset(translateAnim.value, 0f)
                                )
                            )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Simulate a subtitle placeholder
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(12.dp)
                            .padding(horizontal = 8.dp)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = shimmerColorShades,
                                    start = Offset(translateAnim.value - 1000f, 0f),
                                    end = Offset(translateAnim.value, 0f)
                                )
                            )
                    )
                }
            }
        }
    }
}