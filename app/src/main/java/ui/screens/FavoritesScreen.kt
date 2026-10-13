package com.example.almatyplaces.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.almatyplaces.ui.PlacesUiState
import com.example.almatyplaces.ui.PlacesViewModel
import com.example.almatyplaces.ui.components.EmptyState
import com.example.almatyplaces.ui.components.ItemCard
import com.example.almatyplaces.ui.components.PlaceTopBar
import com.example.almatyplaces.ui.preview.previewContentState
import com.example.almatyplaces.ui.theme.AlmatyPlacesTheme
import com.example.almatyplaces.ui.theme.Spacing

// ROUTE: соединяет ViewModel и экран
@Composable
fun FavoritesRoute(
    viewModel: PlacesViewModel,
    onPlaceClick: (Int) -> Unit,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FavoritesScreen(
        uiState = uiState,
        onPlaceClick = onPlaceClick,
        onFavoriteClick = viewModel::onFavoriteClick,
        onBack = onBack
    )
}

// SCREEN: stateless, только рисует uiState.favoritePlaces
@Composable
fun FavoritesScreen(
    uiState: PlacesUiState,
    onPlaceClick: (Int) -> Unit,
    onFavoriteClick: (Int) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = { PlaceTopBar(title = "Избранное", onBack = onBack) }
    ) { padding ->
        if (uiState.favoritePlaces.isEmpty()) {
            EmptyState(
                "Пока ничего нет",
                "Нажмите на сердечко у места, чтобы добавить его сюда",
                Modifier.padding(padding)
            )
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding),
                contentPadding = PaddingValues(Spacing.m),
                verticalArrangement = Arrangement.spacedBy(Spacing.s)
            ) {
                items(uiState.favoritePlaces, key = { it.id }) { place ->
                    ItemCard(
                        place = place,
                        isFavorite = true,
                        onClick = { onPlaceClick(place.id) },
                        onFavoriteClick = { onFavoriteClick(place.id) }
                    )
                }
            }
        }
    }
}

@Preview(name = "With favorites", showBackground = true)
@Composable
private fun FavoritesScreenPreview() {
    AlmatyPlacesTheme {
        FavoritesScreen(previewContentState, onPlaceClick = {}, onFavoriteClick = {}, onBack = {})
    }
}

@Preview(name = "Empty", showBackground = true)
@Composable
private fun FavoritesScreenEmptyPreview() {
    AlmatyPlacesTheme {
        FavoritesScreen(
            previewContentState.copy(favoriteIds = emptySet(), favoritePlaces = emptyList()),
            onPlaceClick = {},
            onFavoriteClick = {},
            onBack = {}
        )
    }
}
