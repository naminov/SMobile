package com.naminov.smobile.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.naminov.smobile.data.db.dao.SettingsDao
import com.naminov.smobile.data.db.entity.SettingsEntity

@Database(entities = [SettingsEntity::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun SettingsDao(): SettingsDao
}