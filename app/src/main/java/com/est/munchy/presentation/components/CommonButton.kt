package com.est.munchy.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
fun CommonButton(text: String, onClick: () -> Unit){
Button(
    onClick = { onClick },
    modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .height(60.dp)
        .clip(RoundedCornerShape(10.dp)),
    shape = RectangleShape,
    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
    ) {
    Text(
        text = text,
        color = Color.White
        )
    }
}