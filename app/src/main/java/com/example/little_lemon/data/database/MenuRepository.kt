package com.example.little_lemon.data.database

import androidx.lifecycle.LiveData
import com.example.little_lemon.data.database.MenuDao
import com.example.little_lemon.data.database.MenuItemEntity
import com.example.little_lemon.data.database.netwrok.MenuApiService
import com.example.little_lemon.data.database.netwrok.MenuItemNetwork

class MenuRepository(private val menuDao: MenuDao, private val apiService: MenuApiService) {

    val allMenuItems: LiveData<List<MenuItemEntity>> = menuDao.getMenuItems()

    suspend fun fetchMenuFromApi() {
        try {
            val menuItems = apiService.fetchMenu()
            menuDao.clearMenu()
            menuDao.insertMenuItems(menuItems.map { it.toEntity() })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun MenuItemNetwork.toEntity(): MenuItemEntity {
        return MenuItemEntity(
            id = this.id,
            title = this.title,
            description = this.description,
            price = this.price,
            image = this.image,
            category = this.category
        )
    }
}