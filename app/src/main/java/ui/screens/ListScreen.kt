package com.example.almatyplaces.ui.screens

import com.example.almatyplaces.ui.components.PlaceTopBar
import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.almatyplaces.data.categories
import com.example.almatyplaces.data.places
import com.example.almatyplaces.ui.components.CategoryChip
import com.example.almatyplaces.ui.components.EmptyState
import com.example.almatyplaces.ui.components.ItemCard
import com.example.almatyplaces.ui.theme.AlmatyPlacesTheme
import com.example.almatyplaces.ui.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    favoriteIds: Set<Int>,
    onPlaceClick: (Int) -> Unit,
    onToggleFavorite: (Int) -> Unit,
    onFavoritesClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selected by remember { mutableStateOf("Все") }
    val filtered = if (selected == "Все") places else places.filter { it.category == selected }

    Scaffold(
        modifier = modifier,
        topBar = {
            PlaceTopBar(
                title = "Алматы: места",
                actions = {
                    IconButton(onClick = onFavoritesClick) {
                        Icon(Icons.Default.Favorite, contentDescription = "Открыть избранное")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = Spacing.m),
                horizontalArrangement = Arrangement.spacedBy(Spacing.s)
            ) {
                items(categories) { category ->
                    CategoryChip(category, category == selected, { selected = category })
                }
            }
            if (filtered.isEmpty()) {
                EmptyState("Ничего не найдено", "Попробуйте другую категорию")
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(Spacing.m),
                    verticalArrangement = Arrangement.spacedBy(Spacing.s)
                ) {
                    items(filtered, key = { it.id }) { place ->
                        ItemCard(
                            place = place,
                            isFavorite = place.id in favoriteIds,
                            onClick = { onPlaceClick(place.id) },
                            onFavoriteClick = { onToggleFavorite(place.id) }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ListScreenPreview() {
    AlmatyPlacesTheme { ListScreen(setOf(1), {}, {}, {}) }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ListScreenDarkPreview() {
    AlmatyPlacesTheme { ListScreen(setOf(1), {}, {}, {}) }
}