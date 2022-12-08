package com.naminov.smobile.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.naminov.smobile.data.db.entity.SettingsEntity

@Dao
interface SettingsDao {
    @Query("SELECT * FROM settings")
    suspend fun getSettings(): List<SettingsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setSettings(settings: List<SettingsEntity>)
}