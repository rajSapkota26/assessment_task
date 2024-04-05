package com.example.assessment.feature.register.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val username: String,
    val email: String,
    val mobile: String,
    val fullName: String,
    val password: String,
    val isVerified:Boolean
)