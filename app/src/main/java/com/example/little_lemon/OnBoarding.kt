package com.example.little_lemon

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

//@Composable
//@Preview(showBackground = true, showSystemUi = true)
//fun OnBoardingPreview(){
//    OnboardingScreen()
//}

@Composable
fun OnboardingScreen(navController: NavHostController) {
    val context = LocalContext.current
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.logo), contentDescription = "Little Lemon Logo",
            modifier = Modifier
                .padding(top = 32.dp)
                .size(200.dp)
        )
        Text(
            text = "Let's get to know you",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 16.dp)
        )

        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name") },
            modifier = Modifier.padding(top = 16.dp)
        )
        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name") },
            modifier = Modifier.padding(top = 16.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.padding(top = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (firstName.isBlank() || lastName.isBlank() || email.isBlank()) {
                    message = "Registration unsuccessful. Please enter all data."
                } else {
                    val sharedPrefs =
                        context.getSharedPreferences("LittleLemon", Context.MODE_PRIVATE)
                    with(sharedPrefs.edit()) {
                        putString("firstName", firstName)
                        putString("lastName", lastName)
                        putString("email", email)
                        apply()
                    }
                    message = "Registration successful!"
                    navController.navigate(Home.route) {
                        popUpTo(onboarding.route) {
                            inclusive = true
                        }
                    }
                }
            },

        ) {
            Text(text = "Register")
        }
        if (message.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = message, color = MaterialTheme.colorScheme.error)
        }
    }
}