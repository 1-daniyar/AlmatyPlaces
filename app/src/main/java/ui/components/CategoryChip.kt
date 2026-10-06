package com.example.almatyplaces.ui.components

import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.almatyplaces.ui.theme.AlmatyPlacesTheme

@Composable
fun CategoryChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(label) },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun CategoryChipPreview() {
    AlmatyPlacesTheme { CategoryChip("Парки", true, {}) }
}