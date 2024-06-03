package com.ark.truckbill.data

import com.ark.truckbill.repository.entity.BillEntity
import kotlinx.datetime.LocalDate
import java.util.Date

data class Bill(
    val name: String,
    val weight: Float,
    val price: Float,
    val start: String,
    val end: String
) {
    var isCloseOut = false
    var startDate = LocalDate(2024, 3, 1)
    var endDate = LocalDate(2024, 3, 1)

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
