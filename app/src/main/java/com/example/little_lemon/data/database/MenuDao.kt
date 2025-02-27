package com.example.little_lemon.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuDao {
    @Query("SELECT * FROM menu_items")
    fun getMenuItems(): LiveData<List<MenuItemEntity>>  // Changed to LiveData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMenuItems(menuItems: List<MenuItemEntity>)

    @Query("DELETE FROM menu_items")
    fun clearMenu()
}