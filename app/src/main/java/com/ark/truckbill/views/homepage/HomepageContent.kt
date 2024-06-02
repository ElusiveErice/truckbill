package com.ark.truckbill.views.homepage

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ark.truckbill.activities.BillActivity
import com.ark.truckbill.components.YearMonthSelectModalLayout
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomepageContent() {
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    val dateState = remember {
        mutableStateOf(LocalDate(2024, 1, 1))
    }
    YearMonthSelectModalLayout(
        sheetState = modalBottomSheetState,
        dateState = dateState,
        hide = { scope.launch { modalBottomSheetState.hide() } }
    ) {
        Content(
            currentDate = dateState.value,
            show = { scope.launch { modalBottomSheetState.show() } })
    }
}

@Composable
private fun Content(currentDate: LocalDate, show: () -> Unit) {
    val context = LocalContext.current
    val onClickAddBill = {
        val intent = Intent(context, BillActivity::class.java)
        context.startActivity(intent)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable(onClick = show)
            ) {
                Text(text = "${currentDate.year}年${currentDate.monthNumber}月")
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "expand"
                )
            }
            BillList(testBillList)
        }
        FloatingActionButton(
            onClick = onClickAddBill,
            modifier = Modifier
                .offset((-50).dp, (-100).dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(Icons.Filled.Add, "add a bill")
        }
    }
}