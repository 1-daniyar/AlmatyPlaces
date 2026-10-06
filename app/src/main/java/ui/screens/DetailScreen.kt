package com.example.almatyplaces.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.almatyplaces.data.places
import com.example.almatyplaces.ui.components.EmptyState
import com.example.almatyplaces.ui.components.PlaceTopBar
import com.example.almatyplaces.ui.theme.AlmatyPlacesTheme
import com.example.almatyplaces.ui.theme.Spacing

@Composable
fun DetailScreen(
    placeId: Int,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val place = places.find { it.id == placeId }

    Scaffold(
        modifier = modifier,
        topBar = { PlaceTopBar(title = place?.name ?: "Место", onBack = onBack) }
    ) { padding ->
        if (place == null) {
            EmptyState("Место не найдено", "Вернитесь к списку", Modifier.padding(padding))
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(Spacing.m)
            ) {
                Image(
                    painter = painterResource(place.imageRes),
                    contentDescription = "Фото: ${place.name}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(Spacing.m))
                )
                Spacer(Modifier.height(Spacing.m))
                Text(place.name, style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(Spacing.s))
                Row {
                    Text(
                        place.category,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(" · ★ ${place.rating}", style = MaterialTheme.typography.bodyLarge)
                }
                Spacer(Modifier.height(Spacing.m))
                Text(place.description, style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(Spacing.l))
                Button(
                    onClick = onToggleFavorite,
                    modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null
                    )
                    Spacer(Modifier.width(Spacing.s))
                    Text(if (isFavorite) "В избранном" else "В избранное")
                }
                Spacer(Modifier.height(Spacing.s))
                OutlinedButton(
                    onClick = onBack,
                    modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                ) { Text("Назад к списку") }
            }
        }
    }
}

@Preview
@Composable
private fun DetailScreenPreview() {
    AlmatyPlacesTheme { DetailScreen(1, false, {}, {}) }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DetailScreenDarkPreview() {
    AlmatyPlacesTheme { DetailScreen(1, true, {}, {}) }
}