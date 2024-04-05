package com.example.assessment.feature.dashboard.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.assessment.utils.DateAndTimeUtils
import java.io.Serializable


@Entity(tableName = "all_transactions")
data class IAndORecord(
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "amount")
    var amount: Double,
    @ColumnInfo(name = "transactionType")
    var transactionType: String,
    @ColumnInfo(name = "tag")
    var tag: String,
    @ColumnInfo(name = "date")
    var date: String,
    @ColumnInfo(name = "note")
    var note: String,
    @ColumnInfo(name = "createdAt")
    var createdAt: String = DateAndTimeUtils.getTodayDate(),
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,
) : Serializable {
    val createdAtDateFormat: String
        get() = DateAndTimeUtils.formatDate(createdAt) // Date Format: 2024-4-4
}