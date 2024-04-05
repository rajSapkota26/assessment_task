package com.example.assessment.service_layer.repository

import android.app.Application
import com.example.assessment.feature.dashboard.dto.IAndORecord
import com.example.assessment.feature.register.dto.User
import com.example.assessment.service_layer.database.AppDatabase
import com.example.assessment.utils.generic.BaseResponse
import kotlinx.coroutines.flow.flow

class RoomRepository constructor(private var application: Application) {
    companion object {
        private lateinit var database: AppDatabase
        fun init(application: Application) {
            database = AppDatabase.getInstance(application)
        }

        fun getDatabase(): AppDatabase {
            if (!Companion::database.isInitialized) {
                throw IllegalStateException("Database not initialized. Call init() first.")
            }
            return database
        }

    }

    init {
        init(application)
    }


    suspend fun insertUser(user: User) = flow {
        try {
            val dao = getDatabase().userDao()
            val newUser = dao.insertUser(user)
            val insertUser = dao.getUserById(newUser)
            if (insertUser != null) {
                emit(BaseResponse.success(data = insertUser))
            } else {
                emit(BaseResponse.empty(message = "user cant create.."))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(BaseResponse.onException(error = ex.message))
        }

    }


    suspend fun doesUserExist(userName: String, password: String): Boolean {
        val dao = getDatabase().userDao()
        val insertUser = dao.getUserByEmailAndPassword(userName, password)
        return insertUser != null

    }

    suspend fun getUserByUserNameAndPassword(userName: String, password: String) = flow {
        try {
            val dao = getDatabase().userDao()
            val insertUser = dao.getUserByEmailAndPassword(userName, password)
            if (insertUser != null) {
                emit(BaseResponse.success(data = insertUser))
            } else {
                emit(BaseResponse.error(error = "user not found"))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(BaseResponse.onException(error = ex.message))
        }

    }

    suspend fun updateUser(user: User) = flow {
        try {
            val dao = getDatabase().userDao()
            dao.updateUser(user)
            val insertUser = dao.getUserById(user.id)
            if (insertUser != null && insertUser.isVerified) {
                emit(BaseResponse.success(data = insertUser))
            } else {
                emit(BaseResponse.empty(message = "user cant create.."))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(BaseResponse.onException(error = ex.message))
        }

    }

    suspend fun insertTransaction(user: IAndORecord) = flow {
        try {
            val dao = getDatabase().transactionDao()
            val newUser = dao.insertTransaction(user)
            emit(BaseResponse.success(data = "Data Added"))
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(BaseResponse.onException(error = ex.message))
        }

    }

    suspend fun upDateTransaction(user: IAndORecord) = flow {
        try {
            val dao = getDatabase().transactionDao()
            val newUser = dao.updateTransaction(user)
            emit(BaseResponse.success(data = "Success"))
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(BaseResponse.onException(error = ex.message))
        }

    }
    suspend fun deleteTransaction(user: IAndORecord) = flow {
        try {
            val dao = getDatabase().transactionDao()
            val newUser = dao.deleteTransaction(user)
            emit(BaseResponse.success(data = "Success"))
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(BaseResponse.onException(error = ex.message))
        }

    }

    suspend fun getAllTransaction() = flow {
        try {
            val dao = getDatabase().transactionDao()
            val list = dao.getAllTransactions()
            emit(BaseResponse.success(data = list))

        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(BaseResponse.onException(error = ex.message))
        }

    }

}