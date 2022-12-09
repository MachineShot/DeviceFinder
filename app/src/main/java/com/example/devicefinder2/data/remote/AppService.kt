package com.example.devicefinder2.data.remote

import com.example.devicefinder2.data.model.Calculation
import com.example.devicefinder2.data.model.Strength
import com.example.devicefinder2.data.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AppService {
    companion object {
        const val ENDPOINT = "http://10.0.3.2:8080"
    }

    @GET("users")
    suspend fun getAllUsers(): Response<List<User>>

    @GET("calculations")
    suspend fun getAllCalculations(): Response<List<Calculation>>

    @GET("strengths")
    suspend fun getAllStrengths(): Response<List<Strength>>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int) : Response<User>
}