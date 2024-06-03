package com.ark.truckbill.repository.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "bill")
data class BillEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo
    val name: String,
    @ColumnInfo
    val weight: Float,
    @ColumnInfo
    val price: Float,
    @ColumnInfo
    val start: String,
    @ColumnInfo
    val end: String,
//    @ColumnInfo
//    val startDate: Date,
//    @ColumnInfo
//    val endDate: Date,

//    @ColumnInfo
//    val updateTimeZone: SimpleTimeZone
)