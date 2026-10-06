package com.example.almatyplaces.ui

import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.almatyplaces.ui.screens.DetailScreen
import com.example.almatyplaces.ui.screens.FavoritesScreen
import com.example.almatyplaces.ui.screens.ListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    var favorites by remember { mutableStateOf(setOf<Int>()) }
    val toggleFavorite: (Int) -> Unit = { id ->
        favorites = if (id in favorites) favorites - id else favorites + id
    }

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            ListScreen(
                favoriteIds = favorites,
                onPlaceClick = { navController.navigate("detail/$it") },
                onToggleFavorite = toggleFavorite,
                onFavoritesClick = { navController.navigate("favorites") }
            )
        }
        composable(
            route = "detail/{placeId}",
            arguments = listOf(navArgument("placeId") { type = NavType.IntType })
        ) { entry ->
            val id = entry.arguments?.getInt("placeId") ?: -1
            DetailScreen(
                placeId = id,
                isFavorite = id in favorites,
                onToggleFavorite = { toggleFavorite(id) },
                onBack = { navController.popBackStack() }
            )
        }
        composable("favorites") {
            FavoritesScreen(
                favoriteIds = favorites,
                onPlaceClick = { navController.navigate("detail/$it") },
                onToggleFavorite = toggleFavorite,
                onBack = { navController.popBackStack() }
            )
        }
    }
}