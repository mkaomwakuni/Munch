package com.est.munchy.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun OutlinedButtonComposable(text: String, painter: Painter,onClick: () -> Unit) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
        .clip(RoundedCornerShape(10.dp)),
        shape = RectangleShape,
        onClick = onClick
    ) {
        Image(
            painter = painter,
            contentDescription = "icon",
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(8.dp))
        Text(text = text)
    }
}