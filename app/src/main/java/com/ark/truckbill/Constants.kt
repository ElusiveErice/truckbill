package com.ark.truckbill

import java.time.format.DateTimeFormatter


val AlternativeYearList = (2024..2050).toList()
val AlternativeMonthsList = (1..12).toList()

val YearMonthDayFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")