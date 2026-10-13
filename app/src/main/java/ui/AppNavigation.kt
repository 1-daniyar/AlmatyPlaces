package com.example.almatyplaces.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.almatyplaces.ui.screens.DetailRoute
import com.example.almatyplaces.ui.screens.FavoritesRoute
import com.example.almatyplaces.ui.screens.ListRoute

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Один ViewModel на все экраны: избранное общее.
    // Он создаётся здесь, вне NavHost, поэтому принадлежит Activity
    // и переживает поворот экрана.
    val viewModel: PlacesViewModel = viewModel()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            ListRoute(
                viewModel = viewModel,
                onPlaceClick = { navController.navigate("detail/$it") },
                onFavoritesClick = { navController.navigate("favorites") }
            )
        }
        composable(
            route = "detail/{placeId}",
            arguments = listOf(navArgument("placeId") { type = NavType.IntType })
        ) { entry ->
            val id = entry.arguments?.getInt("placeId") ?: -1
            DetailRoute(
                placeId = id,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable("favorites") {
            FavoritesRoute(
                viewModel = viewModel,
                onPlaceClick = { navController.navigate("detail/$it") },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
