package com.example.little_lemon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavHostController
import com.example.little_lemon.data.database.MenuItemEntity
import com.example.little_lemon.viewmodel.MenuViewModel
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import android.app.Application

@Composable
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current
    val viewModel: MenuViewModel = viewModel(
        factory = androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
            .getInstance(context.applicationContext as Application)
    )

    LaunchedEffect(Unit) {
        viewModel.fetchMenu()
    }

    // Add debug text to see if the screen is rendering
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface
    ) {
        val menuItems by viewModel.filteredMenuItems.observeAsState(emptyList())
        val searchQuery by viewModel.searchQuery.observeAsState("")
        val selectedCategory by viewModel.selectedCategory.observeAsState(null)

        Column(modifier = Modifier.fillMaxSize()) {
            // Hero Section
            HeroSection(searchQuery, onSearchChanged = viewModel::setSearchQuery)

            // Debug text to show data status
            if (menuItems.isEmpty()) {
                Text(
                    text = "Loading menu items...",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            // Menu Category Filter
            MenuCategoryFilter(selectedCategory, viewModel::setCategory)

            // Display Menu Items
            LazyColumn {
                items(menuItems) { item ->
                    MenuItemCard(item)
                }
            }
        }
    }
}
@Composable
fun HeroSection(searchQuery: String, onSearchChanged: (String) -> Unit) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Little Lemon", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChanged,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Enter search phrase") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") }
        )
    }
}
@Composable
fun MenuCategoryFilter(selectedCategory: String?, onCategorySelected: (String?) -> Unit) {
    val categories = listOf("All", "Starters", "Mains", "Desserts", "Drinks")

    Row(
        modifier = Modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEach { category ->
            Button(
                onClick = { onCategorySelected(if (category == "All") null else category) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedCategory == category) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(category)
            }
        }
    }
}

@Composable
fun MenuItemCard(item: MenuItemEntity) {
    Row(modifier = Modifier.padding(8.dp)) {
        Column {
            Text(text = item.title, fontWeight = FontWeight.Bold)
            Text(text = item.description)
            Text(text = "$${item.price}")
        }
        Spacer(modifier = Modifier.weight(1f))
        AsyncImage(
            model = item.image,  // Use item.image instead of imageUrl
            contentDescription = "Menu item image",
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
    }
}
