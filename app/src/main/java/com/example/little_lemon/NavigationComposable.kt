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

    // Check if user is already registered (i.e., has data in SharedPreferences)
    val context = LocalContext.current
    val sharedPrefs = context.getSharedPreferences("LittleLemon", Context.MODE_PRIVATE)

    val isUserLoggedIn = sharedPrefs.getString("firstName", "").isNullOrBlank().not()
    val startDestination = if (isUserLoggedIn) Home.route else onboarding.route

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(onboarding.route) {
            OnboardingScreen(navController = navController)
        }
        composable(Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Profile.route) {
            ProfileScreen(navController = navController)
        }
    }
}
