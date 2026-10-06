package com.example.almatyplaces.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.almatyplaces.ui.theme.AlmatyPlacesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceTopBar(
    title: String,
    onBack: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = { Text(title, maxLines = 1, overflow = TextOverflow.Ellipsis) },
        navigationIcon = {
            if (onBack != null) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                }
            }
        },
        actions = actions
    )
}

@Preview(showBackground = true)
@Composable
private fun PlaceTopBarPreview() {
    AlmatyPlacesTheme { PlaceTopBar("Заголовок", onBack = {}) }
}