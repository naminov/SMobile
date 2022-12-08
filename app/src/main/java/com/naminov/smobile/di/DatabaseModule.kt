package com.naminov.smobile.di

import android.content.Context
import androidx.room.Room
import com.naminov.smobile.data.db.Constants
import com.naminov.smobile.data.db.Database
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(context: Context): Database {
        return Room
            .databaseBuilder(context, Database::class.java, Constants.DB_NAME)
            .build()
    }
}