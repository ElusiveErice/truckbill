package com.ark.truckbill.repository.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ark.truckbill.repository.entity.BillEntity

@Dao
interface BillDao {
    @Query("Select * from bill")
    fun getAll(): List<BillEntity>

    @Query("Select * from bill where id = :id")
    fun getBillWithId(id: Int): BillEntity?

    @Insert
    fun insertBill(billEntity: BillEntity)

    @Update
    fun updateBill(billEntity: BillEntity)

    @Delete
    fun deleteBill(billEntity: BillEntity)
}
