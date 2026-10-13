package com.example.almatyplaces

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.almatyplaces.ui.AppNavigation
import com.example.almatyplaces.ui.theme.AlmatyPlacesTheme

private const val TAG = "LifecycleDemo"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Activity onCreate (savedInstanceState = ${if (savedInstanceState == null) "null, первый запуск" else "не null, пересоздана"})")
        enableEdgeToEdge()
        setContent {
            AlmatyPlacesTheme { AppNavigation() }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "Activity onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "Activity onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "Activity onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "Activity onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Activity onDestroy (isChangingConfigurations = $isChangingConfigurations)")
    }
}
