package com.ark.truckbill

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import kotlinx.datetime.LocalDate
import java.util.*

@Composable
fun px2Dp(px: Int) = with(LocalDensity.current) { px.toDp() }

@Composable
fun dp2px(dp: Dp) = LocalDensity.current.run { dp.toPx() }

/**
 * 获取当前系统日期
 */
fun getCurrentDate(): LocalDate {
    val instance = Calendar.getInstance()
    val year = instance[Calendar.YEAR]
    val month = instance[Calendar.MONTH]
    val day = instance[Calendar.DAY_OF_MONTH]
    return LocalDate(year, month, day)
}