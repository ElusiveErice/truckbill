package com.ark.truckbill.data

import kotlinx.datetime.LocalDate

data class Bill(
    val name: String,
    val weight: Float,
    val price: Float,
    val start: String,
    val end: String
) {
    var isCloseOut = false
    var startDate = LocalDate(2024, 3, 1)
    var endDate = LocalDate(2024, 3, 2)
}