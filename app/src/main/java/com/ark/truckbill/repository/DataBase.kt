package com.ark.truckbill.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ark.truckbill.repository.dao.BillDao
import com.ark.truckbill.repository.entity.BillEntity

@Database(version = 1, entities = [BillEntity::class], exportSchema = false)
abstract class BillDataBase : RoomDatabase() {
    companion object {
        private var db: BillDataBase? = null
        private const val name = "bill"
        fun getDB(context: Context) =
            db ?: Room.databaseBuilder(context, BillDataBase::class.java, name).build()
                .apply { db = this }
    }

    abstract fun getBillDao(): BillDao
}
