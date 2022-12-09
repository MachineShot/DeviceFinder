package com.example.devicefinder2.data
import com.example.devicefinder2.data.model.Calculation
import com.example.devicefinder2.data.model.Strength
import com.example.devicefinder2.data.model.User
import com.example.devicefinder2.data.remote.AppService
import com.example.devicefinder2.util.ErrorUtils
import com.example.devicefinder2.util.Result
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val retrofit: Retrofit) {
    suspend fun fetchAllStrengths(): Result<List<Strength>> {
        val service = retrofit.create(AppService::class.java);
        return getResponse(
            request = { service.getAllStrengths() },
            defaultErrorMessage = "Error fetching Strength list")

    }

    suspend fun fetchAllUsers(): Result<List<User>> {
        val service = retrofit.create(AppService::class.java);
        return getResponse(
            request = { service.getAllUsers() },
            defaultErrorMessage = "Error fetching User list")

    }

    suspend fun fetchUser(id: Int): Result<User> {
        val service = retrofit.create(AppService::class.java);
        return getResponse(
            request = { service.getUser(id) },
            defaultErrorMessage = "Error fetching User")

    }

    suspend fun fetchAllCalculations(): Result<List<Calculation>> {
        val service = retrofit.create(AppService::class.java);
        return getResponse(
            request = { service.getAllCalculations() },
            defaultErrorMessage = "Error fetching Calculation list")

    }

    private suspend fun <T> getResponse(request: suspend () -> Response<T>, defaultErrorMessage: String): Result<T> {
        return try {
            println("I'm working in thread ${Thread.currentThread().name}")
            val result = request.invoke()
            if (result.isSuccessful) {
                return Result.success(result.body())
            } else {
                val errorResponse = ErrorUtils.parseError(result, retrofit)
                Result.error(errorResponse?.status_message ?: defaultErrorMessage, errorResponse)
            }
        } catch (e: Throwable) {
            Result.error("Network Error", null)
        }
    }
}