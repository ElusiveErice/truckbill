package com.ark.truckbill.data

import com.ark.truckbill.repository.entity.BillEntity
import java.util.*

data class Bill(
    val name: String,
    val weight: Float,
    val price: Float,
    val start: String,
    val end: String
) {
    var isCloseOut = false
    var startDate = Calendar.getInstance()
    var endDate = Calendar.getInstance()

    companion object {
        fun build(billEntity: BillEntity): Bill {
            return Bill(
                billEntity.name,
                billEntity.weight,
                billEntity.price,
                billEntity.start,
                billEntity.end
            )
        }
    }
}
