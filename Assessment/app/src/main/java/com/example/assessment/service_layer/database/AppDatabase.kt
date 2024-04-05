package com.example.assessment.service_layer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.assessment.feature.dashboard.dto.IAndORecord
import com.example.assessment.feature.register.dto.User
import com.example.assessment.service_layer.service.dao.TransactionDao
import com.example.assessment.service_layer.service.dao.UserDao

@Database(entities = [User::class,IAndORecord::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "assessment.db"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }
    }
}