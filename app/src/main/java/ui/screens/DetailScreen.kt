package com.example.almatyplaces.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.almatyplaces.ui.PlacesUiState
import com.example.almatyplaces.ui.PlacesViewModel
import com.example.almatyplaces.ui.components.EmptyState
import com.example.almatyplaces.ui.components.PlaceTopBar
import com.example.almatyplaces.ui.preview.previewContentState
import com.example.almatyplaces.ui.theme.AlmatyPlacesTheme
import com.example.almatyplaces.ui.theme.Spacing

// ROUTE: соединяет ViewModel и экран. id места приходит из навигации.
@Composable
fun DetailRoute(
    placeId: Int,
    viewModel: PlacesViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DetailScreen(
        uiState = uiState,
        placeId = placeId,
        onFavoriteClick = { viewModel.onFavoriteClick(placeId) },
        onBack = onBack
    )
}

// SCREEN: stateless. Место берём из uiState.placesById, это просто доступ по ключу.
@Composable
fun DetailScreen(
    uiState: PlacesUiState,
    placeId: Int,
    onFavoriteClick: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val place = uiState.placesById[placeId]
    val isFavorite = placeId in uiState.favoriteIds

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
                    onClick = onFavoriteClick,
                    modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null
                    )
                    Spacer(Modifier.width(Spacing.s))
                    Text(if (isFavorite) "В избранном" else "В избранное")
                }
                Spacer(Modifier.height(Spacing.l))
                OutlinedButton(
                    onClick = onBack,
                    modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)
                ) { Text("Назад к списку") }
            }
        }
    }
}

@Preview(name = "Place", showBackground = true)
@Composable
private fun DetailScreenPreview() {
    AlmatyPlacesTheme {
        DetailScreen(previewContentState, placeId = 1, onFavoriteClick = {}, onBack = {})
    }
}

@Preview(name = "Not found", showBackground = true)
@Composable
private fun DetailScreenNotFoundPreview() {
    AlmatyPlacesTheme {
        DetailScreen(previewContentState, placeId = 999, onFavoriteClick = {}, onBack = {})
    }
}
