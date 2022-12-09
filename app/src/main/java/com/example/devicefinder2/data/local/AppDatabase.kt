package com.example.devicefinder2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.devicefinder2.data.model.Calculation
import com.example.devicefinder2.data.model.Strength
import com.example.devicefinder2.data.model.User
import com.example.devicefinder2.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [User::class, Strength::class, Calculation::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAppDao(): AppDao

    class Callback @Inject constructor(
        private val database: Provider<AppDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback()

}