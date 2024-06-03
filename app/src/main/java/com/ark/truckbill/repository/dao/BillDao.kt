package com.ark.truckbill.repository.dao

import androidx.room.Dao
import androidx.room.Query
import com.ark.truckbill.repository.entity.BillEntity

@Dao
interface BillDao {
    @Query("Select * from bill")
    fun getAll():List<BillEntity>
}