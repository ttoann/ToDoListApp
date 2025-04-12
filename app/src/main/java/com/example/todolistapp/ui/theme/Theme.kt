package com.example.todolistapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColors = lightColorScheme(
    primary = Color(0xFF4A6572),           // Blueish gray
    onPrimary = Color.White,
    primaryContainer = Color(0xFFDDE7EE),  // Light blue-gray
    onPrimaryContainer = Color(0xFF1A2B35),
    secondary = Color(0xFFF9AA33),         // Golden yellow
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFFFFEECC), // Light yellow
    onSecondaryContainer = Color(0xFF553900),
    tertiary = Color(0xFF4CAF50),          // Green
    onTertiary = Color.White,
    background = Color(0xFFF5F5F5),        // Light gray
    onBackground = Color(0xFF212121),      // Dark gray
    surface = Color.White,
    onSurface = Color(0xFF212121),
    surfaceVariant = Color(0xFFEEEEEE),    // Another light gray
    onSurfaceVariant = Color(0xFF5F6368),  // Medium gray
    error = Color(0xFFB00020),             // Red
    onError = Color.White
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF90A4AE),           // Lighter blue-gray
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF344955),  // Dark blue-gray
    onPrimaryContainer = Color(0xFFDDE7EE),
    secondary = Color(0xFFFFB74D),         // Lighter golden
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF6D4C00), // Dark gold
    onSecondaryContainer = Color(0xFFFFEECC),
    tertiary = Color(0xFF81C784),          // Lighter green
    onTertiary = Color.Black,
    background = Color(0xFF121212),        // Dark gray
    onBackground = Color(0xFFDEDEDE),      // Light gray
    surface = Color(0xFF1E1E1E),           // Slightly lighter dark
    onSurface = Color(0xFFDEDEDE),
    surfaceVariant = Color(0xFF2A2A2A),    // Medium-dark gray
    onSurfaceVariant = Color(0xFFBDBDBD),  // Light-medium gray
    error = Color(0xFFCF6679),             // Pink-red
    onError = Color.Black
)

@Composable
fun TodoAppTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColors
    } else {
        LightColors
    }

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}