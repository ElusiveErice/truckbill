package com.ark.truckbill

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun px2Dp(px: Int) = with(LocalDensity.current) { px.toDp() }

@Composable
fun dp2px(dp: Dp) = LocalDensity.current.run { dp.toPx() }
