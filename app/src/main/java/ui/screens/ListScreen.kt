package com.example.almatyplaces.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.almatyplaces.ui.PlacesUiState
import com.example.almatyplaces.ui.PlacesViewModel
import com.example.almatyplaces.ui.components.CategoryChip
import com.example.almatyplaces.ui.components.EmptyState
import com.example.almatyplaces.ui.components.ErrorState
import com.example.almatyplaces.ui.components.ItemCard
import com.example.almatyplaces.ui.components.LoadingState
import com.example.almatyplaces.ui.components.PlaceTopBar
import com.example.almatyplaces.ui.preview.previewContentState
import com.example.almatyplaces.ui.preview.previewEmptyState
import com.example.almatyplaces.ui.preview.previewErrorState
import com.example.almatyplaces.ui.preview.previewLoadingState
import com.example.almatyplaces.ui.theme.AlmatyPlacesTheme
import com.example.almatyplaces.ui.theme.Spacing

// ROUTE: единственное место, которое знает про ViewModel.
// Берёт состояние, отдаёт его экрану и привязывает события к функциям ViewModel.
@Composable
fun ListRoute(
    viewModel: PlacesViewModel,
    onPlaceClick: (Int) -> Unit,
    onFavoritesClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ListScreen(
        uiState = uiState,
        onSearchChange = viewModel::onSearchChange,
        onCategorySelected = viewModel::onCategorySelected,
        onFavoriteClick = viewModel::onFavoriteClick,
        onRetry = viewModel::onRetry,
        onSimulateErrorClick = viewModel::onSimulateErrorClick,
        onPlaceClick = onPlaceClick,
        onFavoritesClick = onFavoritesClick
    )
}

// SCREEN: stateless. Получает uiState и лямбды, про ViewModel ничего не знает.
// Здесь только рисуем. Никакого поиска, фильтра и сортировки.
@Composable
fun ListScreen(
    uiState: PlacesUiState,
    onSearchChange: (String) -> Unit,
    onCategorySelected: (String) -> Unit,
    onFavoriteClick: (Int) -> Unit,
    onRetry: () -> Unit,
    onSimulateErrorClick: () -> Unit,
    onPlaceClick: (Int) -> Unit,
    onFavoritesClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val error = uiState.error

    Scaffold(
        modifier = modifier,
        topBar = {
            PlaceTopBar(
                title = "Алматы: места",
                actions = {
                    IconButton(onClick = onSimulateErrorClick) {
                        Icon(Icons.Default.BugReport, contentDescription = "Показать ошибку")
                    }
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
                horizontalArrangement = Arrangement.spacedBy(Spacing.l)
            ) {
                items(uiState.categories) { category ->
                    CategoryChip(
                        label = category,
                        selected = category == uiState.selectedCategory,
                        onClick = { onCategorySelected(category) }
                    )
                }
            }
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = onSearchChange,
                label = { Text("Поиск по названию") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.m)
            )
            // Четыре состояния: loading, error, empty, content
            when {
                uiState.isLoading -> LoadingState()
                error != null -> ErrorState(message = error, onRetry = onRetry)
                uiState.visiblePlaces.isEmpty() -> EmptyState(
                    "Ничего не найдено",
                    "Попробуйте другой запрос или категорию"
                )
                else -> LazyColumn(
                    contentPadding = PaddingValues(Spacing.m),
                    verticalArrangement = Arrangement.spacedBy(Spacing.s)
                ) {
                    items(uiState.visiblePlaces, key = { it.id }) { place ->
                        ItemCard(
                            place = place,
                            isFavorite = place.id in uiState.favoriteIds,
                            onClick = { onPlaceClick(place.id) },
                            onFavoriteClick = { onFavoriteClick(place.id) }
                        )
                    }
                }
            }
        }
    }
}

// ---------- Превью: по одному на каждое состояние, без ViewModel ----------

@Composable
private fun ListScreenForPreview(uiState: PlacesUiState) {
    AlmatyPlacesTheme {
        ListScreen(
            uiState = uiState,
            onSearchChange = {},
            onCategorySelected = {},
            onFavoriteClick = {},
            onRetry = {},
            onSimulateErrorClick = {},
            onPlaceClick = {},
            onFavoritesClick = {}
        )
    }
}

@Preview(name = "Loading", showBackground = true)
@Composable
private fun ListScreenLoadingPreview() = ListScreenForPreview(previewLoadingState)

@Preview(name = "Content", showBackground = true)
@Composable
private fun ListScreenContentPreview() = ListScreenForPreview(previewContentState)

@Preview(name = "Empty", showBackground = true)
@Composable
private fun ListScreenEmptyPreview() = ListScreenForPreview(previewEmptyState)

@Preview(name = "Error", showBackground = true)
@Composable
private fun ListScreenErrorPreview() = ListScreenForPreview(previewErrorState)
