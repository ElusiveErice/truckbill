package com.ark.truckbill.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import com.ark.truckbill.R
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ark.truckbill.AlternativeMonthsList
import com.ark.truckbill.AlternativeYearList
import com.ark.truckbill.dp2px
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.datetime.LocalDate


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun YearMonthSelectModalLayout(
    sheetState: ModalBottomSheetState =
        rememberModalBottomSheetState(ModalBottomSheetValue.Hidden),
    dateState: MutableState<LocalDate>,
    hide: () -> Unit,
    content: @Composable () -> Unit
) {
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            YearMonthSelectContainer(
                dateState.value,
                onCancel = hide,
                onConfirm = { year, month ->
                    dateState.value = LocalDate(year, month, 1)
                    hide()
                }
            )
        }
    ) { content() }
}

@Composable
fun YearMonthSelectContainer(
    date: LocalDate,
    onCancel: () -> Unit,
    onConfirm: (years: Int, months: Int) -> Unit
) {
    val currentYearState = remember {
        mutableStateOf(date.year)
    }
    val currentMonthState = remember {
        mutableStateOf(date.monthNumber)
    }
    val connection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                return Offset(x = 0f, y = available.y)
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                return Offset(x = 0f, y = available.y)
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                SelectList(AlternativeYearList, date.year, R.string.some_year) {
                    currentYearState.value = it
                }
                SelectList(AlternativeMonthsList, date.monthNumber, R.string.some_month) {
                    currentMonthState.value = it
                }
            }
            Column(
                modifier = Modifier
                    .height(500.dp)
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color(0XFFFFFFFF),
                                Color(0X00FFFFFF),
                                Color(0XFFFFFFFF)
                            )
                        )
                    ),
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .border(2.dp, Color(0XFF000000), RectangleShape)
                ) {}
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedButton(onClick = onCancel) {
                Text(text = stringResource(id = R.string.cancel))
            }
            Box(modifier = Modifier.width(50.dp)) {}
            Button(onClick = { onConfirm(currentYearState.value, currentMonthState.value) }) {
                Text(text = stringResource(id = R.string.confirm))
            }
        }
    }
}

@Composable
private fun SelectList(
    list: List<Int>,
    selectedItem: Int,
    textRes: Int,
    onChange: (itemKey: Int) -> Unit,
) {
    val listState = rememberLazyListState()
    val itemHeight = 50.dp
    val itemHeightPx = dp2px(dp = itemHeight).toInt()

    LaunchedEffect(selectedItem) {
        val index = list.indexOf(selectedItem)
        listState.scrollToItem(index)
    }
    LaunchedEffect(listState) {
        snapshotFlow { listState.isScrollInProgress }
            .filter { !it }
            .collect {
                val newItemIndex = if (listState.firstVisibleItemScrollOffset > itemHeightPx / 2)
                    listState.firstVisibleItemIndex + 1
                else listState.firstVisibleItemIndex
                listState.scrollToItem(newItemIndex)
                onChange(list[newItemIndex])
            }
    }
    LazyColumn(
        state = listState,
        modifier = Modifier.height(250.dp)
    ) {
        items(2) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(itemHeight)
                    .width(200.dp)
            ) {}
        }
        items(list.size, key = { it }) { index ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(itemHeight)
                    .width(200.dp)
            ) {
                Text(
                    fontSize = 18.sp,
                    text = stringResource(id = textRes, list[index])
                )
            }
        }
        items(2) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(itemHeight)
                    .width(200.dp)
            ) {}
        }
    }
}
