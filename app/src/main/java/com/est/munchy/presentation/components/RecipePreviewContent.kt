package com.est.munchy.presentation.components
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.est.munchy.domain.model.ModelResult

@Composable
fun RecipePreviewContent(
    recipe: ModelResult,
    onViewFullRecipe: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Recipe Image
        AsyncImage(
            model = recipe.image,
            contentDescription = "Recipe Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Recipe Title
        Text(
            text = recipe.title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Quick Info
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Ready in ${recipe.readyInMinutes} mins")
            Text("Liked by ${recipe.aggregateLikes} people")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // View Full Recipe Button
        Button(
            onClick = onViewFullRecipe,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View Full Recipe")
        }
    }
}