package com.example.devicefinder2.repository

import com.example.devicefinder2.data.RemoteDataSource
import com.example.devicefinder2.data.local.AppDao
import com.example.devicefinder2.data.model.Calculation
import com.example.devicefinder2.data.model.Strength
import com.example.devicefinder2.data.model.User
import com.example.devicefinder2.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    val appDao: AppDao
){

    suspend fun fetchStrengths(): Flow<Result<List<Strength>>?> {
        return flow {
            emit(Result.loading())
            val result = remoteDataSource.fetchAllStrengths()

            //Cache to database if response is successful
            if (result.status == Result.Status.SUCCESS) {
                result.data?.let { it ->
                    appDao.deleteAllStrengths()
                    appDao.insertAllStrengths(it)
                }
            }
            else {
                emit(result)
            }
            emit(fetchStrengthsCached())
        }.flowOn(Dispatchers.IO)
    }

    private fun fetchStrengthsCached(): Result<List<Strength>> =
        appDao.getAllStrengths().let {
            Result.success(it)
        }

    fun fetchStrength(id: Int): Flow<Result<Strength>?> {
        return flow {
            emit(fetchStrengthCached(id))
            emit(Result.loading())
        }.flowOn(Dispatchers.IO)
    }

    private fun fetchStrengthCached(id: Int): Result<Strength>? =
        appDao.getStrength(id).let {
            Result.success(it)
        }

    suspend fun fetchUsers(): Flow<Result<List<User>>?> {
        return flow {
            emit(Result.loading())
            val result = remoteDataSource.fetchAllUsers()

            //Cache to database if response is successful
            if (result.status == Result.Status.SUCCESS) {
                result.data?.let { it ->
                    appDao.deleteAllUsers()
                    appDao.insertAllUsers(it)
                }
            }
            else {
                emit(result)
            }
            emit(fetchUsersCached())
        }.flowOn(Dispatchers.IO)
    }

    private fun fetchUsersCached(): Result<List<User>> =
        appDao.getAllUsers().let {
            Result.success(it)
        }

    fun fetchUser(id: Int): Flow<Result<User>?> {
        return flow {
            emit(Result.loading())
            emit(fetchUserCached(id))
        }.flowOn(Dispatchers.IO)
    }

    private fun fetchUserCached(id: Int): Result<User>? =
        appDao.getUser(id).let {
            Result.success(it)
        }

    suspend fun fetchCalculations(): Flow<Result<List<Calculation>>?> {
        return flow {
            emit(Result.loading())
            val result = remoteDataSource.fetchAllCalculations()

            //Cache to database if response is successful
            if (result.status == Result.Status.SUCCESS) {
                result.data?.let { it ->
                    appDao.deleteAllCalculations()
                    appDao.insertAllCalculations(it)
                }
            }
            else {
                emit(result)
            }
            emit(fetchCalculationsCached())
        }.flowOn(Dispatchers.IO)
    }

    private fun fetchCalculationsCached(): Result<List<Calculation>> =
        appDao.getAllCalculations().let {
            Result.success(it)
        }
}