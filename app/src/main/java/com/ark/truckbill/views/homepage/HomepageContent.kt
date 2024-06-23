package com.ark.truckbill.views.homepage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ark.truckbill.R
import com.ark.truckbill.data.BillStatus
import com.ark.truckbill.repository.BillDataBase
import com.ark.truckbill.repository.entity.BillEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun HomepageContent(navController: NavController, currentDate: LocalDate, show: () -> Unit) {
    val context = LocalContext.current
    val dao = BillDataBase.getDB(context).getBillDao()
    var billList by remember {
        mutableStateOf(listOf<BillEntity>())
    }
    val onClickAddBill = {
        navController.navigate("bill?billId=${0}/${BillStatus.ADD.name}")
    }
    val onRefresh: () -> Unit = {
        CoroutineScope(Dispatchers.IO).launch {
            billList = dao.getBillsWithYearAndMonth(currentDate.year, currentDate.monthValue)
        }
    }
    LaunchedEffect(currentDate) {
        CoroutineScope(Dispatchers.IO).launch {
            billList = dao.getBillsWithYearAndMonth(currentDate.year, currentDate.monthValue)
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable(onClick = show)
            ) {
                Text(
                    text = stringResource(
                        id = R.string.year_month,
                        currentDate.year,
                        currentDate.monthValue
                    )
                )
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "expand"
                )
            }
            BillList(navController, billList, onRefresh)
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
