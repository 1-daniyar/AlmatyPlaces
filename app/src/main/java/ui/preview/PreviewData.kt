package com.example.almatyplaces.ui.preview

import com.example.almatyplaces.R
import com.example.almatyplaces.data.ALL_CATEGORIES
import com.example.almatyplaces.data.Place
import com.example.almatyplaces.ui.PlacesUiState

// Фальшивые данные только для @Preview. ViewModel и Repository здесь не нужны:
// именно поэтому можно посмотреть любое состояние, не запуская приложение.

val previewPlaces = listOf(
    Place(1, "Парк Первого Президента", "Парки", 4.8, "Большой городской парк с аллеями и прудами.", R.drawable.park),
    Place(5, "Центральный государственный музей", "Музеи", 4.7, "Крупнейший музей страны.", R.drawable.museum),
    Place(8, "Кофейня «Арбат»", "Кафе", 4.3, "Уютная кофейня на пешеходной улице.", R.drawable.cafe)
)

private val previewCategories = listOf(ALL_CATEGORIES, "Парки", "Кафе", "Музеи", "Бары")

val previewContentState = PlacesUiState(
    isLoading = false,
    categories = previewCategories,
    favoriteIds = setOf(1),
    placesById = previewPlaces.associateBy { it.id },
    visiblePlaces = previewPlaces,
    favoritePlaces = listOf(previewPlaces[0])
)

val previewLoadingState = PlacesUiState(
    isLoading = true,
    categories = previewCategories
)

val previewEmptyState = PlacesUiState(
    isLoading = false,
    categories = previewCategories,
    searchQuery = "xyz",
    visiblePlaces = emptyList()
)

val previewErrorState = PlacesUiState(
    isLoading = false,
    categories = previewCategories,
    error = "Не удалось загрузить места. Проверьте соединение."
)
