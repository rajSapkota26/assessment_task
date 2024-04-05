package com.example.assessment.service_layer.service.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.assessment.feature.dashboard.dto.IAndORecord

@Dao
interface TransactionDao {

    // used to insert new transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: IAndORecord)

    // used to update existing transaction
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTransaction(transaction: IAndORecord)

    // used to delete transaction
    @Delete
    suspend fun deleteTransaction(transaction: IAndORecord)

    // get all saved transaction list
    @Query("SELECT * FROM all_transactions ORDER by createdAt DESC")
    fun getAllTransactions():List<IAndORecord>?

    // get all income or expense list by transaction type param
    @Query("SELECT * FROM all_transactions WHERE transactionType == :transactionType ORDER by createdAt DESC")
    fun getAllSingleTransaction(transactionType: String): List<IAndORecord>?

    // get single transaction by id
    @Query("SELECT * FROM all_transactions WHERE id = :id")
    fun getTransactionByID(id: Long): IAndORecord?

    // delete transaction by id
    @Query("DELETE FROM all_transactions WHERE id = :id")
    suspend fun deleteTransactionByID(id: Int)
}