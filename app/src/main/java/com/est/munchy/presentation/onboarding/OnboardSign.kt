package com.est.munchy.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.est.munchy.R

@Composable
fun OnboardSign() {
    Onboarding(
        title = "Get delivery at your doorStep",
        subtitle = "Your ready order will be delivered\n quickly by our courier",
        mainRes = R.drawable.delivery,
        smallRes = List(4) { R.drawable.plate },
        currentPage = 1,
        totalPages = 2
    )
}

@Composable
fun Onboarding(
    title: String,
    subtitle: String,
    mainRes: Int,
    smallRes: List<Int>,
    currentPage: Int,
    totalPages: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AppIcon()
        Spacer(modifier = Modifier.height(25.dp))
        MainImage(mainRes, smallRes)
        Spacer(modifier = Modifier.height(30.dp))
        OnboardText(title, subtitle)
        Spacer(modifier = Modifier.height(40.dp))
        Indicators(currentPage, totalPages)
        Spacer(modifier = Modifier.height(40.dp))
        OnboardButtons()
    }
}

@Composable
fun Icon() {
    Image(
        painter = painterResource(id = R.drawable.mushroom),
        contentDescription = "App Icon",
        modifier = Modifier.size(60.dp)
    )
}

@Composable
fun MainCover(mainRes: Int, smallRes: List<Int>) {
    Box(modifier = Modifier.size(300.dp)) {
        Image(
            painter = painterResource(id = mainRes),
            contentDescription = "Main Image",
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        smallRes.forEachIndexed { index, imageRes ->
            val size = when (index) {
                0 -> 90.dp  // Top-start: slightly larger
                1 -> 55.dp  // Top-end: slightly smaller
                else -> 40.dp  // Bottom-end: smallest
            }
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Small Image $index",
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape)
                    .align(
                        when (index) {
                            0 -> Alignment.TopStart
                            1 -> Alignment.TopEnd
                            else -> Alignment.BottomEnd
                        }
                    )
                    .offset(
                        x = when (index) {
                            0 -> (-8).dp
                            1 -> 8.dp
                            else -> 8.dp
                        },
                        y = when (index) {
                            0 -> (-8).dp
                            1 -> (-8).dp
                            else -> 8.dp
                        }
                    ),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun OnboardText(title: String, subtitle: String) {
    Text(
        text = title,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = subtitle,
        textAlign = TextAlign.Center
    )
}

@Composable
fun Indicators(currentPage: Int, totalPages: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(totalPages) { index ->
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(if (index == currentPage) Color(0xFFFF9800) else Color.LightGray)
            )
            if (index < totalPages - 1) {
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Composable
fun OnboardButtons() {
    val buttonShape = RoundedCornerShape(10.dp)
    val buttonModifier = Modifier
        .fillMaxWidth()
        .height(56.dp)

    TextButton (
        onClick = { /* TODO: Implement continue action */ },
        modifier = buttonModifier,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
    ) {
        Text("Get Started", color = Color.White)
    }
    Spacer(modifier = Modifier.height(8.dp))
    TextButton (
        onClick = { /* TODO: Implement sign in action */ },
        modifier = buttonModifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFF9800).copy(alpha = 0.2f),
            contentColor = Color(0xFFFF9800)
        )
    ) {
        Text("Sign in")
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview1() {
    OnboardSign()
}