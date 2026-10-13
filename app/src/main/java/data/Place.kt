package com.example.almatyplaces.data

import androidx.annotation.DrawableRes

// Название категории "показать всё". Один раз объявили, используем везде.
const val ALL_CATEGORIES = "Все"

data class Place(
    val id: Int,
    val name: String,
    val category: String,
    val rating: Double,
    val description: String,
    @DrawableRes val imageRes: Int
)
