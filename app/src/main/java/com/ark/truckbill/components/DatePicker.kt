package com.ark.truckbill.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ark.truckbill.R

enum class DatePickerMode {
    Year,
    YearMonth,
    YearMonthDay,
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DatePickerModal(
    mode: DatePickerMode = DatePickerMode.YearMonthDay,
    sheetState: ModalBottomSheetState =
        rememberModalBottomSheetState(ModalBottomSheetValue.Hidden),
    year: Int = 2024,
    month: Int = 1,
    day: Int = 1,
    onDismiss: (selected: Boolean, year: Int, month: Int, day: Int) -> Unit,
    content: @Composable () -> Unit
) {
    var selectYear by remember { mutableStateOf(2024) }
    var selectMonth by remember { mutableStateOf(1) }
    var selectDay by remember { mutableStateOf(1) }
    LaunchedEffect(year, month, day) {
        selectYear = year
        selectMonth = month
        selectDay = day
    }
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    Arrangement.SpaceBetween,
                    Alignment.CenterVertically
                ) {
                    IconButton(onClick = { onDismiss(false, 0, 0, 0) }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "cancel")
                    }
                    Text(text = stringResource(id = R.string.select_date))
                    TextButton(onClick = { onDismiss(true, selectYear, selectMonth, selectDay) }) {
                        Text(text = stringResource(id = R.string.confirm))
                    }
                }
                DateWheel(mode, selectYear, selectMonth, selectDay) { index, value ->
                    when (index) {
                        0 -> selectYear = value
                        1 -> selectMonth = value
                        2 -> selectDay = value
                    }
                }
            }
        }
    ) {
        content()
    }
}

@Composable
fun DatePickerDialog(
    mode: DatePickerMode = DatePickerMode.YearMonthDay,
    dialogState: MutableState<Boolean>,
    year: Int = 2024,
    month: Int = 1,
    day: Int = 1,
    onDismiss: (selected: Boolean, year: Int, month: Int, day: Int) -> Unit,
) {
    var selectYear by remember { mutableStateOf(year) }
    var selectMonth by remember { mutableStateOf(month) }
    var selectDay by remember { mutableStateOf(day) }

    LaunchedEffect(year, month, day) {
        selectYear = year
        selectMonth = month
        selectDay = day
    }
    if (dialogState.value) {
        Dialog(onDismissRequest = { onDismiss(false, 0, 0, 0) }) {
            Column(
                modifier = Modifier
                    .width(500.dp)
                    .background(Color.White, MaterialTheme.shapes.small)
                    .padding(10.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.select_date),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                DateWheel(mode, selectYear, selectMonth, selectDay) { index, value ->
                    when (index) {
                        0 -> selectYear = value
                        1 -> selectMonth = value
                        2 -> selectDay = value
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    OutlinedButton(onClick = { onDismiss(false, 0, 0, 0) }) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                    Button(onClick = { onDismiss(true, selectYear, selectMonth, selectDay) }) {
                        Text(text = stringResource(id = R.string.confirm))
                    }
                }
            }
        }
    }
}


@Composable
private fun DateWheel(
    mode: DatePickerMode,
    year: Int,
    month: Int,
    day: Int,
    onChange: (index: Int, value: Int) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            val modifier = Modifier.weight(1f)

            //  年
            if (setOf(
                    DatePickerMode.YearMonthDay,
                    DatePickerMode.YearMonth,
                    DatePickerMode.Year
                ).indexOf(mode) > -1
            )
                ColumnPicker(
                    modifier = modifier,
                    value = year,
                    label = { "${it}年" },
                    range = 1920..2060,
                    onValueChange = {
                        onChange(0, it)
                    }
                )

            //  月
            if (setOf(DatePickerMode.YearMonthDay, DatePickerMode.YearMonth).indexOf(mode) > -1)
                ColumnPicker(
                    modifier = modifier,
                    value = month,
                    label = { "${it}月" },
                    range = 1..12,
                    onValueChange = {
                        onChange(1, it)
                    }
                )

            //  日
            val lastDay = getLastDay(year, month)
            if (day > lastDay) onChange(2, lastDay)
            if (setOf(DatePickerMode.YearMonthDay).indexOf(mode) > -1)
                ColumnPicker(
                    modifier = modifier,
                    value = day,
                    label = { "${it}日" },
                    range = 1..lastDay,
                    onValueChange = {
                        onChange(2, it)
                    }
                )
        }

        // 中间两道横线
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(ListItemPickerHeight)
                .align(Alignment.Center)
        ) {
            Divider(Modifier.padding(horizontal = 15.dp))
            Divider(
                Modifier
                    .padding(horizontal = 15.dp)
                    .align(Alignment.BottomStart)
            )
        }
    }
}

@Composable
fun ColumnPicker(
    modifier: Modifier = Modifier,
    label: (Int) -> String = {
        it.toString()
    },
    value: Int,
    onValueChange: (Int) -> Unit,
    range: Iterable<Int>,
    textStyle: TextStyle = LocalTextStyle.current,
) {
    ListItemPicker(
        modifier = modifier,
        label = label,
        value = value,
        onValueChange = onValueChange,
        list = range.toList(),
        textStyle = textStyle
    )
}

/**
 * 根据年月, 获取天数
 */
private fun getLastDay(year: Int, month: Int): Int {
    return when (month) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        4, 6, 9, 11 -> 30
        else -> {
            // 百年: 四百年一闰年;  否则: 四年一闰年;
            if (year % 100 == 0) {
                if (year % 400 == 0) {
                    29
                } else {
                    28
                }
            } else {
                if (year % 4 == 0) {
                    29
                } else {
                    28
                }
            }
        }
    }
}
