package com.example.assessment.service_layer.service.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.assessment.feature.register.dto.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User):Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(user: User)

    // get single user by id
    @Query("SELECT * FROM users WHERE id = :id")
    suspend  fun getUserById(id: Long): User?

    // get single user by username and password
    @Query("SELECT * FROM users WHERE username = :userName AND password = :password")
    suspend fun getUserByEmailAndPassword(userName: String,password:String): User?


    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>?
}