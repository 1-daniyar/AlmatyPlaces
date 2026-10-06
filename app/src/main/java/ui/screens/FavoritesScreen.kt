package com.example.almatyplaces.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.almatyplaces.data.places
import com.example.almatyplaces.ui.components.EmptyState
import com.example.almatyplaces.ui.components.ItemCard
import com.example.almatyplaces.ui.components.PlaceTopBar
import com.example.almatyplaces.ui.theme.AlmatyPlacesTheme
import com.example.almatyplaces.ui.theme.Spacing

@Composable
fun FavoritesScreen(
    favoriteIds: Set<Int>,
    onPlaceClick: (Int) -> Unit,
    onToggleFavorite: (Int) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val favoritePlaces = places.filter { it.id in favoriteIds }

    Scaffold(
        modifier = modifier,
        topBar = { PlaceTopBar(title = "Избранное", onBack = onBack) }
    ) { padding ->
        if (favoritePlaces.isEmpty()) {
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
                items(favoritePlaces, key = { it.id }) { place ->
                    ItemCard(
                        place = place,
                        isFavorite = true,
                        onClick = { onPlaceClick(place.id) },
                        onFavoriteClick = { onToggleFavorite(place.id) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun FavoritesScreenPreview() {
    AlmatyPlacesTheme { FavoritesScreen(setOf(1, 5), {}, {}, {}) }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FavoritesScreenEmptyDarkPreview() {
    AlmatyPlacesTheme { FavoritesScreen(emptySet(), {}, {}, {}) }
}