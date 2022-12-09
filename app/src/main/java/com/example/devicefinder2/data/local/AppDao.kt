package com.example.devicefinder2.data.local

import androidx.room.*
import com.example.devicefinder2.data.model.Calculation
import com.example.devicefinder2.data.model.Strength
import com.example.devicefinder2.data.model.User

@Dao
interface AppDao {

    @Query("SELECT * FROM users")
    fun getAllUsers(): List<User>

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUser(id: Int): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllUsers(users: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("DELETE FROM users WHERE id = :id")
    fun deleteUser(id: Int)

    @Query("DELETE FROM users")
    fun deleteAllUsers()

    @Query("SELECT * FROM strengths")
    fun getAllStrengths(): List<Strength>

    @Query("SELECT * FROM strengths WHERE id = :id")
    fun getStrength(id: Int): Strength

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllStrengths(strengths: List<Strength>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStrength(strength: Strength)

    @Query("DELETE FROM strengths")
    fun deleteAllStrengths()

    @Query("SELECT * FROM calculations")
    fun getAllCalculations(): List<Calculation>

    @Query("SELECT * FROM calculations WHERE id = :id")
    fun getCalculation(id: Int): Calculation

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCalculations(calculations: List<Calculation>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCalculation(calculation: Calculation)

    @Query("DELETE FROM calculations")
    fun deleteAllCalculations()
}