package com.example.littlelemon

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.little_lemon.R
import com.example.little_lemon.onboarding

@Composable
fun ProfileScreen(navController: NavHostController) {

    val context = LocalContext.current
    val sharedPrefs = context.getSharedPreferences("LittleLemon", Context.MODE_PRIVATE)

    // Retrieve user data
    val firstName = sharedPrefs.getString("firstName", "") ?: ""
    val lastName = sharedPrefs.getString("lastName", "") ?: ""
    val email = sharedPrefs.getString("email", "") ?: ""

    // Top-level container to manage vertical spacing
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        // Keep everything centered horizontally
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 1. Little Lemon logo
        Image(
            painter = painterResource(id = R.drawable.logo), // Replace with your logo resource
            contentDescription = "Little Lemon Logo",
            modifier = Modifier
                .size(100.dp)
                .padding(top = 32.dp)
        )

        // 2. Section Title
        Text(
            text = "Personal information",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 24.dp, bottom = 24.dp)
        )

        // 3. Read-only OutlinedTextFields to display user info
        OutlinedTextField(
            value = firstName,
            onValueChange = {}, // No changes allowed (read-only)
            readOnly = true,
            label = { Text("First name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = lastName,
            onValueChange = {},
            readOnly = true,
            label = { Text("Last name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = {},
            readOnly = true,
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )

        // Spacer to push the Log out button toward the bottom
        Spacer(modifier = Modifier.weight(1f))

        // 4. Log out button
        Button(
            onClick = {
                // Clear SharedPreferences data
                with(sharedPrefs.edit()) {
                    clear()
                    apply()
                }
                // Navigate to Onboarding
                navController.navigate(onboarding.route) {
                    // Remove Profile from the back stack
                    popUpTo(onboarding.route) { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        ) {
            Text(text = "Log out")
        }
    }
}

