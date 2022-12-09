package com.example.devicefinder2.di

import android.app.Application
import androidx.room.Room
import com.example.devicefinder2.data.local.AppDao
import com.example.devicefinder2.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application, callback: AppDatabase.Callback): AppDatabase{
        return Room.databaseBuilder(application, AppDatabase::class.java, "app_database")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    }

    @Provides
    fun provideAppDao(db: AppDatabase): AppDao {
        return db.getAppDao()
    }
}