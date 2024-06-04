package com.ark.truckbill.repository.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bill")
data class BillEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo
    val name: String,
    @ColumnInfo
    val weight: Int = 0,
    @ColumnInfo
    val price: Int = 0,
    @ColumnInfo
    val start: String,
    @ColumnInfo
    val end: String,
    @ColumnInfo
    val startDate: String,
    @ColumnInfo
    val endDate: String,
)
