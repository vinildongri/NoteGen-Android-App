package com.example.notegen

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.notegen.ui.auth.Login
import com.example.notegen.ui.navigation.AppNavHost
import com.example.notegen.ui.theme.NoteGenTheme
import com.example.notegen.viewModels.LoginViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            )
        )

        // 1. Get access to storage (SharedPreferences)
        val sharedPreferences = getSharedPreferences("App_Settings", Context.MODE_PRIVATE)

        setContent {
            // 2. Load the saved value (Default to 'false' aka Light Mode if nothing is saved)
            val savedDarkModeState = sharedPreferences.getBoolean("DARK_MODE_KEY", false)

            // 3. Initialize state with the SAVED value
            var isDarkMode by remember { mutableStateOf(savedDarkModeState) }

            // Status Bar Fix
            val view = LocalView.current
            if (!view.isInEditMode) {
                SideEffect {
                    val window = (view.context as Activity).window
                    val insetsController = WindowCompat.getInsetsController(window, view)
                    insetsController.isAppearanceLightStatusBars = !isDarkMode
                }
            }

            NoteGenTheme(
                darkTheme = isDarkMode
            ) {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    AppNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        isDarkMode = isDarkMode,
                        onDarkModeChange = { newMode ->
                            // 4. Update the state AND Save to storage immediately
                            isDarkMode = newMode
                            sharedPreferences.edit().putBoolean("DARK_MODE_KEY", newMode).apply()
                        }
                    )
                }
            }
        }
    }
}