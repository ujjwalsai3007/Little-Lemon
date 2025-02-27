package com.example.little_lemon.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.little_lemon.data.database.MenuItemEntity
import com.example.little_lemon.data.database.AppDatabase
import com.example.little_lemon.data.database.MenuRepository
import com.example.little_lemon.data.database.netwrok.MenuApiService
import kotlinx.coroutines.launch

class MenuViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val menuDao = database.menuDao()
    private val apiService = MenuApiService()
    private val repository = MenuRepository(menuDao, apiService)

    val menuItems = repository.allMenuItems

    private val _searchQuery = MutableLiveData<String>("")
    val searchQuery: LiveData<String> = _searchQuery

    private val _selectedCategory = MutableLiveData<String?>(null)
    val selectedCategory: LiveData<String?> = _selectedCategory

    val filteredMenuItems: LiveData<List<MenuItemEntity>> = MediatorLiveData<List<MenuItemEntity>>().apply {
        addSource(menuItems) { updateList() }
        addSource(_searchQuery) { updateList() }
        addSource(_selectedCategory) { updateList() }
    }

    fun fetchMenu() {
        viewModelScope.launch {
            repository.fetchMenuFromApi()
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setCategory(category: String?) {
        _selectedCategory.value = category
    }

    private fun MediatorLiveData<List<MenuItemEntity>>.updateList() {
        val query = _searchQuery.value.orEmpty().lowercase()
        val category = _selectedCategory.value
        val fullList = menuItems.value ?: emptyList()

        val filteredList = fullList.filter { item ->
            (category == null || item.category.equals(category, ignoreCase = true)) &&
                    (query.isEmpty() || item.title.lowercase().contains(query))
        }

        value = filteredList
    }
}