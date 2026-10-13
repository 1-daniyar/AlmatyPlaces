package com.example.almatyplaces.ui

import com.example.almatyplaces.data.ALL_CATEGORIES
import com.example.almatyplaces.data.Place

// Доска заказов: всё, что нужно экрану, чтобы нарисовать себя.
// Только val: состояние не меняют, а заменяют новым через copy().
data class PlacesUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val categories: List<String> = emptyList(),
    val selectedCategory: String = ALL_CATEGORIES,
    val searchQuery: String = "",
    val favoriteIds: Set<Int> = emptySet(),
    val placesById: Map<Int, Place> = emptyMap(),
    val visiblePlaces: List<Place> = emptyList(),   // после поиска и фильтра
    val favoritePlaces: List<Place> = emptyList()   // только избранные
)
