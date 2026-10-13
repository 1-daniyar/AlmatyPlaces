package com.example.almatyplaces.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.almatyplaces.data.ALL_CATEGORIES
import com.example.almatyplaces.data.PlacesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "LifecycleDemo"

// Повар: принимает события от экрана, готовит состояние, переживает поворот экрана.
class PlacesViewModel(
    private val repository: PlacesRepository = PlacesRepository()
) : ViewModel() {

    // private: менять состояние может только ViewModel
    private val _uiState = MutableStateFlow(PlacesUiState(categories = repository.categories))

    // public и только для чтения: экран может смотреть, но не менять
    val uiState: StateFlow<PlacesUiState> = _uiState.asStateFlow()

    init {
        Log.d(TAG, "ViewModel init (создан)")
        loadPlaces()
    }

    // ---------- События от экрана ----------

    fun onSearchChange(query: String) {
        _uiState.update { it.copy(searchQuery = query).withLists() }
    }

    fun onCategorySelected(category: String) {
        _uiState.update { it.copy(selectedCategory = category).withLists() }
    }

    fun onFavoriteClick(id: Int) {
        _uiState.update { state ->
            val newFavorites =
                if (id in state.favoriteIds) state.favoriteIds - id else state.favoriteIds + id
            state.copy(favoriteIds = newFavorites).withLists()
        }
    }

    // Повторить загрузку. Выключаем имитацию ошибки, чтобы увидеть успех.
    fun onRetry() {
        repository.shouldFail = false
        loadPlaces()
    }

    // Кнопка в тулбаре: включаем имитацию ошибки, чтобы показать состояние error.
    fun onSimulateErrorClick() {
        repository.shouldFail = true
        loadPlaces()
    }

    // ---------- Внутренняя кухня ----------

    private fun loadPlaces() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val loaded = repository.getPlaces()
                _uiState.update {
                    it.copy(isLoading = false, placesById = loaded.associateBy { place -> place.id })
                        .withLists()
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = e.message ?: "Неизвестная ошибка")
                }
            }
        }
    }

    // Единственное место, где считаются поиск, фильтр и избранное.
    private fun PlacesUiState.withLists(): PlacesUiState {
        val visible = placesById.values
            .filter { selectedCategory == ALL_CATEGORIES || it.category == selectedCategory }
            .filter { it.name.contains(searchQuery.trim(), ignoreCase = true) }
        val favorites = placesById.values.filter { it.id in favoriteIds }
        return copy(visiblePlaces = visible, favoritePlaces = favorites)
    }

    override fun onCleared() {
        Log.d(TAG, "ViewModel onCleared (уничтожен)")
        super.onCleared()
    }
}
