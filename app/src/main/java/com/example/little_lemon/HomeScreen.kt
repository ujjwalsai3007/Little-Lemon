package com.example.little_lemon

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavHostController){
    Column(modifier=Modifier.fillMaxSize().padding(16.dp)){
        Text(text="Home Screen")

        Spacer(modifier=Modifier.padding(16.dp))

        Button(onClick={
            navController.navigate(Profile.route)
        })
        {
            Text(text="Go to Profile")
        }



    }

}