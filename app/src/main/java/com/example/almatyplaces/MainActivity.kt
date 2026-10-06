package com.example.almatyplaces

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.almatyplaces.ui.screens.ListScreen
import com.example.almatyplaces.ui.theme.AlmatyPlacesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlmatyPlacesTheme {
                var favorites by remember { mutableStateOf(setOf<Int>()) }
                ListScreen(
                    favoriteIds = favorites,
                    onPlaceClick = {},
                    onToggleFavorite = { id ->
                        favorites = if (id in favorites) favorites - id else favorites + id
                    },
                    onFavoritesClick = {}
                )
            }
        }
    }
}