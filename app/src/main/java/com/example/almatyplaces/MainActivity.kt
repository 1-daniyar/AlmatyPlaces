package com.example.almatyplaces

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.almatyplaces.ui.AppNavigation
import com.example.almatyplaces.ui.theme.AlmatyPlacesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlmatyPlacesTheme { AppNavigation() }
        }
    }
}