package com.ark.truckbill.data

import com.ark.truckbill.repository.entity.BillEntity
import java.time.LocalDate

data class Bill(
    val name: String,
    val weight: Int,
    val price: Int,
    val start: String,
    val end: String,
    val startDate: LocalDate,
    val endDate: LocalDate
) {
    var isCloseOut = false
}

fun billEntityToBill(billEntity: BillEntity): Bill {
    return Bill(
        billEntity.name,
        billEntity.weight,
        billEntity.price,
        billEntity.start,
        billEntity.end,
        LocalDate.parse(billEntity.startDate),
        LocalDate.parse(billEntity.endDate)
    )
}
