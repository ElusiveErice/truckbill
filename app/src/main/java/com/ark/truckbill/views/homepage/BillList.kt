package com.ark.truckbill.views.homepage

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ark.truckbill.R
import com.ark.truckbill.data.BillStatus
import com.ark.truckbill.repository.BillDataBase
import com.ark.truckbill.repository.entity.BillEntity
import com.ark.truckbill.ui.theme.BillItemBackground
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun BillList(navController: NavController, billList: List<BillEntity>, onRefresh: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        billList.forEach { BillItem(navController, it, onRefresh) }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BillItem(navController: NavController, bill: BillEntity, onRefresh: () -> Unit) {
    val stateDate = LocalDate.parse(bill.startDate)
    val context = LocalContext.current
    val dao = BillDataBase.getDB(context).getBillDao()
    val scope = rememberCoroutineScope { Dispatchers.IO }
    val alertState = remember { mutableStateOf(false) }

    val closeAlert = {
        alertState.value = false
    }
    val onClick = {
        navController.navigate("bill?billId=${bill.id}/${BillStatus.MODIFY.name}")
    }
    val onLongClick = {
        alertState.value = true
    }
    val onDeleteBill: () -> Unit = {
        scope.launch {
            dao.deleteBill(bill)
            onRefresh()
            closeAlert()
        }
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(onClick = onClick, onLongClick = onLongClick),
        backgroundColor = BillItemBackground,
        shape = MaterialTheme.shapes.small
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = bill.start, fontSize = 24.sp)
                    Icon(
                        painter = painterResource(id = R.drawable.ic_right_arrow),
                        contentDescription = "right arrow",
                        modifier = Modifier.padding(16.dp, 0.dp)
                    )
                    Text(text = bill.end, fontSize = 24.sp)
                }
                Text(text = stringResource(id = R.string.some_yuan, bill.price))
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row() {
                    Text(text = bill.name)
                    Text(text = stringResource(id = R.string.some_dun, bill.weight))
                }
                Text(
                    text = stringResource(
                        id = R.string.year_month_day,
                        stateDate.year,
                        stateDate.monthValue,
                        stateDate.dayOfMonth
                    )
                )
            }
        }
        if (alertState.value) {
            AlertDialog(
                onDismissRequest = closeAlert,
                title = { Text(text = "是否确认删除") },
                confirmButton = {
                    Button(onClick = onDeleteBill) { Text(text = stringResource(id = R.string.confirm)) }
                },
                dismissButton = {
                    OutlinedButton(onClick = closeAlert) { Text(text = stringResource(id = R.string.cancel)) }
                }
            )
        }
    }
}
