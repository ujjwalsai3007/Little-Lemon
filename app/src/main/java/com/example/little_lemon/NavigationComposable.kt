package com.example.little_lemon

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.ProfileScreen

@Composable
fun MyNavigation() {
    val navController = rememberNavController()

    val context = LocalContext.current
    val sharedPrefs = context.getSharedPreferences("LittleLemon", Context.MODE_PRIVATE)

    val isUserLoggedIn = sharedPrefs.getString("firstName", "").isNullOrBlank().not()
    val startDestination = if (isUserLoggedIn) "home" else "onboarding"  // Use string literals

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("onboarding") {  // Use string literals
            OnboardingScreen(navController = navController)
        }
        composable("home") {  // Use string literals
            HomeScreen(navController = navController)
        }
        composable("profile") {
            ProfileScreen(navController = navController)
        }
    }
}
