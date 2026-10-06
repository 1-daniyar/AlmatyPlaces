package com.example.almatyplaces.data

import androidx.annotation.DrawableRes

data class Place(
    val id: Int,
    val name: String,
    val category: String,
    val rating: Double,
    val description: String,
    @DrawableRes val imageRes: Int
)