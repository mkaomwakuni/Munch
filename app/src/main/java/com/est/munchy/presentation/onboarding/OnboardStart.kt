package com.est.munchy.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.est.munchy.R
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.est.munchy.presentation.components.CommonButton

@Composable
fun OnboardStart(navController: NavController) {
    OnboardingScreen(
        title = "All your favourite Dishes",
        subtitle = "Order your favourite menu with easy,\nOn demand delivery",
        mainRes = R.drawable.plate,
        smallRes = List(4) { R.drawable.plate },
        currentPage = 1,
        totalPages = 2
    )
}

@Composable
fun OnboardingScreen(
    title: String,
    subtitle: String,
    mainRes: Int,
    smallRes: List<Int>,
    currentPage: Int,
    totalPages: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize( )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AppIcon()
        Spacer(modifier = Modifier.height(25.dp))
        MainImage(mainRes, smallRes)
        Spacer(modifier = Modifier.height(30.dp))
        OnboardingText(title, subtitle)
        Spacer(modifier = Modifier.height(40.dp))
        PageIndicators(currentPage, totalPages)
        Spacer(modifier = Modifier.height(40.dp))
        OnboardingButtons()
    }
}

@Composable
fun AppIcon() {
    Image(
        painter = painterResource(id = R.drawable.mushroom),
        contentDescription = "App Icon",
        modifier = Modifier.size(60.dp)
    )
}

@Composable
fun MainImage(mainRes: Int, smallRes: List<Int>) {
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
fun OnboardingText(title: String, subtitle: String) {
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
fun PageIndicators(currentPage: Int, totalPages: Int) {
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
fun OnboardingButtons() {
    val buttonShape = RoundedCornerShape(10.dp)
    val buttonModifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
        .background(shape = buttonShape, color = Color.LightGray)

    CommonButton  (
        "Continue",
        onClick = { /* TODO: Implement continue action */ }
    )
    Spacer(modifier = Modifier.height(8.dp))
    CommonButton (
        "Sign In",
        onClick = { /* TODO: Implement sign in action */ }
    )
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    val navController = rememberNavController()
    OnboardStart(navController)
}